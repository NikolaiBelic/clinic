package com.company.clinic.web.screens.receta;

import com.company.clinic.entity.Especialista;
import com.company.clinic.entity.pacientes.Paciente;
import com.haulmont.addon.bproc.web.processform.Outcome;
import com.haulmont.addon.bproc.web.processform.ProcessForm;
import com.haulmont.addon.bproc.web.processform.ProcessFormContext;
import com.haulmont.addon.bproc.web.processform.ProcessVariable;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController("clinic_Receta")
@UiDescriptor("receta.xml")
@ProcessForm(outcomes = {
        @Outcome(id = Receta.ENVIAR),
        @Outcome(id = Receta.VALIDAR),
        @Outcome(id = Receta.DENEGAR)
})
public class Receta extends Screen {

    public final static String ENVIAR = "enviar";
    public final static String VALIDAR = "validar";
    public final static String DENEGAR = "denegar";

    @Inject
    private Button denegar;

    @Inject
    private Button enviar;

    @Inject
    private Button validar;

    @Inject
    @ProcessVariable
    private PickerField<Paciente> paciente;

    @Inject
    @ProcessVariable
    private PickerField<Especialista> especialista;

    @Inject
    @ProcessVariable
    private TextArea<String> descripcion;

    @Inject
    private ProcessFormContext processFormContext;

    @Subscribe("enviar")
    public void onEnviarClick(Button.ClickEvent event) {
        processFormContext
                .taskCompletion()
                .withOutcome(ENVIAR)
                .addProcessVariable("paciente" , paciente.getValue())
                .addProcessVariable("especialista" , especialista.getValue())
                .addProcessVariable("descripcion" , descripcion.getValue())
                .complete();

        closeWithDefaultAction();
    }

    @Subscribe("validar")
    public void onValidarClick(Button.ClickEvent event) {
        processFormContext
                .taskCompletion()
                .withOutcome(VALIDAR)
                .complete();

        closeWithDefaultAction();
    }

    @Subscribe("denegar")
    public void onDenegarClick(Button.ClickEvent event) {
        processFormContext
                .taskCompletion()
                .withOutcome(DENEGAR)
                .complete();

        closeWithDefaultAction();
    }


}