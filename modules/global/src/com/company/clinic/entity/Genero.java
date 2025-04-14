package com.company.clinic.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Genero implements EnumClass<String> {

    HOMBRE("HOMBRE"),
    MUJER("MUJER"),
    NO_BINARIO("NO_BINARIO");

    private String id;

    Genero(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Genero fromId(String id) {
        for (Genero at : Genero.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}