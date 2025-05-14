package com.company.clinic.entity.pacientes;

import com.company.clinic.entity.Provincia;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "CLINIC_DATOS_FACTURACION")
@Entity(name = "clinic_DatosFacturacion")
public class DatosFacturacion extends StandardEntity {
    private static final long serialVersionUID = 3355345576781141606L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID")
    private Paciente paciente;

    @Column(name = "NIF", length = 9)
    private String nif;

    @Column(name = "NOMBRE", length = 50)
    private String nombreFacturacion;

    @Column(name = "APELLIDOS", length = 100)
    private String apellidosFacturacion;

    @Column(name = "CALLE", length = 150)
    private String calleFacturacion;

    @Column(name = "NUMERO", length = 50)
    private String numeroFacturacion;

    @Column(name = "CIUDAD", length = 50)
    private String ciudadFacturacion;

    @Column(name = "PROVINCIA")
    private String provinciaFacturacion;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Provincia getProvinciaFacturacion() {
        return provinciaFacturacion == null ? null : Provincia.fromId(provinciaFacturacion);
    }

    public void setProvinciaFacturacion(Provincia provincia) {
        this.provinciaFacturacion = provincia == null ? null : provincia.getId();
    }

    public String getCiudadFacturacion() {
        return ciudadFacturacion;
    }

    public void setCiudadFacturacion(String ciudad) {
        this.ciudadFacturacion = ciudad;
    }

    public String getNumeroFacturacion() {
        return numeroFacturacion;
    }

    public void setNumeroFacturacion(String numero) {
        this.numeroFacturacion = numero;
    }

    public String getCalleFacturacion() {
        return calleFacturacion;
    }

    public void setCalleFacturacion(String calle) {
        this.calleFacturacion = calle;
    }

    public String getApellidosFacturacion() {
        return apellidosFacturacion;
    }

    public void setApellidosFacturacion(String apellidos) {
        this.apellidosFacturacion = apellidos;
    }

    public String getNombreFacturacion() {
        return nombreFacturacion;
    }

    public void setNombreFacturacion(String nombre) {
        this.nombreFacturacion = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}