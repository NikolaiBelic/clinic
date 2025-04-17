package com.company.clinic.web.screens.paciente;

import com.company.clinic.entity.Cita;
import com.company.clinic.service.paciente.PacienteService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.pacientes.Paciente;
import com.haulmont.cuba.gui.xml.layout.CompositeDescriptorLoader;

import javax.inject.Inject;
import java.util.List;

@UiController("clinic_Paciente.browse")
@UiDescriptor("paciente-browse.xml")
@LookupComponent("pacientesTable")
@LoadDataBeforeShow
public class PacienteBrowse extends StandardLookup<Paciente> {

    @Inject
    private PacienteService pacienteService;

    @Inject
    private CollectionContainer<Paciente> pacientesDc;

    @Inject
    private Metadata metadata;

    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe("pacientesTable.create")
    public void onPacientesTableCreate(Action.ActionPerformedEvent event) {
        Paciente nuevoPaciente = metadata.create(Paciente.class);

        // Abrir la pantalla de edición con la nueva instancia
        Screen editor = screenBuilders.editor(Paciente.class, this)
                .newEntity(nuevoPaciente)
                .build();
        editor.show();
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        pacientesDc.setItems(pacientes);
    }

}