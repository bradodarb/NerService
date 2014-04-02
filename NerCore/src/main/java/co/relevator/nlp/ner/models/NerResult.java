package co.relevator.nlp.ner.models;

import java.util.List;

/**
 * Created by brad on 3/26/14.
 */
public class NerResult {

    String error;
    String input;
    List<NerToken> tokens;


    public Boolean hasError(){
        return !error.isEmpty();
    }

    public String getInput(){
        return input;
    }

    public List<NerToken>  getTokens(){
        return tokens;
    }


    public String getError(){
        return error;
    }

    public NerResult(String input, List<NerToken> tokens, String error){
        this.input = input;
        this.tokens = tokens;
        this.error = error;
    }
}
