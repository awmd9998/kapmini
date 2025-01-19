package com.demo.opennlp.controller;

import com.demo.opennlp.model.RequestModel;
import com.demo.opennlp.model.ResponseModel;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/nlp")
public class NLPController {

    private static String baseUrlTrainModels = "src/main/resources/trainmodel";

    @PostMapping("/model")
    public ResponseModel chat(@RequestBody RequestModel request) {
        try {
            String modelName = request.getModelName();
            File file = ResourceUtils.getFile(baseUrlTrainModels +"/"+ modelName);
            if(file == null){
                return new ResponseModel("Error. model Name not found");
            }
            InputStream in = new FileInputStream(file);
            DoccatModel model = new DoccatModel(in);
            if (model == null) {
                return new ResponseModel("Error. model Name not found");
            }
            String input = request.getInputData();
            String outputResponse = null;
            if (input != null && input.length() > 0) {
                for (String sentence : nlpSentences(input)) {
                    String[] tokens = nlpTokens(sentence);
                    String[] lemmas = nlpLemma(tokens);
                    Map<Double, Set<String>> category = detectCategory(model, lemmas);
                    // outputResponse = outputResponse + " " + chatBean.getQuestionAnswer().get(category);
                    outputResponse = category.toString();
                }
            }
            return new ResponseModel(outputResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel("Error. Please try again");
        }
    }

    private Map<Double, Set<String>> detectCategory(DoccatModel model, String[] lemmas) throws IOException {
        // Initialize document categorizer tool

        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        // Get best possible category.
        double[] probabilitiesOfOutcomes = myCategorizer.categorize(lemmas);
        String category = myCategorizer.getAllResults(probabilitiesOfOutcomes);

        String categoryss = myCategorizer.getBestCategory(probabilitiesOfOutcomes);

        Map<Double, Set<String>> map = myCategorizer.sortedScoreMap(lemmas);
         List<String> li = new ArrayList<>();

        Map<Double, Set<String>> sortedMapDesc = new TreeMap<>(Collections.reverseOrder());
        sortedMapDesc.putAll(map);


        for (Map.Entry<Double, Set<String>> entry : sortedMapDesc.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
            Set<String> lis = entry.getValue();
        }

        for (Map.Entry<Double, Set<String>> entry : map.entrySet()) {

            Double key = entry.getKey();
            Set<String> value = entry.getValue();

            System.out.println("Score: " + key + ", Lemmas: " + value);
        }

      //  System.out.println(map.toString());
        System.out.println(categoryss);


        return sortedMapDesc;

//        return obj;
    }

    private String[] nlpLemma(String[] tokens) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin");
        TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(is));
        InputStream posis = this.getClass().getClassLoader().getResourceAsStream("en-pos-maxent.bin");
        POSModel posModel = new POSModel(posis);
        POSTaggerME pos = new POSTaggerME(posModel);
        String partsOfSpeech[] = pos.tag(tokens);

        //Lemmatization
        InputStream lemmais = this.getClass().getClassLoader().getResourceAsStream("en-lemmatizer.dict");
        DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(lemmais);
        String[] lemmas = lemmatizer.lemmatize(tokens, partsOfSpeech);
        return lemmas;
    }


    private String[] nlpTokens(String sentence) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin");
        TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(is));
        String[] tokens = myCategorizer.tokenize(sentence);
        return tokens;
    }

    private String[] nlpSentences(String input) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin");
        SentenceModel sentModel = new SentenceModel(is);
        SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentModel);
        String sentences[] = sentenceDetector.sentDetect(input);
        return sentences;
    }

}
