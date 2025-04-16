package com.company.clinic.web.screens.paciente;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.pacientes.Paciente;

@UiController("clinic_Paciente.browse")
@UiDescriptor("paciente-browse.xml")
@LookupComponent("pacientesTable")
@LoadDataBeforeShow
public class PacienteBrowse extends StandardLookup<Paciente> {
}