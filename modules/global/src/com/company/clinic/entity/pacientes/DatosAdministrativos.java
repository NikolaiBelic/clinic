package com.company.clinic.entity.pacientes;

import com.company.clinic.entity.Empresa;
import com.company.clinic.entity.Provincia;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "CLINIC_DATOS_ADMINISTRATIVOS")
@Entity(name = "clinic_DatosAdministrativos")
public class DatosAdministrativos extends StandardEntity {
    private static final long serialVersionUID = -7848233555125050829L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID")
    private Paciente paciente;

    @Column(name = "ESTADO_PACIENTE")
    private String estadoPaciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONSABLE_TRATAMIENTO_DATOS_ID")
    private Empresa responsableTratamientoDatos;

    @Column(name = "CIUDAD_NACIMIENTO", length = 50)
    private String ciudadNacimiento;

    @Column(name = "NACIONALIDAD", length = 50)
    private String nacionalidad;

    @Column(name = "PROVINCIA_NACIMIENTO")
    private String provinciaNacimiento;

    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;

    @Column(name = "NUMERO_DOCUMENTO", unique = true, length = 9)
    private String numeroDocumento;

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento == null ? null : TipoDocumento.fromId(tipoDocumento);
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento == null ? null : tipoDocumento.getId();
    }

    public Provincia getProvinciaNacimiento() {
        return provinciaNacimiento == null ? null : Provincia.fromId(provinciaNacimiento);
    }

    public void setProvinciaNacimiento(Provincia provinciaNacimiento) {
        this.provinciaNacimiento = provinciaNacimiento == null ? null : provinciaNacimiento.getId();
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setCiudadNacimiento(String ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }

    public EstadoPaciente getEstadoPaciente() {
        return estadoPaciente == null ? null : EstadoPaciente.fromId(estadoPaciente);
    }

    public void setEstadoPaciente(EstadoPaciente estadoPaciente) {
        this.estadoPaciente = estadoPaciente == null ? null : estadoPaciente.getId();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Empresa getResponsableTratamientoDatos() {
        return responsableTratamientoDatos;
    }

    public void setResponsableTratamientoDatos(Empresa responsableTratamientoDatos) {
        this.responsableTratamientoDatos = responsableTratamientoDatos;
    }
}