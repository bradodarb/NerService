package co.relevator.nlp.ner.models;

public class NerToken {

    String term;


    String type;

    public NerToken(String w,String n) {

        term = w;
        type = n;

    }




    /*Term*/
    public String getTerm(){
        return term;
    }

    /*Named Entity*/
    public String getType(){

        return type;
    }

    @Override
    public String toString() {

        return getTerm() + " : " + getType();
    }

}
