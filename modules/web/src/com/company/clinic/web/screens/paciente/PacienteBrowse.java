package com.company.clinic.web.screens.paciente;

import com.company.clinic.entity.Genero;
import com.company.clinic.entity.Provincia;
import com.company.clinic.entity.pacientes.EstadoPaciente;
import com.company.clinic.entity.pacientes.TipoDocumento;
import com.company.clinic.service.paciente.PacienteService;
import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.model.CollectionContainer;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.pacientes.Paciente;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.reports.app.service.ReportService;
import com.haulmont.reports.entity.Report;
import com.haulmont.yarg.reporting.ReportOutputDocument;


import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@UiController("clinic_Paciente.browse")
@UiDescriptor("paciente-browse.xml")
@LookupComponent("pacientesTable")
@LoadDataBeforeShow
public class PacienteBrowse extends StandardLookup<Paciente> {

    @Inject
    private DataManager dataManager;

    @Inject
    private PacienteService pacienteService;

    @Inject
    private CollectionContainer<Paciente> pacienteDc;

    @Inject
    private Metadata metadata;

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private TextField<String> nombre;

    @Inject
    private TextField<String> apellidos;

    @Inject
    private DateField<Date> fechaNacimiento;

    @Inject
    private LookupField<Genero> genero;

    @Inject
    private LookupField<EstadoPaciente> estadoPaciente;

    @Inject
    private TextField<String> ciudadNacimiento;

    @Inject
    private TextField<String> nacionalidad;

    @Inject
    private LookupField<Provincia> provinciaNacimiento;

    @Inject
    private LookupField<TipoDocumento> tipoDocumento;

    @Inject
    private TextField<String> numeroDocumento;

    @Inject
    private GroupTable<Paciente> pacientesTable;

    @Inject
    Notifications notifications;

    @Inject
    private LookupField<Long> maxRegistros;

    @Inject
    private Button editBtn;

    @Inject
    private GroupBoxLayout filter;

    @Inject
    private Button clear;

    @Inject
    private Button refresh;

    @Inject
    private CollectionLoader<Paciente> pacientesDl;

    @Inject
    private ReportService reportService;

    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    private FileStorageService fileStorageService;

    @Inject
    private UserSession userSession;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    public void onInit(InitEvent event) {
        maxRegistros.setOptionsList(Arrays.asList(20l, 50l, 100l, 200l, 500l));
        maxRegistros.addValueChangeListener(e -> { pacientesDl.setMaxResults(maxRegistros.getValue().intValue()); });
        maxRegistros.setNullOptionVisible(false);
        maxRegistros.setValue(20l);

        refresh.addClickListener(a -> {
            pacientesDl.setFirstResult(0);
            pacientesDl.load();
        });

        clear.addClickListener(c -> {
            for (Component component : filter.getComponents()) {
                if (component instanceof HasValue && !Objects.equals(component.getId(), "maxRegistros")) {
                    ((HasValue) component).setValue(null);
                }
            }
        });

        pacientesTable.setItemClickAction(new BaseAction("itemClick")
                .withHandler(actionPerformedEvent -> {
                    Paciente selected = pacientesTable.getSingleSelected();
                    if (selected != null) {
                        Map<String, Object> params = new HashMap<>();
                        params.put("modo", "ver");

                        Paciente fullPaciente = dataManager.load(Paciente.class)
                                .id(selected.getId())
                                .view("paciente-edit-view")
                                .one();

                        Screen editor = screenBuilders.editor(Paciente.class, this)
                                .withScreenId("clinic_Paciente.edit")
                                .editEntity(fullPaciente)
                                .withOptions(new MapScreenOptions(params))
                                .build();

                        editor.show();
                    }
                }));
    }

    @Subscribe("createBtn")
    public void onCreateBtnClick(Button.ClickEvent event) {
        Paciente nuevoPaciente = metadata.create(Paciente.class);

        Map<String, Object> params = new HashMap<>();
        params.put("modo", "crear");

        // Abrir la pantalla de edición con la nueva instancia
        Screen editor = screenBuilders.editor(Paciente.class, this)
                .newEntity(nuevoPaciente)
                .withScreenClass(PacienteEdit.class)
                .withOptions(new MapScreenOptions(params))
                .build();
        editor.addAfterCloseListener(afterCloseEvent -> {
           pacientesDl.load();
        });
        editor.show();
    }

    @Subscribe("editBtn")
    public void onEditBtnClick(Button.ClickEvent event) {
        Set<Paciente> selectedPacientes = pacientesTable.getSelected();

        if (selectedPacientes.size() != 1) {
            notifications.create()
                    .withCaption("Debe seleccionar exactamente un paciente")
                    .withType(Notifications.NotificationType.WARNING)
                    .show();
            return;
        }

        Paciente selected = selectedPacientes.iterator().next();

        Map<String, Object> params = new HashMap<>();
        params.put("modo", "editar");

        Paciente fullPaciente = dataManager.load(Paciente.class)
                .id(selected.getId())
                .view("paciente-edit-view") // Asegúrate de que esta vista esté bien definida
                .one();

        Screen editor = screenBuilders.editor(Paciente.class, this)
                .withScreenId("clinic_Paciente.edit")
                .editEntity(fullPaciente)
                .withOptions(new MapScreenOptions(params))
                .build();

        editor.addAfterCloseListener(afterCloseEvent -> {
            pacientesDl.load();
        });

        editor.show();
    }

    @Subscribe("viewBtn")
    public void onViewBtnClick(Button.ClickEvent event) {
        Set<Paciente> selectedPacientes = pacientesTable.getSelected();

        if (selectedPacientes.size() != 1) {
            notifications.create()
                    .withCaption("Debe seleccionar exactamente un paciente")
                    .withType(Notifications.NotificationType.WARNING)
                    .show();
            return;
        }

        Paciente selected = selectedPacientes.iterator().next();

        Map<String, Object> params = new HashMap<>();
        params.put("modo", "ver");

        Paciente fullPaciente = dataManager.load(Paciente.class)
                .id(selected.getId())
                .view("paciente-edit-view") // Asegúrate de que esta vista esté bien definida
                .one();

        Screen editor = screenBuilders.editor(Paciente.class, this)
                .withScreenId("clinic_Paciente.edit")
                .editEntity(fullPaciente)
                .withOptions(new MapScreenOptions(params))
                .build();

        editor.addAfterCloseListener(afterCloseEvent -> {
            pacientesDl.load();
        });

        editor.show();
    }


    @Subscribe("removeBtn")
    public void onRemoveBtnClick(Button.ClickEvent event) {
        Set<Paciente> pacientes = pacientesTable.getSelected();

        if (pacientes.isEmpty()) {
            notifications.create()
                    .withCaption("Seleccione al menos un paciente")
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .show();
            return;
        }

        List<UUID> pacientesIds = pacientes.stream()
                .map(Paciente::getId)
                .collect(Collectors.toList());
        System.out.println(pacientesIds);

        Map<String, Object> datos = new HashMap<>();
        datos.put("ids", pacientesIds);
        datos.put("deletedBy", userSession.getUser().getLogin());

        dialogs.createOptionDialog()
                .withCaption("¿Desea elminiar los paciente seleccionados?")
                .withMessage("Esta acción no se puede deshacer.")
                .withWidth("550px")
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(e -> {
                            // Lógica para eliminar los pacientes
                            pacienteService.softDeletePacientes(datos);
                            pacientesDl.load();
                            notifications.create()
                                    .withCaption("Pacientes eliminados correctamente")
                                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                                    .show();
                        }),
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .show();
    }

    @Install(to = "pacientesDl", target = Target.DATA_LOADER)
    private List<Paciente> pacientesDlLoadDelegate(LoadContext<Paciente> loadContext) {
        pacientesDl.setFirstResult(loadContext.getQuery().getFirstResult());
        pacientesDl.setMaxResults(loadContext.getQuery().getMaxResults());
        return loadData(loadContext);
    }

    @Install(to = "pacientesTable", subject = "rowsCountTotalCountDelegate")
    private Long pacientesTableRowsCountTotalCountDelegate(DataLoadContext dataLoadContext) {
        return pacienteService.getTotalFiltros(getFiltros());
    }

    public List<Paciente> loadData (LoadContext<Paciente> loadContext) {
        Map<String, Object> filtros = getFiltros();

        filtros.put("page", loadContext.getQuery().getFirstResult());
        filtros.put("size", loadContext.getQuery().getMaxResults());

        return pacienteService.findPacientesByFiltro(filtros);
    }

    public Map<String, Object> getFiltros() {
        Map<String, Object> filtros = new HashMap<>();

        filter.getComponents().stream().forEach(item -> {
            if (item instanceof HasValue) {
                Object valueObj = ((HasValue<?>) item).getValue();
                if (valueObj != null && !valueObj.toString().isEmpty()) {
                    filtros.put(item.getId(), valueObj);
                }
            }
        });

        return filtros;
    }

    @Subscribe("excelBtn")
    public void onExcelBtnClick(Button.ClickEvent event) {
        Set<Paciente> selectedPacientes = pacientesTable.getSelected();
        if (selectedPacientes.isEmpty()) {
            notifications.create()
                    .withCaption("Seleccione al menos un paciente")
                    .show();
            return;
        }

        // Obtener el reporte
        Report report = dataManager.load(Report.class)
                .query("SELECT r FROM report$Report r WHERE r.code = :reportCode")
                .parameter("reportCode", "PacienteExcel1")
                .one();


        // Crear la lista de IDs de pacientes
         List<UUID> pacientesIds = selectedPacientes.stream()
        .map(Paciente::getId)
        .collect(Collectors.toList());

        // Crear parámetros del reporte
         Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("pacientesIds", pacientesIds);


        // Ejecutar el reporte
        ReportOutputDocument reportResult = reportService.createReport(report, reportParams);

        // Crear un FileDescriptor
        FileDescriptor fileDescriptor = metadata.create(FileDescriptor.class);
        fileDescriptor.setName("ReportePacientes.xlsx");
        fileDescriptor.setExtension("xlsx");
        fileDescriptor.setSize((long) reportResult.getContent().length);
        fileDescriptor.setCreateDate(new Date()); // Establecer la fecha de creación

        // Guardar el archivo en el almacenamiento de archivos
        try {
            fileStorageService.saveFile(fileDescriptor, reportResult.getContent());
            dataManager.commit(fileDescriptor);
        } catch (FileStorageException e) {
            notifications.create()
                    .withCaption("Error al guardar el archivo")
                    .withDescription(e.getMessage())
                    .show();
            return;
        }

        // Descargar el archivo
        exportDisplay.show(fileDescriptor);
    }
}