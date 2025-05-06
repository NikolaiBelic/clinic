package com.company.clinic.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Especialidad implements EnumClass<String> {

    PSICOLOGIA("Psicología"),
    NUTRICION("Nutrición"),
    PODOLOGIA("Podología"),
    FISIOTERAPIA("Fisioterapia"),
    OFTALMOLOGIA("Oftalmología"),
    DERMATOLOGIA("Dermatología");

    private String id;

    Especialidad(String value) {
        this.id = value;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    @Nullable
    @JsonCreator
    public static Especialidad fromId(String id) {
        for (Especialidad at : Especialidad.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}