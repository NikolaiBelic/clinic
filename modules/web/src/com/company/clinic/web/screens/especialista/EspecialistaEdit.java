package com.company.clinic.web.screens.especialista;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Especialista;

@UiController("clinic_Especialista.edit")
@UiDescriptor("especialista-edit.xml")
@EditedEntityContainer("especialistaDc")
@LoadDataBeforeShow
public class EspecialistaEdit extends StandardEditor<Especialista> {
}