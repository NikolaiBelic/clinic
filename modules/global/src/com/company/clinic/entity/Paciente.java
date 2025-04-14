package com.company.clinic.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "CLINIC_PACIENTE")
@Entity(name = "clinic_Paciente")
@NamePattern("%s %s | nombre, apellidos")
public class Paciente extends StandardEntity {
    private static final long serialVersionUID = 4318987470490237086L;
    @Column(name = "NOMBRE", length = 30)
    private String nombre;
    @Column(name = "APELLIDOS", length = 50)
    private String apellidos;
    @Column(name = "EMAIL", length = 50)
    private String email;
    @Column(name = "EDAD")
    private Integer edad;
    @NotNull
    @Column(name = "DNI", nullable = false, unique = true, length = 9)
    private String dni;
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @NotNull
    @Column(name = "GENERO", nullable = false)
    private String genero;

    public Genero getGenero() {
        return genero == null ? null : Genero.fromId(genero);
    }

    public void setGenero(Genero genero) {
        this.genero = genero == null ? null : genero.getId();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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