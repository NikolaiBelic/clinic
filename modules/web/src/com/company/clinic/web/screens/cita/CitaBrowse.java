package com.company.clinic.web.screens.cita;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Cita;

@UiController("clinic_Cita.browse")
@UiDescriptor("cita-browse.xml")
@LookupComponent("citasTable")
@LoadDataBeforeShow
public class CitaBrowse extends StandardLookup<Cita> {
}