package com.company.clinic.service.paciente;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.pacientes.Paciente;

import java.util.List;

public interface PacienteService {
    String NAME = "clinic_PacienteService";

    public List<Paciente> getAllPacientes();
}