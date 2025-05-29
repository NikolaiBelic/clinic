package com.company.clinic.service;

import com.company.clinic.entity.Especialista;

import java.util.List;
import java.util.Map;

public interface EspecialistaService {
    String NAME = "clinic_EspecialistaService";

    public List<Especialista> getEspecialistas();

    public List<Especialista> findEspecialistasByFiltro(Map<String, Object> params);

    public Long getTotalFiltros(Map<String, Object> params);
}