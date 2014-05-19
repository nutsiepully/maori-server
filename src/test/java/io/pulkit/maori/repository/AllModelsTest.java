package io.pulkit.maori.repository;

import io.pulkit.maori.SpringIntegrationTest;
import io.pulkit.maori.domain.Model;
import io.pulkit.maori.domain.ModelDevice;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AllModelsTest extends SpringIntegrationTest {

    @Autowired
    private AllModels allModels;

    @Test
    public void testAdd() throws Exception {
//        allModels.add(new Model("name1", "version1", true, true, new byte[] {'a', 's', 'd'}));
//        template.save(new Model("name1", "version1", true, true, new byte[] {'a', 's', 'd'}));
        Model model = new Model("name2", "version2", true, true, new byte[] {'a', 's', 'd'});
        template.save(model);
        ModelDevice modelDevice = new ModelDevice("device1", model);
        template.save(modelDevice);
    }
}
