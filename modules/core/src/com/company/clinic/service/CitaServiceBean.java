package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.Especialista;
import com.company.clinic.entity.pacientes.Paciente;
import com.company.clinic.entity.Servicio;
import com.google.gson.*;
import com.haulmont.cuba.core.app.ConfigStorageService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
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
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(CitaService.NAME)
public class CitaServiceBean implements CitaService {

    @Inject
    private DataManager dataManager;

    @Inject
    private ConfigStorageService configStorageService;

    @Inject
    private Logger log;
    @Inject
    private Metadata metadata;

    public List<Cita> getAllCitas() {
        LoadContext<Cita> loadContext = LoadContext.create(Cita.class)
                .setQuery(LoadContext.createQuery("select e from clinic_Cita e"))
                .setView("cita-view");
        return dataManager.loadList(loadContext);
    }

    public List<Cita> findCitasByFiltro(Map<String, Object> params) {
        String urlCitas = configStorageService.getDbProperty("URL-CITAS");
        String urlCitasFilter = "/filtro";
        String fullUrl = urlCitas + urlCitasFilter;

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

        ResponseEntity<List<Cita>> responseEntity = restTemplate.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<Cita>>() {});

        return responseEntity.getBody();
    }

    @Override
    public Long getTotalFiltros(Map<String, Object> params) {
        String urlCitas = configStorageService.getDbProperty("URL-CITAS");
        String urlCitasFilter = "/filtro/total";
        String fullUrl = urlCitas + urlCitasFilter;

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

    public String createCita(Cita cita) {
        String urlCitas = configStorageService.getDbProperty("URL-CITAS");
        String urlCitasCreate = "/create";
        String fullUrl = urlCitas + urlCitasCreate;

        log.info("Enviando cita a: {}", fullUrl);

        // 1. Crear estructura manual con el formato exacto requerido
        Map<String, Object> citaJson = new LinkedHashMap<>(); // LinkedHashMap mantiene el orden
        citaJson.put("dia", new SimpleDateFormat("yyyy-MM-dd").format(cita.getDia()));
        citaJson.put("horaInicio", formatTime(cita.getHoraInicio()));
        citaJson.put("horaFinal", formatTime(cita.getHoraFinal()));
        citaJson.put("pagado", cita.getPagado());

        // Campos de auditoría
        citaJson.put("createTs", formatDateTime(cita.getCreateTs()));
        citaJson.put("createdBy", cita.getCreatedBy());
        citaJson.put("updateTs", formatDateTime(cita.getUpdateTs()));

        // Relaciones con estructura {id: 'valor'}
        citaJson.put("paciente", Collections.singletonMap(
                "id", cita.getPaciente() != null ? cita.getPaciente().getId().toString() : null));
        citaJson.put(
                "especialista", Collections.singletonMap("id", cita.getEspecialista() != null ? cita.getEspecialista().getId().toString() : null));
        citaJson.put(
                "servicio", Collections.singletonMap("id", cita.getServicio() != null ? cita.getServicio().getId().toString() : null));

        // 2. Configurar Gson
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        String jsonCita = gson.toJson(citaJson);
        System.out.println(jsonCita);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Traking-Id" , UUID.randomUUID().toString());

        HttpEntity<String> entity = new HttpEntity<>(jsonCita, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                fullUrl,
                HttpMethod.POST,
                entity,
                String.class);

        return responseEntity.getBody();
    }

    private String formatTime(Time time) {
        return time != null ? new SimpleDateFormat("HH:mm:ss").format(time) : null;
    }

    private String formatDateTime(Date date) {
        return date != null ? new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date) : null;
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

        log.info(urlCitas);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Traking-Id" , UUID.randomUUID().toString());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                urlCitas,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {});

        List<Cita> citas = new ArrayList<>();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Map<String, Object> citaData : responseEntity.getBody()) {
            Cita cita = dataManager.create(Cita.class);

            // 1. Asignar ID (si existe)
            if (citaData.get("id") != null) {
                cita.setId(UUID.fromString(citaData.get("id").toString()));
            }

            // 2. Asignar campos básicos
            // Dia
            if (citaData.get("dia") != null) {
                try {
                    if (citaData.get("dia") instanceof String) {
                        cita.setDia(dateFormat.parse((String) citaData.get("dia")));
                    } else if (citaData.get("dia") instanceof Date) {
                        cita.setDia((Date) citaData.get("dia"));
                    }
                } catch (ParseException e) {
                    log.error("Error parsing date", e);
                }
            }

            // Hora Inicio
            if (citaData.get("horaInicio") != null) {
                try {
                    Time horaInicio = new Time(timeFormat.parse(citaData.get("horaInicio").toString()).getTime());
                    cita.setHoraInicio(horaInicio);
                } catch (ParseException e) {
                    log.error("Error parsing horaInicio", e);
                }
            }

            // Hora Final
            if (citaData.get("horaFinal") != null) {
                try {
                    Time horaFinal = new Time(timeFormat.parse(citaData.get("horaFinal").toString()).getTime());
                    cita.setHoraFinal(horaFinal);
                } catch (ParseException e) {
                    log.error("Error parsing horaFinal", e);
                }
            }

            // Pagado
            if (citaData.get("pagado") != null) {
                cita.setPagado(Boolean.parseBoolean(citaData.get("pagado").toString()));
            } else {
                cita.setPagado(false);
            }

            // 3. Asignar relaciones (necesitas cargar las entidades completas)
            // Paciente
            if (citaData.get("pacienteId") != null) {
                UUID pacienteId = UUID.fromString(citaData.get("pacienteId").toString());
                LoadContext<Paciente> loadContext = LoadContext.create(Paciente.class)
                        .setId(pacienteId)
                        .setView("_minimal");
                Paciente paciente = dataManager.load(loadContext);
                cita.setPaciente(paciente);
            }

            // Especialista
            if (citaData.get("especialistaId") != null) {
                UUID especialistaId = UUID.fromString(citaData.get("especialistaId").toString());
                LoadContext<Especialista> loadContext = LoadContext.create(Especialista.class)
                        .setId(especialistaId)
                        .setView("especialista-view");
                Especialista especialista = dataManager.load(loadContext);
                cita.setEspecialista(especialista);
            }

            // Servicio
            if (citaData.get("servicioId") != null) {
                UUID servicioId = UUID.fromString(citaData.get("servicioId").toString());
                LoadContext<Servicio> loadContext = LoadContext.create(Servicio.class)
                        .setId(servicioId)
                        .setView("_minimal");
                Servicio servicio = dataManager.load(loadContext);
                cita.setServicio(servicio);
            }

            citas.add(cita);
        }

        return citas;
    }

    @Override
    public void deleteLogicalDeletedCitas() {
        String urlCitas = configStorageService.getDbProperty("URL-CITAS");
        String endPoint = "/citas/delete-logical";
        String url = urlCitas + endPoint;

        log.info(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Traking-Id" , UUID.randomUUID().toString());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                String.class);
    }
}