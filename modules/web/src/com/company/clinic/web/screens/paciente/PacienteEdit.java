package com.company.clinic.web.screens.paciente;

import com.company.clinic.entity.Genero;
import com.company.clinic.entity.Provincia;
import com.company.clinic.entity.pacientes.*;
import com.company.clinic.service.paciente.PacienteService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import javax.swing.*;
import java.util.Date;
import java.util.Map;

@UiController("clinic_Paciente.edit")
@UiDescriptor("paciente-edit.xml")
@EditedEntityContainer("pacienteDc")
@LoadDataBeforeShow
public class PacienteEdit extends StandardEditor<Paciente> {

    @Inject
    private TextField<String> nombreField;

    @Inject
    private TextField<String> apellidosField;

    @Inject
    private DateField<Date> fechaNacimientoField;

    @Inject
    private LookupField<Genero> generoField;

    @Inject
    private LookupField<EstadoPaciente> estadoPacienteField;

    @Inject
    private TextField<String> ciudadNacimientoField;

    @Inject
    private TextField<String> nacionalidadField;

    @Inject
    private LookupField<Provincia> provinciaAdministrativoField;

    @Inject
    private LookupField<TipoDocumento> tipoDocumentoField;

    @Inject
    private TextField<String> numeroDocumentoField;

    @Inject
    private TextField<String> telefonoField;

    @Inject
    private TextField<String> emailField;

    @Inject
    private TextField<String> calleField;

    @Inject
    private TextField<String> numeroField;

    @Inject
    private TextField<String> codigoPostalField;

    @Inject
    private TextField<String> ciudadField;

    @Inject
    private LookupField<Provincia> provinciaContactoField;

    @Inject
    private TextField<String> nifField;

    @Inject
    private TextField<String> nombreFacturacionField;

    @Inject
    private TextField<String> apellidosFacturacionField;

    @Inject
    private TextField<String> calleFacturacionField;

    @Inject
    private TextField<String> numeroFacturacionField;

    @Inject
    private TextField<String> ciudadFacturacionField;

    @Inject
    private LookupField<Provincia> provinciaFacturacionField;

    @Inject
    private InstanceContainer<Paciente> pacienteDc;

    @Inject
    private Button insertBtn;

    @Inject
    private Metadata metadata;

    @Inject
    private Form formDf;

    @Inject
    private PacienteService pacienteService;

    private String modoPantalla;

    @Inject
    private DataManager dataManager;

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions screenOptions = event.getOptions();

        if (screenOptions instanceof MapScreenOptions) {
            Map<String, Object> params = ((MapScreenOptions) screenOptions).getParams();
            modoPantalla = (String) params.get("modo");

            if ("crear".equals(modoPantalla)) {
                // lógica para modo creación
                insertBtn.setCaption("Crear");
            } else if ("editar".equals(modoPantalla)) {
                // lógica para modo edición
                insertBtn.setCaption("Editar");
            }
        }
    }

    @Subscribe("insertBtn")
    public void onInsertBtnClick(Button.ClickEvent event) {
            if ("crear".equals(modoPantalla)) {
                // lógica para modo creación
                // Paciente
                Paciente paciente = dataManager.create(Paciente.class);
                paciente.setNombre(nombreField.getValue());
                paciente.setApellidos(apellidosField.getValue());
                paciente.setFechaNacimiento(fechaNacimientoField.getValue());
                Genero genero = generoField.getValue() != null ?
                        Genero.fromId(generoField.getValue().getId()): null;
                paciente.setGenero(genero);
                System.out.println("PG: " + genero);

                // Datos administrativos
                DatosAdministrativos datosAdministrativos= dataManager.create(DatosAdministrativos.class);
                TipoDocumento tipoDocumento = tipoDocumentoField.getValue() != null ?
                        TipoDocumento.fromId(tipoDocumentoField.getValue().getId()) : null;
                datosAdministrativos.setTipoDocumento(tipoDocumento);
                datosAdministrativos.setNumeroDocumento(numeroDocumentoField.getValue());
                datosAdministrativos.setNacionalidad(nacionalidadField.getValue());
                EstadoPaciente estadoPaciente = estadoPacienteField.getValue() != null ?
                        EstadoPaciente.fromId(estadoPacienteField.getValue().getId()) : null;
                datosAdministrativos.setEstadoPaciente(estadoPaciente);
                datosAdministrativos.setCiudadNacimiento(ciudadNacimientoField.getValue());
                Provincia provinciaNacimiento = provinciaAdministrativoField.getValue() != null ?
                        Provincia.fromId(provinciaAdministrativoField.getValue().getId()) : null;
                datosAdministrativos.setProvinciaNacimiento(provinciaNacimiento);

                // Datos contacto
                DatosContacto datosContacto = dataManager.create(DatosContacto.class);
                datosContacto.setTelefono(telefonoField.getValue());
                datosContacto.setEmail(emailField.getValue());
                datosContacto.setCalleContacto(calleField.getValue());
                datosContacto.setNumeroContacto(numeroField.getValue());
                datosContacto.setCiudadContacto(ciudadField.getValue());
                Provincia provinciaContacto = provinciaContactoField.getValue() != null ?
                        Provincia.fromId(provinciaContactoField.getValue().getId()) : null;
                datosContacto.setProvinciaContacto(provinciaContacto);
                datosContacto.setCodigoPostal(codigoPostalField.getValue());

                // Datos facturacion
                DatosFacturacion datosFacturacion = dataManager.create(DatosFacturacion.class);
                datosFacturacion.setNif(nifField.getValue());
                datosFacturacion.setNombreFacturacion(nombreFacturacionField.getValue());
                datosFacturacion.setApellidosFacturacion(apellidosFacturacionField.getValue());
                datosFacturacion.setCalleFacturacion(calleFacturacionField.getValue());
                datosFacturacion.setNumeroFacturacion(numeroFacturacionField.getValue());
                datosFacturacion.setCiudadFacturacion(ciudadFacturacionField.getValue());
                Provincia provinciaFacturacion = provinciaFacturacionField.getValue() != null ?
                        Provincia.fromId(provinciaFacturacionField.getValue().getId()) : null;
                datosFacturacion.setProvinciaFacturacion(provinciaFacturacion);


                paciente.setDatosAdministrativos(datosAdministrativos);
                paciente.setDatosContacto(datosContacto);
                paciente.setDatosFacturacion(datosFacturacion);

                try {
                    pacienteService.createPaciente(paciente);
                } catch (Exception e) {
                    System.out.println("Error insertando paciente");
                }

            } else if ("editar".equals(modoPantalla)) {
                // lógica para modo edición
            }
        System.out.println("========== Paciente ========");
        System.out.println("Nombre: " + nombreField.getValue());
        System.out.println("Apellidos: " + apellidosField.getValue());
        System.out.println("Fecha de Nacimiento: " + fechaNacimientoField.getValue());
        System.out.println("Género: " + generoField.getValue().getId());

        /*System.out.println("========== Datos Administrativos ========");
        System.out.println("Estado Paciente: " + estadoPacienteField.getValue().getId());
        System.out.println("Ciudad Nacimiento: " + ciudadNacimientoField.getValue());
        System.out.println("Nacionalidad: " + nacionalidadField.getValue());
        System.out.println("Provincia Nacimiento: " + provinciaAdministrativoField.getValue().getId());
        System.out.println("Tipo Documento: " + tipoDocumentoField.getValue().getId());
        System.out.println("Número Documento: " + numeroDocumentoField.getValue());

        System.out.println("========== Datos de Contacto ========");
        System.out.println("Teléfono: " + telefonoField.getValue());
        System.out.println("Email: " + emailField.getValue());
        System.out.println("Calle: " + calleField.getValue());
        System.out.println("Número: " + numeroField.getValue());
        System.out.println("Código Postal: " + codigoPostalField.getValue());
        System.out.println("Ciudad: " + ciudadField.getValue());
        System.out.println("Provincia: " + provinciaContactoField.getValue().getId());

        System.out.println("========== Datos de Facturación ========");
        System.out.println("NIF: " + nifField.getValue());
        System.out.println("Nombre Facturación: " + nombreFacturacionField.getValue());
        System.out.println("Apellidos Facturación: " + apellidosFacturacionField.getValue());
        System.out.println("Calle Facturación: " + calleFacturacionField.getValue());
        System.out.println("Número Facturación: " + numeroFacturacionField.getValue());
        System.out.println("Ciudad Facturación: " + ciudadFacturacionField.getValue());
        System.out.println("Provincia Facturación: " + provinciaFacturacionField.getValue().getId());*/
    }

    @Subscribe("mismosDatosContactoFacturacion")
    public void onMismosDatosContactoFacturacionValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue().equals(true)) {
            formDf.setVisible(false);
        } else {
            formDf.setVisible(true);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        Paciente paciente = pacienteDc.getItem();
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        Paciente paciente = getEditedEntity();

        if (paciente.getDatosAdministrativos() == null) {
            DatosAdministrativos datos = metadata.create(DatosAdministrativos.class);
            datos.setPaciente(paciente); // importante para mantener la relación bidireccional
            paciente.setDatosAdministrativos(datos);
        }

        if (paciente.getDatosContacto() == null) {
            DatosContacto datos = metadata.create(DatosContacto.class);
            datos.setPaciente(paciente); // importante para mantener la relación bidireccional
            paciente.setDatosContacto(datos);
        }

        if (paciente.getDatosFacturacion() == null) {
            DatosFacturacion datos = metadata.create(DatosFacturacion.class);
            datos.setPaciente(paciente); // importante para mantener la relación bidireccional
            paciente.setDatosFacturacion(datos);
        }
    }




}