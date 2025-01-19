package com.demo.opennlp.bean;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.model.ModelUtil;

public class ChatBean {
}

//@Component
//public class ChatBean implements InitializingBean {
//
//	private  Map<String, String> chatResponse = new HashMap();
//	private DoccatModel model;
//
//	private  void trainCategorizerModel() throws FileNotFoundException, IOException, URISyntaxException {
//		// train.txt is a custom training data with categories
//		InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File(this.getClass().getClassLoader().getResource("train.txt").toURI()  ));
//		ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
//		ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
//
//		DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });
//
//		TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
//		params.put(TrainingParameters.CUTOFF_PARAM, 0);
//
//		// Train a model with classifications from above file.
//		model = DocumentCategorizerME.train("en", sampleStream, params, factory);
//
//
//	}
//
//
//
//	@Override
//    public void afterPropertiesSet() throws Exception {
//        // Your code here
//		String SOCIAL_MEDIA_BOT_ENABLED_CONFIG = "this is a social media chat bot configuration, use to enable social bot like whatsapp, facebook, instagram, and email bot";
//		chatResponse.put("SOCIAL_MEDIA_BOT_ENABLED_CONFIG", SOCIAL_MEDIA_BOT_ENABLED_CONFIG);
//
//		String TICKET_DASHBOARD_CONFIGURATION = "refers to the setup and customization of a dashboard that displays key information and metrics related to tickets, such as support tickets, service requests, or incident tickets. The configuration typically involves selecting data sources, visualizations, and filters to present ticket-related insights effectively.";
//		chatResponse.put("TICKET_DASHBOARD_CONFIGURATION", TICKET_DASHBOARD_CONFIGURATION);
//
//		String TICKET_MIGRATION_CONFIGURATION = "refers to the setup and process of transferring ticket data from one system or platform to another. This configuration ensures that all necessary details about tickets—such as historical data, statuses, attachments, and user information—are accurately and securely migrated to a new system without loss or corruption.";
//		chatResponse.put("TICKET_MIGRATION_CONFIGURATION", TICKET_MIGRATION_CONFIGURATION);
//
//
//		String QUEUE_KEY_TO_CITY_MAP = "refers to a configuration or mapping system that associates specific queue keys (or identifiers) with corresponding cities or geographic locations. This configuration is typically used in customer service, call centers, or support systems where tickets, requests, or customer interactions are routed based on geographic location.";
//		chatResponse.put("QUEUE_KEY_TO_CITY_MAP", QUEUE_KEY_TO_CITY_MAP);
//
//		trainCategorizerModel();
//
//    }
//
//	public DoccatModel getModel() {
//		return model;
//	}
//
//	public Map<String, String> getQuestionAnswer() {
//		return chatResponse;
//	}
//
//}


