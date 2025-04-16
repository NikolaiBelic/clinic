package com.company.clinic.entity;

import com.company.clinic.entity.pacientes.Paciente;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "CLINIC_EXPEDIENTE")
@Entity(name = "clinic_Expediente")
public class Expediente extends StandardEntity {
    private static final long serialVersionUID = 2728351244518361217L;
    @Column(name = "TITULO", length = 75)
    private String titulo;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESPECIALISTA_ID")
    private Especialista especialista;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID")
    private Paciente paciente;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}