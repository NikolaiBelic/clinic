package com.company.clinic.web.screens.expediente;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Expediente;

@UiController("clinic_Expediente.browse")
@UiDescriptor("expediente-browse.xml")
@LookupComponent("expedientesTable")
@LoadDataBeforeShow
public class ExpedienteBrowse extends StandardLookup<Expediente> {
}