package com.demo.opennlp.model;

public class RequestModel {

    String inputData;
    String fileName; // text
    String modelName; // modelName

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }


    public String getFileName() {
        return fileName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
