package com.company.clinic.entity.pacientes;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum EstadoPaciente implements EnumClass<String> {

    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private String id;

    EstadoPaciente(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EstadoPaciente fromId(String id) {
        for (EstadoPaciente at : EstadoPaciente.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}