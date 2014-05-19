package io.pulkit.maori.controllers;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.pulkit.maori.domain.Model;
import io.pulkit.maori.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

@Controller
@RequestMapping(value = "/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    /*
     * Adds a model to the database.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addModel(@RequestParam String name, @RequestParam String version,
                           @RequestParam MultipartFile file,
                           @RequestParam(required = false) Boolean active,
                           @RequestParam(required = false) Boolean archive) throws IOException {

        // by default models are active and not archived.
        active = (active == null) ? true : active;
        archive = (archive == null) ? false : archive;

        Model model = new Model(name, version, active, archive, file.getBytes());
        modelService.addModel(model);

        return "Success";
    }

    /*
     * Activate/deactivate model
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/activate")
    @ResponseBody
    public String activateModel(@RequestParam String name, @RequestParam String version, @RequestParam Boolean active) {
        throw new NotImplementedException();
    }

    /*
     * Archive/Un-archive model
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/archive")
    @ResponseBody
    public String archiveModel(@RequestParam String name, @RequestParam String version, @RequestParam Boolean archive) {
        throw new NotImplementedException();
    }

    /*
     * Associate an existing model in the database with a device.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/device/associate")
    @ResponseBody
    public String associateWithDevice(@RequestParam String name, @RequestParam String version,
                                      @RequestParam String deviceId) {
        modelService.attachModelToDevice(name, version, deviceId);

        return "Success";
    }

    /*
     * Fetch a Model, given its name
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getModel(@RequestParam String name, @RequestParam String version) {
        byte[] modelPayload = modelService.getModelPayload(name, version);
        String encodedModel = Base64.encode(modelPayload);

        return encodedModel;
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

    @RequestMapping(method = RequestMethod.POST, value = "/test")
    @ResponseBody
    public String uploadFile(@RequestParam MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("uploaded_file")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded file.";
            } catch (Exception e) {
                return "You failed to upload file." + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }

}
