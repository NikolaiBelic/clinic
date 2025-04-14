/*
package com.company.clinic.web.screens.servicio;

import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Servicio;

@UiController("clinic_Servicio.browse")
@UiDescriptor("servicio-browse.xml")
@LookupComponent("serviciosTable")
@LoadDataBeforeShow
public class ServicioBrowse extends StandardLookup<Servicio> {
}
*/

package com.company.clinic.web.screens.servicio;

import com.company.clinic.entity.Especialidad;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Servicio;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import com.haulmont.cuba.gui.screen.MapScreenOptions;

import javax.inject.Inject;
import java.util.Map;

@UiController("clinic_Servicio.browse")
@UiDescriptor("servicio-browse.xml")
@LookupComponent("serviciosTable")
@LoadDataBeforeShow
public class ServicioBrowse extends StandardLookup<Servicio> {

    @Inject
    private CollectionLoader<Servicio> serviciosDl;

    private Especialidad especialidad;

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        if (especialidad == null) {
            serviciosDl.setQuery("select e from clinic_Servicio e");
        } else {
            serviciosDl.setParameter("especialidad", especialidad);
            serviciosDl.load();
        }
    }
}

