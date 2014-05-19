package io.pulkit.maori.repository;

import io.pulkit.maori.domain.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AllModels {

    @Autowired
    private DataAccessTemplate dataAccessTemplate;

    public AllModels() {
    }

    public void add(Model model) {
        dataAccessTemplate.save(model);
    }
}
