package io.pulkit.maori.services;

import io.pulkit.maori.domain.Model;
import io.pulkit.maori.domain.ModelDevice;
import io.pulkit.maori.repository.AllModelDevices;
import io.pulkit.maori.repository.AllModels;
import io.pulkit.maori.repository.DataAccessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {

    @Autowired
    private AllModels allModels;

    @Autowired
    private AllModelDevices allModelDevices;

    @Autowired
    private DataAccessTemplate dataAccessTemplate;

    public void addModel(Model model) {
        Model existingModel = allModels.get(model.getName(), model.getVersion());
        if (existingModel != null) throw new RuntimeException("Model already exists : " + model.getName() + " " + model.getVersion());

        allModels.add(model);
    }

    public void attachModelToDevice(String modelName, String modelVersion, String deviceId) {
        Model model = allModels.get(modelName, modelVersion);
        if (model == null) throw new RuntimeException("Model does not exit");

        ModelDevice modelDevice = new ModelDevice(deviceId, model);
        allModelDevices.add(modelDevice);
    }

    public byte[] getModelPayload(String modelName, String modelVersion) {
        Model model = allModels.get(modelName, modelVersion);
        return model.getPayload();
    }

    public List<Model> getModelsForDevice(String deviceId) {
        List models = dataAccessTemplate.find("select m from ModelDevice md inner join md.model m where md.deviceId = ?", deviceId);
        return models;
    }
}
