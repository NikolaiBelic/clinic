package com.company.clinic.entity;

import com.company.clinic.entity.pacientes.Paciente;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "CLINIC_FACTURA")
@Entity(name = "clinic_Factura")
public class Factura extends StandardEntity {
    private static final long serialVersionUID = 1792793408913496882L;

    @Column(name = "NUMERO_FACTURA", unique = true)
    public String numeroFactura;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_EMISION", nullable = false)
    private Date fechaEmision;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CITA_ID")
    private Cita cita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    private FileDescriptor file;

    @Column(name = "EXENCION_IVA", length = 100)
    private String exencionIva;


    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public String getExencionIva() {
        return exencionIva;
    }

    public void setExencionIva(String exencionIva) {
        this.exencionIva = exencionIva;
    }

    public FileDescriptor getFile() {
        return file;
    }

    public void setFile(FileDescriptor file) {
        this.file = file;
    }
}