package com.company.clinic.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "CLINIC_SERVICIO")
@Entity(name = "clinic_Servicio")
@NamePattern("%s | descripcion")
public class Servicio extends StandardEntity {
    private static final long serialVersionUID = 7703852359843597933L;
    @NotNull
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
    @Column(name = "ESPECIALIDAD")
    private String especialidad;
    @Column(name = "PRECIO")
    private Double precio;

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Especialidad getEspecialidad() {
        return especialidad == null ? null : Especialidad.fromId(especialidad);
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad == null ? null : especialidad.getId();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}