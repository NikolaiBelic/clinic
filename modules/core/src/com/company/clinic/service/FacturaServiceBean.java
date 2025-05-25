package com.company.clinic.service;

import com.company.clinic.entity.Cita;
import com.company.clinic.entity.Factura;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service(FacturaService.NAME)
public class FacturaServiceBean implements FacturaService {
    @Inject
    private DataManager dataManager;

    @Inject
    private Metadata metadata;

    @Override
    public Factura crearFacturaParaCita(Cita cita, FileDescriptor file, String numeroFactura, String exencionIva) {
        Factura factura = metadata.create(Factura.class);
        factura.setCita(cita);
        factura.setFile(file);
        factura.setFechaEmision(new Date());
        factura.setNumeroFactura(numeroFactura);
        factura.setExencionIva(exencionIva);

        return dataManager.commit(factura);
    }

    @Override
    public void actualizarArchivoFactura(Factura factura, FileDescriptor file) {
        factura.setFile(file);
        dataManager.commit(factura);
    }

    @Override
    public String generarNumeroFacturaSecuencial() {
        // Implementación de generación de número secuencial
        // Puedes usar un Sequence de base de datos o un contador
        return "FAC-" + System.currentTimeMillis();
    }
}