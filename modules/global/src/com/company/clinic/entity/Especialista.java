package com.company.clinic.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "CLINIC_ESPECIALISTA")
@Entity(name = "clinic_Especialista")
@NamePattern("%s - %s | nombre, especialidad")
public class Especialista extends StandardEntity {
    private static final long serialVersionUID = -2006171333942161248L;
    @Column(name = "NOMBRE", length = 30)
    private String nombre;
    @Column(name = "APELLIDOS", length = 50)
    private String apellidos;
    @NotNull
    @Column(name = "DNI", nullable = false, unique = true, length = 9)
    private String dni;
    @NotNull
    @Column(name = "ESPECIALIDAD", nullable = false)
    private String especialidad;

    public Especialidad getEspecialidad() {
        return especialidad == null ? null : Especialidad.fromId(especialidad);
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad == null ? null : especialidad.getId();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}