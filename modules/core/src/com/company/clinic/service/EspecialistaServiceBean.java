package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.Especialista;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(EspecialistaService.NAME)
public class EspecialistaServiceBean implements EspecialistaService {

    @Inject
    private DataManager dataManager;

    public List<Especialista> getEspecialistas() {
        LoadContext<Especialista> loadContext = LoadContext.create(Especialista.class)
                .setQuery(LoadContext.createQuery("select e from clinic_Especialista e"))
                .setView("especialista-view");
        return dataManager.loadList(loadContext);
    }
}