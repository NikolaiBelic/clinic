package com.company.clinic.web.screens.cita;

import com.company.clinic.entity.Especialidad;
import com.company.clinic.entity.Especialista;
import com.company.clinic.entity.Servicio;
import com.company.clinic.entity.pacientes.Paciente;
import com.company.clinic.service.CitaService;
import com.company.clinic.web.screens.paciente.PacienteEdit;
import com.company.clinic.web.screens.servicio.ServicioBrowse;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.global.DataLoadContext;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Cita;
import com.haulmont.cuba.gui.screen.LookupComponent;

import javax.inject.Inject;
import java.sql.Time;
import java.util.*;

@UiController("clinic_Cita.browse")
@UiDescriptor("cita-browse.xml")
@LookupComponent("citasTable")
@LoadDataBeforeShow
public class CitaBrowse extends StandardLookup<Cita> {
    @Inject
    private LookupField pagado;

    @Inject
    CitaService citaService;

    @Inject
    private CollectionContainer<Cita> citasDc;

    @Inject
    private CollectionLoader<Cita> citasDl;

    @Inject
    private LookupField<Long> maxRegistros;

    @Inject
    private GroupBoxLayout filter;

    @Inject
    private Button aplicar;

    @Inject
    private TimeField<Date> horaFinal;

    @Inject
    private TimeField<Date> horaInicio;

    @Inject
    private Button clear;

    @Inject
    private PickerField<Especialista> especialista;

    @Inject
    private Notifications notifications;

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private PickerField<Servicio> servicio;
    @Inject
    private Metadata metadata;

    @Subscribe
    public void onInit(InitEvent event) {
        maxRegistros.setOptionsList(Arrays.asList(20l, 50l, 100l, 200l, 500l));
        maxRegistros.addValueChangeListener(e -> { citasDl.setMaxResults(maxRegistros.getValue().intValue()); });
        maxRegistros.setNullOptionVisible(false);
        maxRegistros.setValue(20l);

        aplicar.addClickListener(a -> {
            citasDl.setFirstResult(0);
            citasDl.load();
        });

        clear.addClickListener(c -> {
            for (Component component : filter.getComponents()) {
                if (component instanceof HasValue && !Objects.equals(component.getId(), "maxRegistros")) {
                    ((HasValue) component).setValue(null);
                }
            }
        });


        Map<String, Boolean> opcionesPagado = new LinkedHashMap<>();
        opcionesPagado.put("Pagado", true);
        opcionesPagado.put("No pagado", false);
        pagado.setOptionsMap(opcionesPagado);
    }

    public List<Cita> loadData (LoadContext<Cita> loadContext) {
        Map<String, Object> filtros = getFiltros();

        filtros.put("page", loadContext.getQuery().getFirstResult());
        filtros.put("size", loadContext.getQuery().getMaxResults());

        return citaService.findCitasByFiltro(filtros);
    }



    @Install(to = "citasDl", target = Target.DATA_LOADER)
    private List<Cita> citasDlLoadDelegate(LoadContext<Cita> loadContext) {
        citasDl.setFirstResult(loadContext.getQuery().getFirstResult());
        citasDl.setMaxResults(loadContext.getQuery().getMaxResults());
        return loadData(loadContext);
    }

    @Install(to = "citasTable", subject = "rowsCountTotalCountDelegate")
    private Long citasTableRowsCountTotalCountDelegate(DataLoadContext dataLoadContext) {
        return citaService.getTotalFiltros(getFiltros());
    }



    public Map<String, Object> getFiltros() {
        Map<String, Object> filtros = new HashMap<>();

        filter.getComponents().stream().forEach(item -> {
            if (item instanceof HasValue) {
                Object valueObj = ((HasValue<?>) item).getValue();
                if (valueObj != null && !valueObj.toString().isEmpty()) {

                    if (valueObj instanceof BaseUuidEntity) {
                        UUID valorFinal = ((BaseUuidEntity) valueObj).getId();
                        System.out.println(valorFinal);
                        filtros.put(item.getId(), valorFinal);
                    } else {
                        filtros.put(item.getId(), valueObj);
                    }



                    System.out.println("Filtro: " + item.getId() + ", Valor: " + valueObj + ", Tipo: " + valueObj.getClass().getName());
                }
            }
        });

        return filtros;
    }

    @Subscribe("servicio.lookup")
    public void onServicioLookup(Action.ActionPerformedEvent event) {
        Especialista especialista = this.especialista.getValue();

        if (especialista == null) {
            notifications.create()
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
                    this.servicio.setValue(servicio);
                })
                .build();
        servicioBrowse.setEspecialidad(especialidad);
        servicioBrowse.show();
    }

    @Subscribe("createBtn")
    public void onCreateBtnClick(Button.ClickEvent event) {
        Cita nuevaCita = metadata.create(Cita.class);

        Map<String, Object> params = new HashMap<>();
        params.put("modo", "crear");

        // Abrir la pantalla de edición con la nueva instancia
        Screen editor = screenBuilders.editor(Cita.class, this)
                .newEntity(nuevaCita)
                .withScreenClass(CitaEdit.class)
                .withOptions(new MapScreenOptions(params))
                .withLaunchMode(OpenMode.DIALOG)
                .build();
        editor.addAfterCloseListener(afterCloseEvent -> {
            citasDl.load();
        });
        editor.show();
    }
}