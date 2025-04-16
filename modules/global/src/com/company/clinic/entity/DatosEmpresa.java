package com.company.clinic.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "CLINIC_DATOS_EMPRESA")
@Entity(name = "clinic_DatosEmpresa")
public class DatosEmpresa extends StandardEntity {
    private static final long serialVersionUID = 4533828163285666987L;
    @NotNull
    @Column(name = "NOMBRE", nullable = false, length = 150)
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "NIF", length = 9)
    private String nif;
    @Column(name = "TELEFONO", length = 9)
    private String telefono;
    @Column(name = "EMAIL", length = 75)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}