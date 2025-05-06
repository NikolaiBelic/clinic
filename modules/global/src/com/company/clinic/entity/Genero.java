package com.company.clinic.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Genero implements EnumClass<String> {

    HOMBRE("Hombre"),
    MUJER("Mujer"),
    NO_BINARIO("No binario");

    private String id;

    Genero(String value) {
        this.id = value;
    }

    @JsonValue
    public String getId() {
        return id;
    }


    @Nullable
    @JsonCreator
    public static Genero fromId(String id) {
        for (Genero at : Genero.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}