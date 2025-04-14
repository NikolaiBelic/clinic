package com.company.clinic.web.screens.cita;

import com.company.clinic.entity.Especialidad;
import com.company.clinic.entity.Especialista;
import com.company.clinic.entity.Servicio;
import com.company.clinic.web.screens.servicio.ServicioBrowse;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Cita;

import javax.inject.Inject;

@UiController("clinic_Cita.edit")
@UiDescriptor("cita-edit.xml")
@EditedEntityContainer("citaDc")
@LoadDataBeforeShow
public class CitaEdit extends StandardEditor<Cita> {
    @Inject
    private PickerField<Servicio> servicioField;

    @Subscribe("especialistaField")
    public void onEspecialistaFieldValueChange(HasValue.ValueChangeEvent<Especialista> event) {
        servicioField.setValue(null);
    }

    @Inject
    private Notifications notification;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Dialogs dialog;

    @Subscribe("servicioField.lookup")
    public void onServicioFieldLookup(Action.ActionPerformedEvent event) {
        Especialista especialista = getEditedEntity().getEspecialista();

        if (especialista == null) {
            notification.create()
                    .withCaption("ERROR")
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .withType(Notifications.NotificationType.ERROR)
                    .withDescription("Debe seleccionar un especialista primero")
                    .withHideDelayMs(1000)
                    .show();
        } else {
            openServicios(especialista.getEspecialidad());
        }
    }

    private void openServicios(Especialidad especialidad) {
        ServicioBrowse servicioBrowse = screenBuilders.lookup(Servicio.class, this)
                .withScreenClass(ServicioBrowse.class)
                .withLaunchMode(OpenMode.DIALOG)
                .withSelectHandler(services -> {
                    Servicio servicio = services.iterator().next();
                    getEditedEntity().setServicio(servicio);
                })
                .build();
        servicioBrowse.setEspecialidad(especialidad);
        servicioBrowse.show();
    }
}