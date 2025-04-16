package com.company.clinic.entity;

import com.company.clinic.entity.pacientes.Paciente;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

@Table(name = "CLINIC_CITA")
@Entity(name = "clinic_Cita")
public class Cita extends StandardEntity {
    private static final long serialVersionUID = -6306275524571543633L;

    @Temporal(TemporalType.DATE)
    @Column(name = "DIA", nullable = false)
    @NotNull
    private Date dia;
    @Column(name = "HORA_INICIO", nullable = false)
    @NotNull
    private Time horaInicio;
    @NotNull
    @Column(name = "HORA_FINAL", nullable = false)
    private Time horaFinal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID")
    private Paciente paciente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESPECIALISTA_ID")
    private Especialista especialista;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERVICIO_ID")
    private Servicio servicio;
    @NotNull
    @Column(name = "PAGADO", nullable = false)
    private Boolean pagado = false;

    public Time getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + getId() +
                "horaInicio=" + horaInicio +
                ", horaFinal=" + horaFinal +
                ", paciente=" + (paciente != null ? paciente.getNombre() + " " + paciente.getApellidos() : "N/A") +
                ", especialista=" + (especialista != null ? especialista.getNombre() + " " + especialista.getApellidos() : "N/A") +
                ", servicio=" + (servicio != null ? servicio.getDescripcion() : "N/A") +
                ", pagado=" + pagado +
                '}';
    }


}