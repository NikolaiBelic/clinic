package com.company.clinic.web.screens.paciente;

import com.company.clinic.entity.Genero;
import com.company.clinic.entity.Provincia;
import com.company.clinic.entity.pacientes.*;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import javax.swing.*;
import java.util.Date;

@UiController("clinic_Paciente.edit")
@UiDescriptor("paciente-edit.xml")
@EditedEntityContainer("pacienteDc")
@LoadDataBeforeShow
public class PacienteEdit extends StandardEditor<Paciente> {

    @Inject
    private TextField<String> nombreField;

    @Inject
    private TextField<String> apellidosField;

    @Inject
    private DateField<Date> fechaNacimientoField;

    @Inject
    private LookupField<Genero> generoField;

    @Inject
    private LookupField<EstadoPaciente> estadoPacienteField;

    @Inject
    private TextField<String> ciudadNacimientoField;

    @Inject
    private TextField<String> nacionalidadField;

    @Inject
    private LookupField<Provincia> provinciaAdministrativoField;

    @Inject
    private LookupField<TipoDocumento> tipoDocumentoField;

    @Inject
    private TextField<String> numeroDocumentoField;

    @Inject
    private TextField<String> telefonoField;

    @Inject
    private TextField<String> emailField;

    @Inject
    private TextField<String> calleField;

    @Inject
    private TextField<String> numeroField;

    @Inject
    private TextField<String> codigoPostalField;

    @Inject
    private TextField<String> ciudadField;

    @Inject
    private LookupField<Provincia> provinciaContactoField;

    @Inject
    private CheckBox mismoDatosFacturacionField;

    @Inject
    private TextField<String> nifField;

    @Inject
    private TextField<String> nombreFacturacionField;

    @Inject
    private TextField<String> apellidosFacturacionField;

    @Inject
    private TextField<String> calleFacturacionField;

    @Inject
    private TextField<String> numeroFacturacionField;

    @Inject
    private TextField<String> ciudadFacturacionField;

    @Inject
    private LookupField<Provincia> provinciaFacturacionField;

    @Inject
    private VBoxLayout datosFacturacion;

    @Inject
    private InstanceContainer<Paciente> pacienteDc;
    @Inject
    private Button insertBtn;

    @Subscribe("insertBtn")
    public void onInsertBtnClick(Button.ClickEvent event) {
        System.out.println("========== Paciente ========");
        System.out.println("Nombre: " + nombreField.getValue());
        System.out.println("Apellidos: " + apellidosField.getValue());
        System.out.println("Fecha de Nacimiento: " + fechaNacimientoField.getValue());
        System.out.println("Género: " + generoField.getValue().getId());

        System.out.println("========== Datos Administrativos ========");
        System.out.println("Estado Paciente: " + estadoPacienteField.getValue().getId());
        System.out.println("Ciudad Nacimiento: " + ciudadNacimientoField.getValue());
        System.out.println("Nacionalidad: " + nacionalidadField.getValue());
        System.out.println("Provincia Nacimiento: " + provinciaAdministrativoField.getValue().getId());
        System.out.println("Tipo Documento: " + tipoDocumentoField.getValue().getId());
        System.out.println("Número Documento: " + numeroDocumentoField.getValue());

        System.out.println("========== Datos de Contacto ========");
        System.out.println("Teléfono: " + telefonoField.getValue());
        System.out.println("Email: " + emailField.getValue());
        System.out.println("Calle: " + calleField.getValue());
        System.out.println("Número: " + numeroField.getValue());
        System.out.println("Código Postal: " + codigoPostalField.getValue());
        System.out.println("Ciudad: " + ciudadField.getValue());
        System.out.println("Provincia: " + provinciaContactoField.getValue().getId());
        System.out.println("Usar Datos de Facturación: " + mismoDatosFacturacionField.getValue());

        System.out.println("========== Datos de Facturación ========");
        System.out.println("NIF: " + nifField.getValue());
        System.out.println("Nombre Facturación: " + nombreFacturacionField.getValue());
        System.out.println("Apellidos Facturación: " + apellidosFacturacionField.getValue());
        System.out.println("Calle Facturación: " + calleFacturacionField.getValue());
        System.out.println("Número Facturación: " + numeroFacturacionField.getValue());
        System.out.println("Ciudad Facturación: " + ciudadFacturacionField.getValue());
        System.out.println("Provincia Facturación: " + provinciaFacturacionField.getValue().getId());
    }

    @Subscribe("mismoDatosFacturacionField")
    public void onMismoDatosFacturacionFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue().equals(true)) {
            datosFacturacion.setVisible(false);
        } else {
            datosFacturacion.setVisible(true);
        }
    }

    @Subscribe
    public void onInit(InitEvent event) {
    }
}