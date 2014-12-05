package io.pulkit.maori;

import io.pulkit.maori.controllers.KairosController;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;

public class KairosModelBuilder {
    public static void main(String[] args) throws Exception {
        URL file = Thread.currentThread().getContextClassLoader().getResource("kairos/data_1416450449843_v2.txt");

        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(file.getPath()));
        Instances kairosData = loader.getDataSet();
        kairosData.setClassIndex(9);

        RandomForest randomForest = new RandomForest();
        randomForest.buildClassifier(kairosData);

        FileOutputStream fileOutputStream = new FileOutputStream(KairosController.KAIROS_MODEL_RANDOM_FOREST);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(randomForest);
        outputStream.close();
        fileOutputStream.close();
    }
}
