package com.company.clinic.web.screens.paciente;

import com.company.clinic.entity.Genero;
import com.company.clinic.entity.Provincia;
import com.company.clinic.entity.pacientes.*;
import com.company.clinic.service.paciente.PacienteService;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.util.OperationResult;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import javax.swing.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    @Inject
    private UserSession userSession;

    @Inject
    private Notifications notifications;

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
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
        Date fechaHoraEspana = Date.from(zonedDateTime.toInstant());

        if ("crear".equals(modoPantalla)) {
                if (nombreField.getValue() == null || apellidosField.getValue() == null ||
                    fechaNacimientoField.getValue() == null || generoField.getValue() == null) {

                    notifications.create(Notifications.NotificationType.WARNING)
                            .withCaption("Por favor, completa todos los campos obligatorios.")
                            .withPosition(Notifications.Position.BOTTOM_RIGHT)
                            .show();

                    return;
                }
                // lógica para modo creación
                // Paciente
                Paciente paciente = dataManager.create(Paciente.class);
                paciente.setNombre(nombreField.getValue());
                paciente.setApellidos(apellidosField.getValue());
                paciente.setFechaNacimiento(fechaNacimientoField.getValue());
                Genero genero = generoField.getValue() != null ?
                        Genero.fromId(generoField.getValue().getId()): null;
                paciente.setGenero(genero);
                paciente.setCreateTs(fechaHoraEspana);
                paciente.setCreatedBy(userSession.getUser().getLogin());
                paciente.setUpdateTs(fechaHoraEspana);

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
                datosAdministrativos.setCreateTs(fechaHoraEspana);
                datosAdministrativos.setCreatedBy(userSession.getUser().getLogin());
                datosAdministrativos.setUpdateTs(fechaHoraEspana);

                // Datos contacto
                DatosContacto datosContacto = dataManager.create(DatosContacto.class);
                datosContacto.setTelefono(telefonoField.getValue());
                datosContacto.setEmail(emailField.getValue());
                datosContacto.setCalle(calleField.getValue());
                datosContacto.setNumero(numeroField.getValue());
                datosContacto.setCiudad(ciudadField.getValue());
                Provincia provinciaContacto = provinciaContactoField.getValue() != null ?
                        Provincia.fromId(provinciaContactoField.getValue().getId()) : null;
                datosContacto.setProvincia(provinciaContacto);
                datosContacto.setCodigoPostal(codigoPostalField.getValue());
                datosContacto.setCreateTs(fechaHoraEspana);
                datosContacto.setCreatedBy(userSession.getUser().getLogin());
                datosContacto.setUpdateTs(fechaHoraEspana);

                // Datos facturacion
                DatosFacturacion datosFacturacion = dataManager.create(DatosFacturacion.class);
                datosFacturacion.setNif(nifField.getValue());
                datosFacturacion.setNombre(nombreFacturacionField.getValue());
                datosFacturacion.setApellidos(apellidosFacturacionField.getValue());
                datosFacturacion.setCalle(calleFacturacionField.getValue());
                datosFacturacion.setNumero(numeroFacturacionField.getValue());
                datosFacturacion.setCiudad(ciudadFacturacionField.getValue());
                Provincia provinciaFacturacion = provinciaFacturacionField.getValue() != null ?
                        Provincia.fromId(provinciaFacturacionField.getValue().getId()) : null;
                datosFacturacion.setProvincia(provinciaFacturacion);
                datosFacturacion.setCreateTs(fechaHoraEspana);
                datosFacturacion.setCreatedBy(userSession.getUser().getLogin());
                datosFacturacion.setUpdateTs(fechaHoraEspana);

                paciente.setDatosAdministrativos(datosAdministrativos);

                paciente.setDatosContacto(datosContacto);

                paciente.setDatosFacturacion(datosFacturacion);

                try {
                    pacienteService.createPaciente(paciente);
                    notifications.create()
                            .withCaption("¡Paciente guardado correctamente!")
                            .withPosition(Notifications.Position.BOTTOM_RIGHT)
                            .withType(Notifications.NotificationType.TRAY)
                            .show();

                    closeWithDiscard();

                } catch (Exception e) {
                    System.out.println("Error insertando paciente");
                }

            } else if ("editar".equals(modoPantalla)) {
                Paciente paciente = getEditedEntity();

            System.out.println("Paciente ID: " + paciente.getId());
            System.out.println("CreateTs: " + paciente.getCreateTs());
            System.out.println("Version: " + paciente.getVersion());

                paciente.setNombre(nombreField.getValue());
                paciente.setApellidos(apellidosField.getValue());
                paciente.setFechaNacimiento(fechaNacimientoField.getValue());
                Genero genero = generoField.getValue() != null ?
                        Genero.fromId(generoField.getValue().getId()): null;
                paciente.setGenero(genero);
                paciente.setCreateTs(paciente.getCreateTs());
                paciente.setCreatedBy(paciente.getCreatedBy());
                paciente.setUpdateTs(fechaHoraEspana);
                paciente.setUpdatedBy(userSession.getUser().getLogin());
                paciente.setVersion(paciente.getVersion());

                // Datos administrativos
                DatosAdministrativos datosAdministrativos= metadata.create(DatosAdministrativos.class);
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
                datosAdministrativos.setCreateTs(paciente.getDatosAdministrativos().getCreateTs());
                datosAdministrativos.setCreatedBy(paciente.getDatosAdministrativos().getCreatedBy());
                datosAdministrativos.setUpdateTs(fechaHoraEspana);
                datosAdministrativos.setUpdatedBy(userSession.getUser().getLogin());
                datosAdministrativos.setVersion(paciente.getDatosAdministrativos().getVersion());

                // Datos contacto
                DatosContacto datosContacto = dataManager.create(DatosContacto.class);
                datosContacto.setTelefono(telefonoField.getValue());
                datosContacto.setEmail(emailField.getValue());
                datosContacto.setCalle(calleField.getValue());
                datosContacto.setNumero(numeroField.getValue());
                datosContacto.setCiudad(ciudadField.getValue());
                Provincia provinciaContacto = provinciaContactoField.getValue() != null ?
                        Provincia.fromId(provinciaContactoField.getValue().getId()) : null;
                datosContacto.setProvincia(provinciaContacto);
                datosContacto.setCodigoPostal(codigoPostalField.getValue());
                datosContacto.setCreateTs(paciente.getDatosContacto().getCreateTs());
                datosContacto.setCreatedBy(paciente.getDatosContacto().getCreatedBy());
                datosContacto.setUpdateTs(fechaHoraEspana);
                datosContacto.setUpdatedBy(userSession.getUser().getLogin());
                datosContacto.setVersion(paciente.getDatosContacto().getVersion());

                // Datos facturacion
                DatosFacturacion datosFacturacion = dataManager.create(DatosFacturacion.class);
                datosFacturacion.setNif(nifField.getValue());
                datosFacturacion.setNombre(nombreFacturacionField.getValue());
                datosFacturacion.setApellidos(apellidosFacturacionField.getValue());
                datosFacturacion.setCalle(calleFacturacionField.getValue());
                datosFacturacion.setNumero(numeroFacturacionField.getValue());
                datosFacturacion.setCiudad(ciudadFacturacionField.getValue());
                Provincia provinciaFacturacion = provinciaFacturacionField.getValue() != null ?
                        Provincia.fromId(provinciaFacturacionField.getValue().getId()) : null;
                datosFacturacion.setProvincia(provinciaFacturacion);
                datosFacturacion.setCreateTs(paciente.getDatosFacturacion().getCreateTs());
                datosFacturacion.setCreatedBy(paciente.getDatosFacturacion().getCreatedBy());
                datosFacturacion.setUpdateTs(fechaHoraEspana);
                datosFacturacion.setUpdatedBy(userSession.getUser().getLogin());
                datosFacturacion.setVersion(paciente.getDatosFacturacion().getVersion());


            System.out.println("Tipo de dato de fechaHoraEspana: " + fechaHoraEspana.getClass().getName());

                paciente.setDatosAdministrativos(datosAdministrativos);

                paciente.setDatosContacto(datosContacto);

                paciente.setDatosFacturacion(datosFacturacion);

                try {
                    pacienteService.updatePaciente(paciente);
                    notifications.create()
                            .withCaption("¡Paciente guardado correctamente!")
                            .withPosition(Notifications.Position.BOTTOM_RIGHT)
                            .withType(Notifications.NotificationType.TRAY)
                            .show();
                    closeWithDiscard();

                } catch (Exception e) {
                    System.out.println("Error insertando paciente");
                }
            }
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

    }




}