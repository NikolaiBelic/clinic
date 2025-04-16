package com.company.clinic.web.screens.datosempresa;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.DatosEmpresa;

@UiController("clinic_DatosEmpresa.browse")
@UiDescriptor("datos-empresa-browse.xml")
@LookupComponent("datosEmpresasTable")
@LoadDataBeforeShow
public class DatosEmpresaBrowse extends StandardLookup<DatosEmpresa> {
}