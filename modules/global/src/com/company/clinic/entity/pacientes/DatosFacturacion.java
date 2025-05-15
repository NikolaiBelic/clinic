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
    private String nombre;

    @Column(name = "APELLIDOS", length = 100)
    private String apellidos;

    @Column(name = "CALLE", length = 150)
    private String calle;

    @Column(name = "NUMERO", length = 50)
    private String numero;

    @Column(name = "CIUDAD", length = 50)
    private String ciudad;

    @Column(name = "PROVINCIA")
    private String provincia;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}