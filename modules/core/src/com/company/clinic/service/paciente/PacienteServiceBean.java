package com.company.clinic.service.paciente;

import com.company.clinic.entity.pacientes.Paciente;
import com.haulmont.cuba.core.app.ConfigStorageService;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service(PacienteService.NAME)
public class PacienteServiceBean implements PacienteService {

    @Inject
    private ConfigStorageService configStorageService;

    @Inject
    private Logger log;

    public List<Paciente> getAllPacientes() {
        String urlPacientes = configStorageService.getDbProperty("URL-PACIENTES");

        log.info(urlPacientes);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Traking-Id" , UUID.randomUUID().toString());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Paciente>> responseEntity = restTemplate.exchange(
                urlPacientes,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Paciente>>() {});

        return responseEntity.getBody();
    }

}