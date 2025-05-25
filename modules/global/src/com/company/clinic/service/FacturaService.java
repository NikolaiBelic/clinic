package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.Factura;
import com.haulmont.cuba.core.entity.FileDescriptor;

public interface FacturaService {
    String NAME = "clinic_FacturaService";

    Factura crearFacturaParaCita(Cita cita, FileDescriptor file, String numeroFactura, String exencionIva);
    void actualizarArchivoFactura(Factura factura, FileDescriptor file);
    String generarNumeroFacturaSecuencial();
}