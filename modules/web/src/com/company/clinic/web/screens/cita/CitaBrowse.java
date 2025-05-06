package com.company.clinic.web.screens.cita;

import com.company.clinic.service.CitaService;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Cita;
import com.haulmont.cuba.gui.screen.LookupComponent;

import javax.inject.Inject;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@UiController("clinic_Cita.browse")
@UiDescriptor("cita-browse.xml")
@LookupComponent("citasTable")
@LoadDataBeforeShow
public class CitaBrowse extends StandardLookup<Cita> {
    @Inject
    private DateField<Date> filterDia;

    @Inject
    private TimeField<Time> filterHoraInicio;

    @Inject
    private TimeField<Time> filterHoraFinal;

    @Inject
    private LookupField<String> filterPaciente;

    @Inject
    private LookupField<String> filterEspecialista;

    @Inject
    private LookupField<String> filterServicio;

    @Inject
    private CheckBox filterPagado;

    @Inject
    CitaService citaService;

    @Inject
    private CollectionContainer<Cita> citasDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        List<Cita> citas = citaService.getAllCitasMS();
        citasDc.setItems(citas);
    }

    @Subscribe("filterBtn")
    public void onFilterBtnClick(Button.ClickEvent event) {
        Date dia = filterDia.getValue();
        Time horaInicio = filterHoraInicio.getValue();
        Time horaFinal = filterHoraFinal.getValue();
        String paciente = filterPaciente.getValue();
        String especialista = filterEspecialista.getValue();
        String servicio = filterServicio.getValue();
        Boolean pagado = filterPagado.getValue();

        System.out.println("Día: " + dia);
        System.out.println("Hora Inicio: " + horaInicio);
        System.out.println("Hora Final: " + horaFinal);
        System.out.println("Paciente: " + paciente);
        System.out.println("Especialista: " + especialista);
        System.out.println("Servicio: " + servicio);
        System.out.println("Pagado: " + pagado);
    }
}