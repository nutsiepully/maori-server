package io.pulkit.maori.repository;

import io.pulkit.maori.domain.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
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

    public Model get(String name, String version) {
        Object result = DataAccessUtils.uniqueResult(dataAccessTemplate.find(
                "select m from Model m where m.name = ? and m.version = ?",
                name, version));

        return (Model) result;
    }
}
