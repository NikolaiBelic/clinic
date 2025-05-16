package com.company.clinic.service.paciente;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.pacientes.Paciente;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface PacienteService {
    String NAME = "clinic_PacienteService";

    public List<Paciente> getAllPacientes();

    public List<Paciente> findPacientesByFilter(
            String nombre,
            String apellidos,
            java.sql.Date fechaNacimiento,
            String genero,
            String estadoPaciente,
            String ciudadNacimiento,
            String nacionalidad,
            String provinciaAdministrativo,
            String tipoDocumento,
            String numeroDocumento
    );

    public List<Paciente> findPacientesByFiltro(Map<String, Object> params);

    public Long getTotalFiltros(Map<String, Object> params);

    public String createPaciente(Paciente paciente);

    public String updatePaciente(Paciente paciente);
}