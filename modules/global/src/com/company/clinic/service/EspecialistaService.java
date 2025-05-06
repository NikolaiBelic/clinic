package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.Especialista;

import java.util.List;

public interface EspecialistaService {
    String NAME = "clinic_EspecialistaService";

    List<Especialista> getEspecialistas();
}