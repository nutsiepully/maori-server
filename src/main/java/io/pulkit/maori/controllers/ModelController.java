package io.pulkit.maori.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

@Controller
@RequestMapping(value = "/model")
public class ModelController {

    /*
     * Adds a model to the database. But doesn't associate it with any
     * device.
     */
    @RequestMapping(method = RequestMethod.POST)
    public void addModel(HttpServletRequest httpServletRequest) {
        throw new NotImplementedException();
    }

    /*
     * Adds a model and associates it with a device.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/device/add")
    public void addModelAndAssociateDevice(HttpServletRequest httpServletRequest) {
        throw new NotImplementedException();
    }

    /*
     * Associate an existing model in the database with a device.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/device/associate")
    public void associateWithDevice() {
        throw new NotImplementedException();
    }

    /*
     * Fetch a Model, given its name
     */
    @RequestMapping(method = RequestMethod.GET)
    public void getModel(HttpServletResponse response, @RequestParam String modelName) {
        throw new NotImplementedException();
    }

    /*
     * Get the list of all the models for the device - as JSON
     */
    @RequestMapping(method = RequestMethod.GET, value = "/device")
    public String getModelsForDevice(@RequestParam String deviceId) {
        throw new NotImplementedException();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public void getClassifier(HttpServletResponse httpServletResponse, @RequestParam String modelId) throws Exception {
        System.out.println("Serving the model - " + modelId);

        if (modelId == null || modelId.trim().equals("")) {
            URL file = Thread.currentThread().getContextClassLoader().getResource("iris.data");

            CSVLoader loader = new CSVLoader();
            loader.setSource(new File(file.getPath()));
            Instances irisData = loader.getDataSet();
            irisData.setClassIndex(4);

            NaiveBayesUpdateable naiveBayes = new NaiveBayesUpdateable();
            naiveBayes.buildClassifier(irisData);
            for (Instance instance : irisData) {
                naiveBayes.updateClassifier(instance);
            }

            ObjectOutputStream outputStream = new ObjectOutputStream(httpServletResponse.getOutputStream());
            outputStream.writeObject(naiveBayes);

            return;
        }

        URL file = Thread.currentThread().getContextClassLoader().getResource(modelId);
        FileInputStream fis = new FileInputStream(file.getPath());
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object model = ois.readObject();
        ObjectOutputStream outputStream = new ObjectOutputStream(httpServletResponse.getOutputStream());
        outputStream.writeObject(model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    @ResponseBody
    public String get() {
        return "test";
    }

}
