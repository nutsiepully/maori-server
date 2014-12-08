package io.pulkit.maori;

import io.pulkit.maori.controllers.KairosController;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;

public class KairosModelBuilder {
    public static void main(String[] args) throws Exception {
        URL file = Thread.currentThread().getContextClassLoader().getResource("kairos/d5_withHeader.arff");
        String outputFolder = "src/main/resources/kairos/";
        
        //CSVLoader loader = new CSVLoader();
        ArffLoader loader = new ArffLoader();
        
        //loader.setOptions(new String[]{"-H"});//if file has no header
        
        loader.setSource(new File(file.getPath()));
        Instances kairosData = loader.getDataSet();
        kairosData.setClassIndex(0);

        //-I <number of trees> 
        //-K <number of features>
        RandomForest randomForest = new RandomForest();
        randomForest.setOptions(new String[]{"-I","100","-K","5"});
        randomForest.buildClassifier(kairosData);

        //output model
        FileOutputStream fileOutputStream = new FileOutputStream(outputFolder+KairosController.KAIROS_MODEL_RANDOM_FOREST);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(randomForest);
        outputStream.close();
        fileOutputStream.close();
        //output instances
        fileOutputStream = new FileOutputStream(outputFolder+KairosController.KAIROS_INSTANCES_RANDOM_FOREST);
        outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(kairosData);
        outputStream.close();
        fileOutputStream.close();
    }
}
