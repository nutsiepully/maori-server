package io.pulkit.maori.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/kairos")
public class KairosController {
    public static final String KAIROS_MODEL_RANDOM_FOREST = "kairos-model-random-forest";
    public static final String KAIROS_INSTANCES_RANDOM_FOREST = "kairos-instances-random-forest";
    public static final String KAIROS_RESOURCE_FOLDER = "kairos";
    public static final int N_FEATURES = 16; //includes label
    public static final HashMap<Double, String> labelMap = new HashMap<Double, String>(); 
    

    private RandomForest randomForest;
    private Instances dataset;

    private void initModel() throws IOException, ClassNotFoundException {
        URL file = Thread.currentThread().getContextClassLoader().getResource(KAIROS_RESOURCE_FOLDER+"/"+KAIROS_MODEL_RANDOM_FOREST);
        FileInputStream fis = new FileInputStream(file.getPath());
        ObjectInputStream ois = new ObjectInputStream(fis);
        randomForest = (RandomForest)ois.readObject();
        ois.close();
    }
    
    private void initInstances() throws IOException, ClassNotFoundException {
        URL file = Thread.currentThread().getContextClassLoader().getResource(KAIROS_RESOURCE_FOLDER+"/"+KAIROS_INSTANCES_RANDOM_FOREST);
        FileInputStream fis = new FileInputStream(file.getPath());
        ObjectInputStream ois = new ObjectInputStream(fis);
        dataset = (Instances)ois.readObject();
        ois.close();
        
        Enumeration<Object> en = dataset.attribute(0).enumerateValues();
        int i=0;
        while(en.hasMoreElements()){
        	labelMap.put((double)i++, en.nextElement().toString());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/predict")
    @ResponseBody
    public String activateModel(@RequestParam String inputFeatures) throws Exception {
    	
        if (randomForest == null) initModel();
        if (dataset == null) initInstances();

        String[] features = inputFeatures.split(",");
        double[] dFeatures = new double[N_FEATURES];
        if(features.length!=N_FEATURES-1){
        	throw new IllegalArgumentException();
        }
        for(int i=1;i<N_FEATURES;i++){
        	dFeatures[i] = Double.parseDouble(features[i-1]);
        }
        
        Instance instance = new DenseInstance(1, dFeatures);
        instance.setDataset(dataset);
        
        double result_idx = randomForest.classifyInstance(instance);
        String result = labelMap.get(result_idx);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    @ResponseBody
    public String helloKairos(@RequestParam String myName) throws Exception {
        return "Hello Kairos, from " + myName;
    }
}

