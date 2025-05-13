package com.company.clinic.entity.pacientes;

import com.company.clinic.entity.Provincia;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "CLINIC_DATOS_CONTACTO")
@Entity(name = "clinic_DatosContacto")
public class DatosContacto extends StandardEntity {
    private static final long serialVersionUID = 2593889447049544244L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACIENTE_ID")
    private Paciente paciente;

    @Column(name = "TELEFONO", length = 9)
    private String telefono;

    @Column(name = "EMAIL", length = 60)
    private String email;

    @Column(name = "CALLE", length = 150)
    private String calleContacto;

    @Column(name = "NUMERO", length = 50)
    private String numeroContacto;

    @Column(name = "CODIGO_POSTAL", length = 5)
    private String codigoPostal;

    @Column(name = "CIUDAD", length = 30)
    private String ciudadContacto;

    @Column(name = "PROVINCIA")
    private String provinciaContacto;

    // Modificar getter/setter:
    public Provincia getProvinciaContacto() {
        return provinciaContacto == null ? null : Provincia.fromId(provinciaContacto);
    }

    public void setProvinciaContacto(Provincia provincia) {
        this.provinciaContacto = provincia == null ? null : provincia.getId();
    }

    public String getCiudadContacto() {
        return ciudadContacto;
    }

    public void setCiudadContacto(String ciudad) {
        this.ciudadContacto = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numero) {
        this.numeroContacto = numero;
    }

    public String getCalleContacto() {
        return calleContacto;
    }

    public void setCalleContacto(String calle) {
        this.calleContacto = calle;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

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
}