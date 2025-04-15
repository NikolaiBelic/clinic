package com.company.clinic.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.HasUuid;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@MetaClass(name = "clinic_CitaDTO")
public class CitaDTO extends BaseUuidEntity implements HasUuid {
    private static final long serialVersionUID = 1292380527401018228L;

    private UUID uuid;

    @MetaProperty
    private Date dia;

    @MetaProperty
    private UUID pacienteId;

    @MetaProperty
    private UUID especialistaId;

    @MetaProperty
    private UUID servicioId;

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public UUID getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(UUID pacienteId) {
        this.pacienteId = pacienteId;
    }

    public UUID getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(UUID especialistaId) {
        this.especialistaId = especialistaId;
    }

    public UUID getServicioId() {
        return servicioId;
    }

    public void setServicioId(UUID servicioId) {
        this.servicioId = servicioId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}