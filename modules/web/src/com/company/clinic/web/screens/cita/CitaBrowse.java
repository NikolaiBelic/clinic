package com.company.clinic.web.screens.cita;

import com.company.clinic.service.CitaService;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Cita;

import javax.inject.Inject;
import java.util.List;

@UiController("clinic_Cita.browse")
@UiDescriptor("cita-browse.xml")
@LookupComponent("citasTable")
@LoadDataBeforeShow
public class CitaBrowse extends StandardLookup<Cita> {

    @Inject
    CitaService citaService;

    @Subscribe
    public void onInit(InitEvent event) {
        List<Cita> citas = citaService.getAllCitasMS();
        for (Cita cita : citas) {
            System.out.println(cita.getHoraInicio());
        }
    }
}