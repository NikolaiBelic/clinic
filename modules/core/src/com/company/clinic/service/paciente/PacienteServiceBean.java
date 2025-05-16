package com.company.clinic.service.paciente;

import com.company.clinic.entity.pacientes.DatosAdministrativos;
import com.company.clinic.entity.pacientes.DatosContacto;
import com.company.clinic.entity.pacientes.DatosFacturacion;
import com.company.clinic.entity.pacientes.Paciente;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haulmont.cuba.core.app.ConfigStorageService;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.sql.Date;
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

    @Override
    public List<Paciente> findPacientesByFilter(
            String nombre,
            String apellidos,
            Date fechaNacimiento,
            String genero,
            String estadoPaciente,
            String ciudadNacimiento,
            String nacionalidad,
            String provinciaNacimiento,
            String tipoDocumento,
            String numeroDocumento
    ) {
        String urlPacientes = configStorageService.getDbProperty("URL-PACIENTES");
        String urlPacientesFilter = "/filter";
        StringBuilder fullUrl = new StringBuilder(urlPacientes + urlPacientesFilter + "?");

        // Agregar parámetros a la URL solo si no son nulos
        if (nombre != null) fullUrl.append("nombre=").append(nombre).append("&");
        if (apellidos != null) fullUrl.append("apellidos=").append(apellidos).append("&");
        if (fechaNacimiento != null) fullUrl.append("fechaNacimiento=").append(fechaNacimiento).append("&");
        if (genero != null) fullUrl.append("genero=").append(genero).append("&");
        if (estadoPaciente != null) fullUrl.append("estadoPaciente=").append(estadoPaciente).append("&");
        if (ciudadNacimiento != null) fullUrl.append("ciudadNacimiento=").append(ciudadNacimiento).append("&");
        if (nacionalidad != null) fullUrl.append("nacionalidad=").append(nacionalidad).append("&");
        if (provinciaNacimiento != null) fullUrl.append("provinciaNacimiento=").append(provinciaNacimiento).append("&");
        if (tipoDocumento != null) fullUrl.append("tipoDocumento=").append(tipoDocumento).append("&");
        if (numeroDocumento != null) fullUrl.append("numeroDocumento=").append(numeroDocumento).append("&");

        // Eliminar el último "&" si existe
        if (fullUrl.charAt(fullUrl.length() - 1) == '&') {
            fullUrl.deleteCharAt(fullUrl.length() - 1);
        }

        log.info("URL construida: {}", fullUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Traking-Id" , UUID.randomUUID().toString());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Paciente>> responseEntity = restTemplate.exchange(
                fullUrl.toString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Paciente>>() {});

        return responseEntity.getBody();
    }

    @Override
    public List<Paciente> findPacientesByFiltro(Map<String, Object> params) {
        String urlPacientes = configStorageService.getDbProperty("URL-PACIENTES");
        String urlPacientesFilter = "/filtro";
        String fullUrl = urlPacientes + urlPacientesFilter;

        int page = Integer.parseInt(params.remove("page").toString());
        int size = Integer.parseInt(params.remove("size").toString());

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(fullUrl)
                .queryParam("page", page)
                .queryParam("size", size);

        log.info(uriComponentsBuilder.toUriString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Tracking-Id" , UUID.randomUUID().toString());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Paciente>> responseEntity = restTemplate.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<Paciente>>() {});

        return responseEntity.getBody();
    }

    @Override
    public Long getTotalFiltros(Map<String, Object> params) {
        String urlPacientes = configStorageService.getDbProperty("URL-PACIENTES");
        String urlPacientesFilter = "/filtro/total";
        String fullUrl = urlPacientes + urlPacientesFilter;

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

    public String createPaciente(Paciente paciente) {
        String urlPacientes = configStorageService.getDbProperty("URL-PACIENTES");
        String urlPacientesCreate = "/create";
        String fullUrl = urlPacientes + urlPacientesCreate;

        log.info(fullUrl);

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        String name = f.getName();
                        return name.equals("dynamicAttributes") || name.startsWith("_persistence_") || name.startsWith("__")
                                || name.equals("id") || name.equals("version");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setPrettyPrinting()
                .create();

        String jsonPaciente = gson.toJson(paciente);
        System.out.println(jsonPaciente);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Traking-Id" , UUID.randomUUID().toString());

        HttpEntity<String> entity = new HttpEntity<>(jsonPaciente, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                fullUrl,
                HttpMethod.POST,
                entity,
                String.class);

        return responseEntity.getBody();
    }

    public String updatePaciente(Paciente paciente) {
        String urlPacientes = configStorageService.getDbProperty("URL-PACIENTES");
        String urlPacientesCreate = "/update";
        String fullUrl = urlPacientes + urlPacientesCreate;

        log.info(fullUrl);

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        String name = f.getName();
                        String declaringClassName = f.getDeclaringClass().getSimpleName();

                        return name.equals("dynamicAttributes")
                                || name.startsWith("_persistence_")
                                || name.startsWith("__")
                                || (name.equals("paciente") && (
                                declaringClassName.equals("DatosAdministrativos")
                                        || declaringClassName.equals("DatosContacto")
                                        || declaringClassName.equals("DatosFacturacion")
                        ));
                }


                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setPrettyPrinting()
                .create();

        String jsonPaciente = gson.toJson(paciente);
        System.out.println(jsonPaciente);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Traking-Id" , UUID.randomUUID().toString());

        HttpEntity<String> entity = new HttpEntity<>(jsonPaciente, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                fullUrl,
                HttpMethod.PUT,
                entity,
                String.class);

        return responseEntity.getBody();
    }

    public void softDeletePacientes(Map<String, Object> pacientes) {
        String urlPacientes = configStorageService.getDbProperty("URL-PACIENTES");
        String urlPacientesCreate = "/soft-delete";
        String fullUrl = urlPacientes + urlPacientesCreate;

        log.info(fullUrl);

        // Serializar el Map a JSON para el log
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonPacientes = gson.toJson(pacientes);
        log.info("JSON enviado: {}", jsonPacientes);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Traking-Id" , UUID.randomUUID().toString());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(pacientes, headers);

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                fullUrl,
                HttpMethod.PATCH,
                entity,
                Void.class);
    }
}