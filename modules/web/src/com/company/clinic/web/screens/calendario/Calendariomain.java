package com.company.clinic.web.screens.calendario;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.Especialista;
import com.company.clinic.service.CitaService;
import com.company.clinic.service.EspecialistaService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.calendar.SimpleCalendarEvent;
import com.haulmont.cuba.gui.screen.*;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.sql.Time;
import java.time.*;
import java.util.*;

@UiController("clinic_Calendariomain")
@UiDescriptor("CalendarioMain.xml")
public class Calendariomain extends Screen {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Calendariomain.class);
    @Inject
    private UiComponents uiComponents;

    @Inject
    CitaService citaService;

    @Inject
    EspecialistaService especialistaService;

    private Calendar<Date> calendario;

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private Notifications notifications;

    @Subscribe
    public void onInit(InitEvent event) {
        VBoxLayout vBox = uiComponents.create(VBoxLayout.class);
        HBoxLayout hBox = uiComponents.create(HBoxLayout.class);
        hBox.setSpacing(true);

        // Título
        Label titulo = uiComponents.create(Label.class);
        titulo.setValue("Calendario de citas");
        titulo.setIcon("font-icon:BOOK");
        titulo.setStyleName("h2 label-with-icon");
        hBox.add(titulo);

        // Filter
        LookupField filter = uiComponents.create(LookupField.class);
        List<Especialista> especialistas = especialistaService.getEspecialistas();
        filter.setOptionsList(especialistas);

        filter.addValueChangeListener(e -> {
            Especialista selectedEspecialista = (Especialista) filter.getValue();

            if (selectedEspecialista != null) {
                List<Cita> citas = citaService.getCitasPorEspecialista(selectedEspecialista.getId());

                updateCalendar(citas, calendario);
            } else {
                List<Cita> citas = citaService.getAllCitasMS();

                updateCalendar(citas, calendario);
            }
        });


        hBox.add(filter);

        vBox.add(hBox);

        calendario = uiComponents.create(Calendar.class);

        calendario.setWidth("100%");
        calendario.setHeightFull();
        calendario.setNavigationButtonsVisible(true);
        calendario.setTimeFormat(Calendar.TimeFormat.FORMAT_24H);
        calendario.setFirstVisibleHourOfDay(10);
        calendario.setLastVisibleHourOfDay(20);
//        calendario.setFirstDayOfWeek(2);
        calendario.setFirstVisibleDayOfWeek(2);
        calendario.setLastVisibleDayOfWeek(6);
        calendario.setWeeklyCaptionFormat("dd/MM/yyyy");

        Map<DayOfWeek, String> days = new HashMap<>(7);
        days.put(DayOfWeek.MONDAY, "Lunes");
        days.put(DayOfWeek.TUESDAY, "Martes");
        days.put(DayOfWeek.WEDNESDAY, "Miércoles");
        days.put(DayOfWeek.THURSDAY, "Jueves");
        days.put(DayOfWeek.FRIDAY, "Viernes");
        days.put(DayOfWeek.SATURDAY, "Sábado");
        days.put(DayOfWeek.SUNDAY, "Domingo");
        calendario.setDayNames(days);

        vBox.add(calendario);
        vBox.expand(calendario);


        getWindow().add(vBox);
        getWindow().expand(vBox);


        List<Cita> citas = citaService.getAllCitasMS();
        for (Cita cita : citas) {
            generateEvents(cita, calendario);
        }

        calendario.addEventClickListener(e -> {
            Cita cita = citaService.getCita(UUID.fromString(e.getCalendarEvent().getDescription()));
            Screen citaEditScreen = screenBuilders.editor(Cita.class, this)
                    .editEntity(cita)
                    .withLaunchMode(OpenMode.DIALOG)
                    .build()
                    .show();

            citaEditScreen.addAfterCloseListener(afterCloseEvent -> {
               if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                   notifications.create()
                           .withCaption("Saved!")
                           .withDescription("Se ha guardado la cita")
                           .withType(Notifications.NotificationType.TRAY)
                           .show();

                   List<Cita> citasUpdated = citaService.getAllCitasMS();
                   updateCalendar(citasUpdated, calendario);

               } else {
                   notifications.create()
                           .withCaption("AVISO")
                           .withDescription("Se ha cancelado la edición")
                           .withType(Notifications.NotificationType.WARNING)
                           .show();
               }
            });


        });
    }

    private void updateCalendar(List<Cita> citas, Calendar calendario) {
        calendario.getEventProvider().removeAllEvents();
        for (Cita cita : citas) {
            generateEvents(cita, calendario);
        }
    }


    public void generateEvents(Cita cita, Calendar calendario) {

        Date fecha = cita.getDia();
        Time horaInicio = cita.getHoraInicio();
        Time horaFinal = cita.getHoraFinal();


        Date fechaHoraInicio = new Date(fecha.getTime());
        fechaHoraInicio.setHours(horaInicio.getHours());
        fechaHoraInicio.setMinutes(horaInicio.getMinutes());
        fechaHoraInicio.setSeconds(0);

        Date fechaHoraFinal = new Date(fecha.getTime());
        fechaHoraFinal.setHours(horaFinal.getHours());
        fechaHoraFinal.setMinutes(horaFinal.getMinutes());
        fechaHoraFinal.setSeconds(0);

        SimpleCalendarEvent calendarEvent = new SimpleCalendarEvent();

        calendarEvent.setStart(fechaHoraInicio);
        calendarEvent.setEnd(fechaHoraFinal);
        calendarEvent.setCaption("P: " + cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellidos());
        calendarEvent.setDescription(cita.getId().toString());
        /*calendarEvent.setDescription("E: " + cita.getEspecialista().getNombre() + " "
                + cita.getEspecialista().getApellidos());*/

        String idEspecialista = cita.getEspecialista().getId().toString();

        calendarEvent.setStyleName(idEspecialista.toUpperCase());

        calendario.getEventProvider().addEvent(calendarEvent);
    }
}