package com.company.clinic.web.screens.datosempresa;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.DatosEmpresa;

@UiController("clinic_DatosEmpresa.edit")
@UiDescriptor("datos-empresa-edit.xml")
@EditedEntityContainer("datosEmpresaDc")
@LoadDataBeforeShow
public class DatosEmpresaEdit extends StandardEditor<DatosEmpresa> {
}