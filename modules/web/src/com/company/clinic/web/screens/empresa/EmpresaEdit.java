package com.company.clinic.web.screens.empresa;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Empresa;

@UiController("clinic_DatosEmpresa.edit")
@UiDescriptor("empresa-edit.xml")
@EditedEntityContainer("empresaDc")
@LoadDataBeforeShow
public class EmpresaEdit extends StandardEditor<Empresa> {
}