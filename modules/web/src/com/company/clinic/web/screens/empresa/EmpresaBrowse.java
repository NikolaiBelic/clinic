package com.company.clinic.web.screens.empresa;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Empresa;

@UiController("clinic_DatosEmpresa.browse")
@UiDescriptor("empresa-browse.xml")
@LookupComponent("empresasTable")
@LoadDataBeforeShow
public class EmpresaBrowse extends StandardLookup<Empresa> {
}