package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CitaService {
    String NAME = "clinic_CitaService";

    List<Cita> getAllCitas();

    List<Cita> getCitasPorEspecialista(UUID id);

    Cita getCita(UUID id);

    public List<Cita> getAllCitasMS ();

    public void deleteLogicalDeletedCitas();

    public List<Cita> findCitasByFiltro(Map<String, Object> params);
}