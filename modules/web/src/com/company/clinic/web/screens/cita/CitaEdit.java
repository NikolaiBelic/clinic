package com.company.clinic.web.screens.cita;

import com.company.clinic.entity.*;
import com.company.clinic.entity.pacientes.*;
import com.company.clinic.service.CitaService;
import com.company.clinic.service.FacturaService;
import com.company.clinic.web.screens.servicio.ServicioBrowse;
import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.reports.app.service.ReportService;
import com.haulmont.reports.entity.Report;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Time;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@UiController("clinic_Cita.edit")
@UiDescriptor("cita-edit.xml")
@EditedEntityContainer("citaDc")
@LoadDataBeforeShow
public class CitaEdit extends StandardEditor<Cita> {

    private static final Logger log = LoggerFactory.getLogger(CitaEdit.class);
    @Inject
    private DataManager dataManager;

    @Inject
    private ReportService reportService;

    @Inject
    private Metadata metadata;

    @Inject
    private FileStorageService fileStorageService;

    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    private Notifications notifications;

    @Inject
    private PickerField<Servicio> servicio;

    @Inject
    private Notifications notification;

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private Dialogs dialog;

    private String modoPantalla;

    @Inject
    private TimeField<Time> horaFinal;

    @Inject
    private TimeField<Time> horaInicio;

    @Inject
    private PickerField<Especialista> especialista;

    @Inject
    private DateField<Date> dia;

    @Inject
    private PickerField<Paciente> paciente;

    @Inject
    private CheckBox pagado;

    @Inject
    private CitaService citaService;

    @Inject
    private UserSession userSession;

    @Inject
    private Button insertBtn;

    @Inject
    private Button closeBtn;

    @Inject
    private FacturaService facturaService;

    @Inject
    private Dialogs dialogs;
    @Inject
    private Button removeBtn;
    @Inject
    private Button btnFactura;

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions screenOptions = event.getOptions();

        if (screenOptions instanceof MapScreenOptions) {
            Map<String, Object> params = ((MapScreenOptions) screenOptions).getParams();
            modoPantalla = (String) params.get("modo");
        }

        if ("ver".equals(modoPantalla)) {
            servicio.setEditable(false);
            paciente.setEditable(false);
            especialista.setEditable(false);
            dia.setEditable(false);
            horaInicio.setEditable(false);
            horaFinal.setEditable(false);
            pagado.setEditable(false);
            insertBtn.setVisible(false);
            closeBtn.setCaption("Volver");
            removeBtn.setVisible(false);
            /*closeBtn.setIcon("font-icon:BACK");*/
        }

        if ("crear".equals(modoPantalla)) {
            removeBtn.setVisible(false);
            btnFactura.setVisible(false);
        }
    }

    @Subscribe("especialista")
    public void onEspecialistaValueChange(HasValue.ValueChangeEvent<Especialista> event) {
        servicio.setValue(null);
    }





    @Subscribe("paciente.lookup")
    public void onPacienteLookup(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(Paciente.class, this)
                .withLaunchMode(OpenMode.DIALOG) // Configura el modo diálogo
                .withSelectHandler(pacientes -> {
                    Paciente pacienteSeleccionado = pacientes.iterator().next();
                    getEditedEntity().setPaciente(pacienteSeleccionado);
                })
                .build()
                .show();
    }


    @Subscribe("servicio.lookup")
    public void onServicioLookup(Action.ActionPerformedEvent event) {
        Especialista especialista = getEditedEntity().getEspecialista();

        if (especialista == null) {
            notification.create()
                    .withCaption("ERROR")
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .withType(Notifications.NotificationType.ERROR)
                    .withDescription("Debe seleccionar un especialista primero")
                    .withHideDelayMs(1000)
                    .show();
        } else {
            openServicios(especialista.getEspecialidad());
        }
    }

    private void openServicios(Especialidad especialidad) {
        ServicioBrowse servicioBrowse = screenBuilders.lookup(Servicio.class, this)
                .withScreenClass(ServicioBrowse.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withSelectHandler(services -> {
                    Servicio servicio = services.iterator().next();
                    getEditedEntity().setServicio(servicio);
                })
                .build();
        servicioBrowse.setEspecialidad(especialidad);
        servicioBrowse.show();
    }

    @Subscribe("horaInicio")
    public void onHoraInicioValueChange(HasValue.ValueChangeEvent<Time> event) {
        Time horaInicioTime = event.getValue();
        System.out.println(horaInicioTime);

        assert horaInicioTime != null;
        LocalTime localTimeInicio = horaInicioTime.toLocalTime();

        LocalTime localTimeFinal = localTimeInicio.plusHours(1);

        Time horaFinalTime = Time.valueOf(localTimeFinal);
        System.out.println(horaFinalTime);
        horaFinal.setValue(horaFinalTime);

    }


    @Subscribe("btnFactura")
    public void onBtnFacturaClick(Button.ClickEvent event) {
        Cita cita = getEditedEntity();

        try {
            // 1. Primero creamos la factura (sin el PDF aún)
            String numeroFactura = facturaService.generarNumeroFacturaSecuencial();
            String exencionIva = "Servicios médicos exentos de IVA según normativa vigente";

            Factura factura = facturaService.crearFacturaParaCita(cita, null, numeroFactura, exencionIva);

            // 2. Generamos el reporte con el ID de la factura
            Report report = dataManager.load(Report.class)
                    .query("SELECT r FROM report$Report r WHERE r.code = :reportCode")
                    .parameter("reportCode", "FacturaPDF1")
                    .one();

            Map<String, Object> reportParams = new HashMap<>();
            reportParams.put("facturaId", factura.getId());

            ReportOutputDocument reportResult = reportService.createReport(report, reportParams);

            // 3. Creamos y guardamos el archivo PDF
            FileDescriptor fileDescriptor = metadata.create(FileDescriptor.class);
            fileDescriptor.setName("Factura_" + factura.getNumeroFactura() + ".pdf");
            fileDescriptor.setExtension("pdf");
            fileDescriptor.setSize((long) reportResult.getContent().length);
            fileDescriptor.setCreateDate(new Date());
            fileStorageService.saveFile(fileDescriptor, reportResult.getContent());
            fileDescriptor = dataManager.commit(fileDescriptor);

            // 4. Actualizamos la factura con el PDF
            facturaService.actualizarArchivoFactura(factura, fileDescriptor);

            // 5. Mostramos resultados
            exportDisplay.show(fileDescriptor);
            notifications.create()
                    .withCaption("Factura generada exitosamente")
                    .withDescription("Número: " + factura.getNumeroFactura())
                    .show();

        } catch (Exception e) {
            notifications.create()
                    .withCaption("Error al generar factura")
                    .withDescription(e.getMessage())
                    .withType(Notifications.NotificationType.ERROR)
                    .show();
        }
    }

    @Subscribe("insertBtn")
    public void onInsertBtnClick(Button.ClickEvent event) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
        Date fechaHoraEspana = Date.from(zonedDateTime.toInstant());

        // 1. Validar campos obligatorios
        if (dia.getValue() == null || horaInicio.getValue() == null ||
                horaFinal.getValue() == null || especialista.getValue() == null ||
            servicio.getValue() == null || paciente.getValue() == null) {

            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("Por favor, completa todos los campos obligatorios.")
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
            return;
        }

        // 2. Validar que hora inicio < hora final
        if (horaInicio.getValue().after(horaFinal.getValue())) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("La hora de inicio no puede ser posterior a la hora final.")
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
            return;
        }

        // 3. Validar horario laboral (10:00 - 20:00)
        Time horaApertura = Time.valueOf("10:00:00");
        Time horaCierre = Time.valueOf("20:00:00");

        if (horaInicio.getValue().before(horaApertura)) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("La clínica abre a las 10:00. No se pueden agendar citas antes de esa hora.")
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
            return;
        }

        if (horaFinal.getValue().after(horaCierre)) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("La clínica cierra a las 20:00. No se pueden agendar citas después de esa hora.")
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
            return;
        }

        // 4. Validar duración mínima (opcional)
        long duracionMinutos = (horaFinal.getValue().getTime() - horaInicio.getValue().getTime()) / (60 * 1000);
        if (duracionMinutos < 15) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("La cita debe tener al menos 15 minutos de duración.")
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
            return;
        }

        // 5. Crear objeto cita para validación
        Cita cita = metadata.create(Cita.class);
        cita.setDia(dia.getValue());
        cita.setHoraInicio(horaInicio.getValue());
        cita.setHoraFinal(horaFinal.getValue());
        cita.setEspecialista(especialista.getValue());

        // 6. Validar solapamiento con el servicio
        if ("editar".equals(modoPantalla)) {
            cita.setId(getEditedEntity().getId());
        }

        // Validar solapamiento (el backend decide qué método usar)
        try {
            if (citaService.checkSolapamiento(cita)) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption("El especialista ya tiene una cita en ese horario.")
                        .withPosition(Notifications.Position.BOTTOM_RIGHT)
                        .show();
                return;
            }
        } catch (Exception e) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Error al verificar disponibilidad")
                    .withDescription(e.getMessage())
                    .show();
            return;
        }

        // 7. Continuar con creación/edición
        if ("crear".equals(modoPantalla)) {
            cita.setServicio(servicio.getValue());
            cita.setPaciente(paciente.getValue());
            cita.setCreateTs(fechaHoraEspana);
            cita.setCreatedBy(userSession.getUser().getLogin());
            cita.setUpdateTs(fechaHoraEspana);
            cita.setPagado(pagado.getValue());

            try {
                citaService.createCita(cita);
                notifications.create()
                        .withCaption("¡Cita guardada correctamente!")
                        .withPosition(Notifications.Position.BOTTOM_RIGHT)
                        .withType(Notifications.NotificationType.TRAY)
                        .show();
                closeWithDiscard();
            } catch (Exception e) {
                notifications.create(Notifications.NotificationType.ERROR)
                        .withCaption("Error al crear cita")
                        .withDescription(e.getMessage())
                        .show();
            }
        } else if ("editar".equals(modoPantalla)) {
            Cita citaEditada = getEditedEntity();
            citaEditada.setDia(dia.getValue());
            citaEditada.setHoraInicio(horaInicio.getValue());
            citaEditada.setHoraFinal(horaFinal.getValue());
            citaEditada.setEspecialista(especialista.getValue());
            citaEditada.setServicio(servicio.getValue());
            citaEditada.setPaciente(paciente.getValue());
            citaEditada.setUpdateTs(fechaHoraEspana);
            citaEditada.setUpdatedBy(userSession.getUser().getLogin());
            citaEditada.setPagado(pagado.getValue());

            try {
                citaService.updateCita(citaEditada);
                notifications.create()
                        .withCaption("¡Cita editada correctamente!")
                        .withPosition(Notifications.Position.BOTTOM_RIGHT)
                        .withType(Notifications.NotificationType.TRAY)
                        .show();
                closeWithDiscard();
            } catch (Exception e) {
                notifications.create(Notifications.NotificationType.ERROR)
                        .withCaption("Error al editar cita")
                        .withDescription(e.getMessage())
                        .show();
            }
        }
    }

    @Subscribe("closeBtn")
    public void onCloseBtnClick(Button.ClickEvent event) {
        closeWithDefaultAction();
    }

    @Subscribe("removeBtn")
    public void onRemoveBtnClick(Button.ClickEvent event) {
        UUID citaId = getEditedEntity().getId();

        List<UUID> citasIds = new ArrayList<>(List.of());
        citasIds.add(citaId);
        log.info(citasIds.toString());

        Map<String, Object> datos = new HashMap<>();
        datos.put("ids", citasIds);
        datos.put("deletedBy", userSession.getUser().getLogin());

        dialogs.createOptionDialog()
                .withCaption("¿Está seguro que desea eliminar la cita?")
                .withMessage("Esta acción no se puede deshacer.")
                .withWidth("550px")
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(e -> {
                            // Lógica para eliminar los pacientes
                            citaService.softDeleteCitas(datos);
                            notifications.create()
                                    .withCaption("Cita eliminada correctamente")
                                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                                    .show();
                            closeWithDiscard();
                        }),
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .show();
    }



}