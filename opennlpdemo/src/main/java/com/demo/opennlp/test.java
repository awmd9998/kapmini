package com.demo.opennlp;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class test {

    public static void loadModel(InputStream is) {
        if (is == null) {
            throw new IllegalArgumentException("Resource not found!");
        }
        // Add logic to process the InputStream, e.g., loading the model
        System.out.println("Model loaded successfully.");
    }




    public static void main(String[] args) throws IOException {

        InputStream is = LanguageDetector.class.getClassLoader().getResourceAsStream("langdetect-183.bin");


        //langdetect-183.bin is the model downloaded
        LanguageDetectorModel langModel = new LanguageDetectorModel(is);
        String input = "This is an example of text in English i want to learning more things on it";
        LanguageDetector langDetector = new LanguageDetectorME(langModel);
        Language language = langDetector.predictLanguage(input);

        System.out.println("Language Detected: " + language.getLang() +" Confidence Score: " + language.getConfidence());

        Language[] languages = langDetector.predictLanguages(input);
        System.out.println("Language Possibilities: " + Arrays.toString(languages));



        InputStream iss =  LanguageDetector.class.getClassLoader().getResourceAsStream("opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin");
//opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin is the model for English sentences
        SentenceModel model= new SentenceModel(iss);
        String inputss = "Hello, how are you? I am fine, thank you for asking. It is a wonderful day today, isnt it? ";

        SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
        String sentences[] = sentenceDetector.sentDetect(inputss);
        System.out.println("Number of sentences detected: " + sentences.length );
        System.out.println("Second Sentence: " + sentences[1] );





        InputStream isss = LanguageDetector.class.getClassLoader().getResourceAsStream("opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin");
//opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin is the English Token model
        TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(isss));

        String sentence = "Hello, how are you? I am fine, thank you for asking. It is a wonderful day today, isnt it? ";
        String[] tokens = myCategorizer.tokenize(sentence);
        System.out.println("Tokenizer : " + Arrays.stream(tokens).collect(Collectors.joining(" | ")));





    }

}
