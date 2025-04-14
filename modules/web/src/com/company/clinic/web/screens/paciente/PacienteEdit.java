package com.company.clinic.web.screens.paciente;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Paciente;

@UiController("clinic_Paciente.edit")
@UiDescriptor("paciente-edit.xml")
@EditedEntityContainer("pacienteDc")
@LoadDataBeforeShow
public class PacienteEdit extends StandardEditor<Paciente> {
}