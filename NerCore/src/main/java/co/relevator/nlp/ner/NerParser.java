package co.relevator.nlp.ner;

import co.relevator.nlp.ner.models.NerResult;
import co.relevator.nlp.ner.models.NerToken;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;


import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.util.Triple;
import org.apache.commons.lang3.text.WordUtils;

public class NerParser {

    private final static NerParser instance = new NerParser();
    private static NerSettings _settings;
    private NerParser() {

    }

    public static NerParser getInstance() {
        return instance;
    }
    public void init(NerSettings settings) {
        _settings = settings;
        _classifier = null;
    }

    private static AbstractSequenceClassifier<CoreLabel> _classifier;

    private AbstractSequenceClassifier<CoreLabel> getClassifier() {

        if(_settings == null){
            //build defaults
            _settings = new NerSettings();
            _settings.setDictionary("edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
        }
        if (_classifier == null) {
            _classifier = CRFClassifier.getClassifierNoExceptions(_settings.getDictionary());
        }
        return _classifier;
    }

    public NerResult parse(String rawInput) {

        String error = "";
        List<NerToken> tokens = new ArrayList<NerToken>();
        try {
            String input = WordUtils.capitalize(rawInput);

            AbstractSequenceClassifier<CoreLabel> classifier = getClassifier();

            List<Triple<String, Integer, Integer>> output = classifier.classifyToCharacterOffsets(input);

            for (Triple<String, Integer, Integer> token : output) {

                String ne = input.substring(token.second, token.third);
                NerToken nerToken = new NerToken(ne, token.first());
                tokens.add(nerToken);
            }

        } catch (Exception ex) {
            error = ex.getMessage();
        } finally {
            return new NerResult(rawInput, tokens, error);
        }
    }

}
