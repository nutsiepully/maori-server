package io.pulkit.maori.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;

@Controller
@RequestMapping(value = "/kairos")
public class KairosController {
    public static final String KAIROS_MODEL_RANDOM_FOREST = "kairos-model-random-forest";

    private RandomForest randomForest;

    private void initModel() throws IOException, ClassNotFoundException {
        URL file = Thread.currentThread().getContextClassLoader().getResource(KAIROS_MODEL_RANDOM_FOREST);
        FileInputStream fis = new FileInputStream(file.getPath());
        ObjectInputStream ois = new ObjectInputStream(fis);
        randomForest = (RandomForest)ois.readObject();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/predict")
    @ResponseBody
    public String activateModel(@RequestParam String inputFeatures) throws Exception {
        if (randomForest == null) initModel();

        String[] features = inputFeatures.split(",");

        double result = randomForest.classifyInstance(new DenseInstance(5));

        throw new NotImplementedException();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    @ResponseBody
    public String helloKairos(@RequestParam String myName) throws Exception {
        return "Hello Kairos, from " + myName;
    }
}

