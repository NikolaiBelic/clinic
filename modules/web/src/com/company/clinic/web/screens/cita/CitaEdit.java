package com.company.clinic.web.screens.cita;

import com.company.clinic.entity.*;
import com.company.clinic.entity.pacientes.*;
import com.company.clinic.service.CitaService;
import com.company.clinic.web.screens.servicio.ServicioBrowse;
import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
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
import com.haulmont.reports.exception.ReportingException;
import com.haulmont.yarg.reporting.ReportOutputDocument;

import javax.inject.Inject;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UiController("clinic_Cita.edit")
@UiDescriptor("cita-edit.xml")
@EditedEntityContainer("citaDc")
@LoadDataBeforeShow
public class CitaEdit extends StandardEditor<Cita> {

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

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions screenOptions = event.getOptions();

        if (screenOptions instanceof MapScreenOptions) {
            Map<String, Object> params = ((MapScreenOptions) screenOptions).getParams();
            modoPantalla = (String) params.get("modo");
        }
    }

    @Subscribe("especialista")
    public void onEspecialistaFieldValueChange(PickerField.FieldValueChangeEvent<Especialista> event) {
        servicio.setValue(null);
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

    @Subscribe("btnFactura")
    public void onBtnFacturaClick(Button.ClickEvent event) {
        Cita cita = getEditedEntity();
        UUID pacienteId = cita.getPaciente().getId();

        // Obtener el reporte
        Report report = dataManager.load(Report.class)
                .query("SELECT r FROM report$Report r WHERE r.code = :reportCode")
                .parameter("reportCode", "FacturaPDF1")
                .one();

        // Crear parámetros del reporte
        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("pacienteId", pacienteId);

        // Ejecutar el reporte
        try {
            ReportOutputDocument reportResult = reportService.createReport(report, reportParams);

            // Crear un FileDescriptor
            FileDescriptor fileDescriptor = metadata.create(FileDescriptor.class);
            fileDescriptor.setName("FacturaPaciente.pdf");
            fileDescriptor.setExtension("pdf");
            fileDescriptor.setSize((long) reportResult.getContent().length);
            fileDescriptor.setCreateDate(new Date());

            // Guardar el archivo en el almacenamiento de archivos
            fileStorageService.saveFile(fileDescriptor, reportResult.getContent());
            dataManager.commit(fileDescriptor);

            // Descargar el archivo
            exportDisplay.show(fileDescriptor);
        } catch (ReportingException e) {
            notifications.create()
                    .withCaption("Error al generar el reporte")
                    .withDescription(e.getMessage())
                    .show();
        } catch (FileStorageException e) {
            notifications.create()
                    .withCaption("Error al guardar el archivo")
                    .withDescription(e.getMessage())
                    .show();
        }
    }

    @Subscribe("insertBtn")
    public void onInsertBtnClick(Button.ClickEvent event) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
        Date fechaHoraEspana = Date.from(zonedDateTime.toInstant());

        if ("crear".equals(modoPantalla)) {
            if (dia.getValue() == null || horaInicio.getValue() == null ||
                    horaFinal.getValue() == null) {

                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption("Por favor, completa todos los campos obligatorios.")
                        .withPosition(Notifications.Position.BOTTOM_RIGHT)
                        .show();

                return;
            }

            Cita cita = metadata.create(Cita.class);

            cita.setDia(dia.getValue());
            cita.setHoraInicio(horaInicio.getValue());
            System.out.println("Hora inicio: " + horaInicio.getValue());
            cita.setHoraFinal(horaFinal.getValue());
            cita.setEspecialista(especialista.getValue());
            cita.setServicio(servicio.getValue());
            cita.setPaciente(paciente.getValue());
            cita.setCreateTs(fechaHoraEspana);
            cita.setCreatedBy(userSession.getUser().getLogin());
            cita.setUpdateTs(fechaHoraEspana);


            try {
                citaService.createCita(cita);
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

    @Subscribe("closeBtn")
    public void onCloseBtnClick(Button.ClickEvent event) {
        closeWithDefaultAction();
    }

}