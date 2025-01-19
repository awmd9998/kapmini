package com.demo.opennlp.controller;

import com.demo.opennlp.model.RequestModel;
import com.demo.opennlp.model.ResponseModel;
import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/trainmodel")
public class TrainModelController {

    @PostMapping("/gen")
    public ResponseModel chat(@RequestBody RequestModel request) {
        String baseUrlTrainModels = "src/main/resources/trainmodel";
        String baseUrlFileName = "filename";
        String fileName = Strings.EMPTY;
        String modelName = Strings.EMPTY;
        try {
            fileName = request.getFileName();
            modelName = request.getModelName();
            String nn = baseUrlTrainModels + "/" + modelName+".bin";
            try (OutputStream modelOut = new BufferedOutputStream(
                    new FileOutputStream(nn))) {

                DoccatModel model = trainCategorizerModel(
                        baseUrlFileName + "/" + fileName
                );
                model.serialize(modelOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel("Error. Please try again");
        }
        return new ResponseModel("successfully trained, generate "+baseUrlTrainModels + "/" + modelName+".bin");
    }

    private DoccatModel trainCategorizerModel(String modelFilePath) throws FileNotFoundException, IOException, URISyntaxException {
        DoccatModel model = null;
        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File(this.getClass().getClassLoader().getResource(modelFilePath).toURI() ));
        ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
        ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
        DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator()});
        TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM, 0);
        model = DocumentCategorizerME.train("en", sampleStream, params, factory);
        return model;
    }

}

