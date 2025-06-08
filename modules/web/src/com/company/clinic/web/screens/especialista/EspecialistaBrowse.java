package com.company.clinic.web.screens.especialista;

import com.company.clinic.entity.pacientes.Paciente;
import com.company.clinic.service.EspecialistaService;
import com.haulmont.cuba.core.global.DataLoadContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Especialista;
import com.haulmont.cuba.gui.screen.LookupComponent;

import javax.inject.Inject;
import java.util.*;

@UiController("clinic_Especialista.browse")
@UiDescriptor("especialista-browse.xml")
@LookupComponent("especialistasTable")
@LoadDataBeforeShow
public class EspecialistaBrowse extends StandardLookup<Especialista> {

    @Inject
    private EspecialistaService especialistaService;

    @Inject
    private GroupBoxLayout filter;

    @Inject
    private CollectionLoader<Especialista> especialistasDl;

    @Inject
    private Button aplicar;

    @Inject
    private Button limpiar;

    @Subscribe
    public void onInit(InitEvent event) {

        aplicar.addClickListener(a -> {
            especialistasDl.setFirstResult(0);
            especialistasDl.load();
        });

        limpiar.addClickListener(c -> {
            for (Component component : filter.getComponents()) {
                if (component instanceof HasValue && !Objects.equals(component.getId(), "maxRegistros")) {
                    ((HasValue) component).setValue(null);
                }
            }
        });
    }

    public List<Especialista> loadData (LoadContext<Especialista> loadContext) {
        Map<String, Object> filtros = getFiltros();

        return especialistaService.findEspecialistasByFiltro(filtros);
    }

    @Install(to = "especialistasDl", target = Target.DATA_LOADER)
    private List<Especialista> especialistasDlLoadDelegate(LoadContext<Especialista> loadContext) {
        especialistasDl.setFirstResult(loadContext.getQuery().getFirstResult());
        especialistasDl.setMaxResults(loadContext.getQuery().getMaxResults());
        return loadData(loadContext);
    }

    @Install(to = "especialistasTable", subject = "rowsCountTotalCountDelegate")
    private Long especialistasTableRowsCountTotalCountDelegate(DataLoadContext dataLoadContext) {
        return especialistaService.getTotalFiltros(getFiltros());
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
}