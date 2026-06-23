package com.devsu.cuenta.application;

import com.devsu.cuenta.domain.model.EstadoCuentaLinea;
import com.devsu.cuenta.domain.port.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;

    @Transactional(readOnly = true)
    public List<EstadoCuentaLinea> estadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        return reporteRepository.generarEstadoCuenta(clienteId, fechaInicio, fechaFin);
    }
}
