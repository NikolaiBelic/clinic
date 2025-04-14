package com.company.clinic.web.screens.especialista;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Especialista;

@UiController("clinic_Especialista.browse")
@UiDescriptor("especialista-browse.xml")
@LookupComponent("especialistasTable")
@LoadDataBeforeShow
public class EspecialistaBrowse extends StandardLookup<Especialista> {
}