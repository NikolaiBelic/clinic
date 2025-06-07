package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.pacientes.Paciente;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CitaService {
    String NAME = "clinic_CitaService";

    List<Cita> getAllCitas();

    public List<Cita> getCitasCalendario(Map<String, Object> params);

    List<Cita> getCitasPorEspecialista(UUID id);

    Cita getCita(UUID id);

    public List<Cita> getAllCitasMS ();

    public void deleteLogicalDeletedCitas();

    public List<Cita> findCitasByFiltro(Map<String, Object> params);

    public Long getTotalFiltros(Map<String, Object> params);

    public String createCita(Cita cita);

    public String updateCita(Cita cita);

    public void softDeleteCitas(Map<String, Object> citas);

    public Boolean checkSolapamiento(Cita cita);
}