package co.relevator.nlp.ner;

/**
 * Created by brad on 3/29/14.
 */
public class NerSettings {

    protected String name;
    protected String dictionary;

    public String getName(){
        return name;
    }
    public String getDictionary(){
        return dictionary;
    }


    public void setName(String _name){
        name = _name;
    }
    public void setDictionary(String _dictionary){
        dictionary = _dictionary;
    }

}
