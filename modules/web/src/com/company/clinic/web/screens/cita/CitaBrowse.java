package com.company.clinic.web.screens.cita;

import com.company.clinic.entity.pacientes.Paciente;
import com.company.clinic.service.CitaService;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Cita;
import com.haulmont.cuba.gui.screen.LookupComponent;

import javax.inject.Inject;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UiController("clinic_Cita.browse")
@UiDescriptor("cita-browse.xml")
@LookupComponent("citasTable")
@LoadDataBeforeShow
public class CitaBrowse extends StandardLookup<Cita> {

    @Inject
    private CheckBox filterPagado;

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

    public Map<String, Object> getFiltros() {
        Map<String, Object> filtros = new HashMap<>();

        filter.getComponents().stream().forEach(item -> {
            if (item instanceof HasValue) {
                Object valueObj = ((HasValue<?>) item).getValue();
                if (valueObj != null && !valueObj.toString().isEmpty()) {
                    filtros.put(item.getId(), valueObj);

                    System.out.println("Filtro: " + item.getId() + ", Valor: " + valueObj + ", Tipo: " + valueObj.getClass().getName());
                }
            }
        });

        return filtros;
    }
}