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
    private String calle;

    @Column(name = "NUMERO", length = 50)
    private String numero;

    @Column(name = "CODIGO_POSTAL", length = 5)
    private String codigoPostal;

    @Column(name = "CIUDAD", length = 30)
    private String ciudad;

    @Column(name = "PROVINCIA")
    private String provincia;

    // Modificar getter/setter:
    public Provincia getProvincia() {
        return provincia == null ? null : Provincia.fromId(provincia);
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia == null ? null : provincia.getId();
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
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