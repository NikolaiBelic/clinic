package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import com.haulmont.cuba.core.app.ConfigStorageService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.graalvm.compiler.code.DataSection;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service(CitaService.NAME)
public class CitaServiceBean implements CitaService {

    @Inject
    private DataManager dataManager;

    @Inject
    private ConfigStorageService configStorageService;

    @Inject
    private Logger log;

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

    @Override
    public List<Cita> getAllCitasMS() {
        String urlCitas = configStorageService.getDbProperty("URL-CITAS");
        String endPoint = "/citas";
        String url = urlCitas + endPoint;

        log.info(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Traking-Id" , UUID.randomUUID().toString());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Cita>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Cita>>() {});


        return responseEntity.getBody();
    }
}