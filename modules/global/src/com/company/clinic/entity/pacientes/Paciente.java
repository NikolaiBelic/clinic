package com.company.clinic.entity.pacientes;

import com.company.clinic.entity.Genero;
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

    @NotNull
    @Column(name = "NOMBRE", length = 30)
    private String nombre;

    @NotNull
    @Column(name = "APELLIDOS", length = 50)
    private String apellidos;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @NotNull
    @Column(name = "GENERO", nullable = false)
    private String genero;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "paciente")
    private DatosAdministrativos datosAdministrativos;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "paciente")
    private DatosContacto datosContacto;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "paciente")
    private DatosFacturacion datosFacturacion;

    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public DatosContacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(DatosContacto datosContacto) {
        this.datosContacto = datosContacto;
    }

    public DatosAdministrativos getDatosAdministrativos() {
        return datosAdministrativos;
    }

    public void setDatosAdministrativos(DatosAdministrativos datosAdministrativos) {
        this.datosAdministrativos = datosAdministrativos;
    }

    public Genero getGenero() {
        return genero == null ? null : Genero.fromId(genero);
    }

    public void setGenero(Genero genero) {
        this.genero = genero == null ? null : genero.getId();
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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