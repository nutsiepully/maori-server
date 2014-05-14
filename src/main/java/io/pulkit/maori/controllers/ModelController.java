package io.pulkit.maori.controllers;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    @RequestMapping(method = RequestMethod.GET, value = "/info")
    @ResponseBody
    public String getModelsForDevice(@RequestParam String deviceId) {
        System.out.println("Serving model info list - " + deviceId);

        return "{\n" +
                "    \"result\": [\n" +
                "        { \"name\": \"barometer-model.model\", \"version\": \"version1\", \"active\": false },\n" +
                "        { \"name\": \"barometer-model.model\", \"version\": \"version2\", \"active\": true },\n" +
                "        { \"name\": \"model2\", \"version\": \"version1\", \"active\": false }\n" +
                "    ]\n" +
                "}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    @ResponseBody
    public String getClassifier(HttpServletResponse httpServletResponse, @RequestParam String model,
                              @RequestParam String version) throws Exception {
        System.out.println("Serving the model - " + model);

        // Read model file into object
        URL file = Thread.currentThread().getContextClassLoader().getResource(model);
        FileInputStream fis = new FileInputStream(file.getPath());
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object modelObject = ois.readObject();

        String result = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(modelObject);
            objectOutputStream.close();
            result = new String(Base64.encode(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    @ResponseBody
    public String get() {
        return "test";
    }

}
