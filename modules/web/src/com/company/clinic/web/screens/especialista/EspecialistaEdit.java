package com.company.clinic.web.screens.especialista;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Especialista;

@UiController("clinic_Especialista.edit")
@UiDescriptor("especialista-edit.xml")
@EditedEntityContainer("especialistaDc")
@LoadDataBeforeShow
public class EspecialistaEdit extends StandardEditor<Especialista> {
    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        Especialista especialista = getEditedEntity();
        if (especialista.getId() == null) {
            throw new IllegalStateException("El ID del Especialista no puede ser nulo.");
        }
        System.out.println("ID del Especialista: " + especialista.getId());
    }
}