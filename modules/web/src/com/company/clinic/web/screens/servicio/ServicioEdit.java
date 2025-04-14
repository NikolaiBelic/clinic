package com.company.clinic.web.screens.servicio;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Servicio;

@UiController("clinic_Servicio.edit")
@UiDescriptor("servicio-edit.xml")
@EditedEntityContainer("servicioDc")
@LoadDataBeforeShow
public class ServicioEdit extends StandardEditor<Servicio> {
}