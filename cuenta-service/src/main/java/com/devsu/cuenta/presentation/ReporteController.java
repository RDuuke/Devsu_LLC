package com.devsu.cuenta.presentation;

import com.devsu.cuenta.application.ReporteService;
import com.devsu.cuenta.presentation.dto.EstadoCuentaResponse;
import com.devsu.cuenta.presentation.mapper.ReporteApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;
    private final ReporteApiMapper mapper;

    @GetMapping("/reportes")
    public List<EstadoCuentaResponse> estadoCuenta(
            @RequestParam("cliente") Long clienteId,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return reporteService.estadoCuenta(clienteId, fechaInicio, fechaFin).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
