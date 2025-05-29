package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.Especialista;
import com.haulmont.cuba.core.app.ConfigStorageService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service(EspecialistaService.NAME)
public class EspecialistaServiceBean implements EspecialistaService {

    @Inject
    private DataManager dataManager;

    @Inject
    private Logger log;

    @Inject
    private ConfigStorageService configStorageService;

    public List<Especialista> getEspecialistas() {
        LoadContext<Especialista> loadContext = LoadContext.create(Especialista.class)
                .setQuery(LoadContext.createQuery("select e from clinic_Especialista e"))
                .setView("especialista-view");
        return dataManager.loadList(loadContext);
    }

    @Override
    public List<Especialista> findEspecialistasByFiltro(Map<String, Object> params) {
        String urlEspecialistas = configStorageService.getDbProperty("URL-ESPECIALISTAS");
        String urlEspecialistasFilter = "/filtro";
        String fullUrl = urlEspecialistas + urlEspecialistasFilter;

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(fullUrl);

        log.info(uriComponentsBuilder.toUriString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Tracking-Id" , UUID.randomUUID().toString());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Especialista>> responseEntity = restTemplate.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<Especialista>>() {});

        return responseEntity.getBody();
    }

    @Override
    public Long getTotalFiltros(Map<String, Object> params) {
        String urlEspecialistas = configStorageService.getDbProperty("URL-ESPECIALISTAS");
        String urlEspecialistasFilter = "/filtro/total";
        String fullUrl = urlEspecialistas + urlEspecialistasFilter;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Tracking-Id" , UUID.randomUUID().toString());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Long> responseEntity = restTemplate.exchange(
                fullUrl,
                HttpMethod.POST,
                entity,
                Long.class);

        return responseEntity.getBody();
    }
}