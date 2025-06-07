package com.company.clinic.web.screens.expediente;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Expediente;

@UiController("clinic_Expediente.edit")
@UiDescriptor("expediente-edit.xml")
@EditedEntityContainer("expedienteDc")
@LoadDataBeforeShow
public class ExpedienteEdit extends StandardEditor<Expediente> {
}