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
import com.haulmont.cuba.security.global.UserSession;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@UiController("clinic_Calendariomain")
@UiDescriptor("CalendarioMain.xml")
public class Calendariomain extends Screen {

    private static final Logger log = LoggerFactory.getLogger(Calendariomain.class);
    @Inject
    private UiComponents uiComponents;

    @Inject
    CitaService citaService;

    @Inject
    EspecialistaService especialistaService;

    private Calendar<Date> calendario;

    @Inject
    private ScreenBuilders screenBuilders;

    private final Map <SimpleCalendarEvent, Cita> eventCalendar = new HashMap<>();

    private final Map<UUID, Cita> eventCitaMap = new HashMap<>();

    private final Map<String, Object> paramsFiltro = new HashMap<>();

    private List<Cita> citas = new ArrayList<>();
    @Inject
    private UserSession userSession;

    @Subscribe
    public void onInit(InitEvent event) {
        // Configuración inicial
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Madrid"));
        System.setProperty("user.timezone", "Europe/Madrid");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        VBoxLayout vBox = uiComponents.create(VBoxLayout.class);
        HBoxLayout hBox = uiComponents.create(HBoxLayout.class);
        hBox.setSpacing(true);

        // Título
        Label titulo = uiComponents.create(Label.class);
        titulo.setValue("Calendario de citas");
        titulo.setIcon("font-icon:BOOK");
        titulo.setStyleName("h2 label-with-icon");
        hBox.add(titulo);

        Button crearCitaButton = uiComponents.create(Button.class);
        crearCitaButton.setCaption("Crear cita");
        crearCitaButton.setIcon("font-icon:PLUS_CIRCLE");
        crearCitaButton.setStyleName("primary");
        crearCitaButton.addClickListener(clickEvent -> {
            Map<String, Object> paramsScreen = new HashMap<>();
            paramsScreen.put("modo", "crear");

            Screen citaCreateScreen = screenBuilders.editor(Cita.class, this)
                    .newEntity()
                    .withLaunchMode(OpenMode.DIALOG)
                    .withOptions(new MapScreenOptions(paramsScreen))
                    .build()
                    .show();

            citaCreateScreen.addAfterCloseListener(afterCloseEvent -> {
                paramsFiltro.put("startDate", sdf.format(calendario.getStartDate()));
                paramsFiltro.put("endDate", sdf.format(calendario.getEndDate()));

                citas = citaService.getCitasCalendario(paramsFiltro);
                updateCalendar(citas, calendario);
            });
        });




        // Filter
        LookupField filter = uiComponents.create(LookupField.class);
        filter.setNullSelectionCaption("Todos los especialistas");
        List<Especialista> especialistas = especialistaService.getEspecialistas();
        filter.setOptionsList(especialistas);

        for (Especialista esp : especialistas) {
            if (userSession.getUser().getLogin().equalsIgnoreCase(esp.getNombre())) {
                filter.setValue(esp);
                break;
            }
            if (userSession.getUser().getLogin().equalsIgnoreCase(esp.getNombre())) {
                filter.setValue(esp);
                break;
            }
        }

        filter.addValueChangeListener(e -> {
            Especialista selectedEspecialista = (Especialista) filter.getValue();

            if (selectedEspecialista != null) {
                paramsFiltro.put("especialista", selectedEspecialista.getId());
                paramsFiltro.put("startDate", sdf.format(calendario.getStartDate()));
                paramsFiltro.put("endDate", sdf.format(calendario.getEndDate()));
                citas = citaService.getCitasCalendario(paramsFiltro);

                updateCalendar(citas, calendario);
            } else {
                paramsFiltro.remove("especialista");
                paramsFiltro.put("startDate", sdf.format(calendario.getStartDate()));
                paramsFiltro.put("endDate", sdf.format(calendario.getEndDate()));

                citas = citaService.getCitasCalendario(paramsFiltro);

                updateCalendar(citas, calendario);
            }
        });


        hBox.add(filter);
        hBox.add(crearCitaButton);

        vBox.add(hBox);

        calendario = uiComponents.create(Calendar.class);

        System.out.println("Fecha de inicio: " + calendario.getStartDate());
        calendario.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));

        /*System.out.println("Primer día: " + calendario.getStartDate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Formatted: " + sdf.format(calendario.getStartDate()));
        System.out.println("Final: " + sdf.format(calendario.getEndDate()));*/

        calendario.setWidth("100%");
        calendario.setHeightFull();
        calendario.setNavigationButtonsVisible(true);
        calendario.setTimeFormat(Calendar.TimeFormat.FORMAT_24H);
        calendario.setFirstVisibleHourOfDay(10);
        calendario.setLastVisibleHourOfDay(20);
        calendario.setFirstVisibleDayOfWeek(3);
        calendario.setLastVisibleDayOfWeek(7);
        /*calendario.setFirstVisibleDayOfWeek(2);
        calendario.setLastVisibleDayOfWeek(6);*/
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


        System.out.println("Formatted: " + sdf.format(calendario.getStartDate()));
        System.out.println("Final: " + sdf.format(calendario.getEndDate()));


        paramsFiltro.put("startDate", sdf.format(calendario.getStartDate()));
        paramsFiltro.put("endDate", sdf.format(calendario.getEndDate()));
        if (filter.getValue() != null) {
            Especialista selectedEspecialista = (Especialista) filter.getValue();
            System.out.println("Selected Especialista: " + selectedEspecialista.getNombre());
            paramsFiltro.put("especialista", selectedEspecialista.getId());
        }

        citas = citaService.getCitasCalendario(paramsFiltro);
        System.out.println(citas.size());
        for (Cita cita : citas) {
            log.debug("Cita ID: {} - Fecha: {} Hora Inicio: {} Hora Fin: {}",
                    cita.getId(),
                    cita.getDia(),
                    cita.getHoraInicio(),
                    cita.getHoraFinal());
            generateEvents(cita, calendario);

        }

        calendario.addEventClickListener(e -> {
            /*Cita cita = citaService.getCita(UUID.fromString(e.getCalendarEvent().getDescription()));*/
            UUID citaId = UUID.fromString(e.getCalendarEvent().getDescription());
            System.out.println("Cita ID: " + citaId);
            Cita cita = eventCitaMap.get(citaId);

            Map<String, Object> paramsScreen = new HashMap<>();
            paramsScreen.put("modo", "editar");

            Screen citaEditScreen = screenBuilders.editor(Cita.class, this)
                    .editEntity(cita)
                    .withLaunchMode(OpenMode.DIALOG)
                    .withOptions(new MapScreenOptions(paramsScreen))
                    .build()
                    .show();

            citaEditScreen.addAfterCloseListener(afterCloseEvent -> {
                paramsFiltro.put("startDate", sdf.format(calendario.getStartDate()));
                paramsFiltro.put("endDate", sdf.format(calendario.getEndDate()));

                citas = citaService.getCitasCalendario(paramsFiltro);
                updateCalendar(citas, calendario);
            });
        });

        calendario.addForwardClickListener(dateCalendarForwardClickEvent -> {
            System.out.println("He avanzado una semana");

            calendario.setStartDate(DateUtils.addWeeks(calendario.getStartDate(), 1));
            calendario.setEndDate(DateUtils.addWeeks(calendario.getEndDate(), 1));

            paramsFiltro.put("startDate", sdf.format(calendario.getStartDate()));
            paramsFiltro.put("endDate", sdf.format(calendario.getEndDate()));

            citas = citaService.getCitasCalendario(paramsFiltro);

            calendario.getEventProvider().removeAllEvents();

            for (Cita cita : citas) {
                generateEvents(cita, calendario);
            }


            System.out.println("Formatted: " + sdf.format(calendario.getStartDate()));
            System.out.println("Final: " + sdf.format(calendario.getEndDate()));

        });

        calendario.addBackwardClickListener(dateCalendarBackwardClickEvent -> {
            System.out.println("He retrocedido una semana");

            calendario.setStartDate(DateUtils.addWeeks(calendario.getStartDate(), -1));
            calendario.setEndDate(DateUtils.addWeeks(calendario.getEndDate(), -1));

            paramsFiltro.put("startDate", sdf.format(calendario.getStartDate()));
            paramsFiltro.put("endDate", sdf.format(calendario.getEndDate()));

            citas = citaService.getCitasCalendario(paramsFiltro);

            calendario.getEventProvider().removeAllEvents();

            for (Cita cita : citas) {
                generateEvents(cita, calendario);
            }

            System.out.println("Formatted: " + sdf.format(calendario.getStartDate()));
            System.out.println("Final: " + sdf.format(calendario.getEndDate()));
        });

        System.out.println("TimeZone: " + calendario.getTimeZone());

        System.out.println(("Calendario creado - Fecha inicio: " + calendario.getStartDate() + "Fecha fin: " + calendario.getEndDate()));
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

        eventCitaMap.put(cita.getId(), cita);


        String idEspecialista = cita.getEspecialista().getId().toString();

        calendarEvent.setStyleName(idEspecialista.toUpperCase());

        calendario.getEventProvider().addEvent(calendarEvent);
    }
}