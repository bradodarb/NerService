package co.relevator.nlp.ner;

import co.relevator.nlp.ner.models.NerToken;
import junit.framework.TestCase;

import java.util.List;

/**
 * Created by brad on 3/26/14.
 */
public class NerParserTest extends TestCase {
    public void testGetInstance() throws Exception {
        assertNotNull(NerParser.getInstance());
    }

    public void testParse() throws Exception {
        String inputLine1 = "Flights from New York to buenos Aires";
        String inputLine2 = "After leaving Cornell University we moved to South Africa to meet Nelson Mandela";


        List<NerToken> tokens = NerParser.getInstance().parse(inputLine1).getTokens();

        //Should be two locations
        assertEquals(tokens.size(), 2);
        for(NerToken token: tokens){
            System.out.println(token.toString());
        }

        tokens = NerParser.getInstance().parse(inputLine2).getTokens();

        //Organization, Location and Person
        assertEquals(tokens.size(), 3);
        for(NerToken token: tokens){
            System.out.println(token.toString());
        }
    }
}
