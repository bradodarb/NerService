package co.relevator.nlp.ner.http;

import co.relevator.nlp.ner.NerParser;
import co.relevator.nlp.ner.models.NerResult;
import com.google.gson.Gson;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import java.io.*;
import java.util.*;

/**
 * Root resource (exposed at "api" path)
 */
@Path("api")
public class NerResource {
    static NerWebServiceSettings _settings;
    @Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ping")
    public String ping() throws IOException {
        Gson gson = new Gson();

        return gson.toJson( new Date());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("query/{q}")
    public String getResult(@PathParam("q") String q) {

        Gson gson = new Gson();
        NerResult result;
        try {

            checkInit();
            NerParser.getInstance().init(_settings);
            result = NerParser.getInstance().parse(q);

        }catch (Exception ex){

            result = new NerResult(q, null, ex.getMessage());
        }
        return gson.toJson(result);
    }

    private void checkInit(){
        if(_settings == null)
        {
            try {
                String path = context.getInitParameter("propsFile");
                FileInputStream inputStream = null;
                inputStream = new FileInputStream(new File(context.getRealPath(path)));
                if (inputStream != null) {
                    final Gson gson = new Gson();
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    _settings = gson.fromJson(reader, NerWebServiceSettings.class);
                }
            } catch (final Exception e) {
                //log
            }
        }
    }


}
