package com.company.clinic.entity.pacientes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum TipoDocumento implements EnumClass<String> {
    DNI("DNI"),
    NIE("NIE"),
    NIF("NIF");

    private String id;

    TipoDocumento(String value) {
        this.id = value;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    @Nullable
    @JsonCreator
    public static TipoDocumento fromId(String id) {
        for (TipoDocumento at : TipoDocumento.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
