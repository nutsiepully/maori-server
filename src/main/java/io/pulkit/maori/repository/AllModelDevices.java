package io.pulkit.maori.repository;

import io.pulkit.maori.domain.ModelDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AllModelDevices {

    @Autowired
    private DataAccessTemplate dataAccessTemplate;

    public AllModelDevices() {}

    public void add(ModelDevice modelDevice) {
        dataAccessTemplate.save(modelDevice);
    }
}
