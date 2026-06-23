package com.devsu.cuenta.domain.port;

import com.devsu.cuenta.domain.model.EstadoCuentaLinea;

import java.time.LocalDate;
import java.util.List;

public interface ReporteRepository {

    List<EstadoCuentaLinea> generarEstadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}
