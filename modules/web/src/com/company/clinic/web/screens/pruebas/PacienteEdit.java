package com.company.clinic.web.screens.pruebas;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.pacientes.Paciente;

@UiController("clinic_Paciente.edit2")
@UiDescriptor("paciente-edit.xml")
@EditedEntityContainer("pacienteDc")
@LoadDataBeforeShow
public class PacienteEdit extends StandardEditor<Paciente> {
}