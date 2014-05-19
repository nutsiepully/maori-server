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

import java.io.IOException;
import java.util.List;

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
        List<Model> models = modelService.getModelsForDevice(deviceId);

        StringBuilder sb = new StringBuilder();
        for (Model model : models) {
            sb.append(model.toString() + ",\n");
        }
        String modelInfo = sb.substring(0, sb.length() - 2);

        return "{\n" +
                "    \"result\": [\n" +
                     modelInfo +
                "    ]\n" +
                "}";
    }

}
