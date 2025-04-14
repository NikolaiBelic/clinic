package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.graalvm.compiler.code.DataSection;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service(CitaService.NAME)
public class CitaServiceBean implements CitaService {

    @Inject
    private DataManager dataManager;

    public List<Cita> getAllCitas() {
        LoadContext<Cita> loadContext = LoadContext.create(Cita.class)
                .setQuery(LoadContext.createQuery("select e from clinic_Cita e"))
                .setView("cita-view");
        return dataManager.loadList(loadContext);
    }


    public List<Cita> getCitasPorEspecialista(UUID id) {
        LoadContext<Cita> loadContext = LoadContext.create(Cita.class)
            .setQuery(LoadContext.createQuery("select e from clinic_Cita e where e.especialista.id = :especialistaId")
                .setParameter("especialistaId", id))
            .setView("cita-view");

        return dataManager.loadList(loadContext);
    }

    public Cita getCita(UUID id) {
        LoadContext<Cita> loadContext = LoadContext.create(Cita.class)
                .setQuery(LoadContext.createQuery("select e from clinic_Cita e where e.id = :citaId")
                        .setParameter("citaId", id))
                .setView("cita-view");

        return dataManager.load(loadContext);
    }
}