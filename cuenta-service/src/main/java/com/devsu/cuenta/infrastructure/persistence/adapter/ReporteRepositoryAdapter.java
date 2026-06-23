package com.devsu.cuenta.infrastructure.persistence.adapter;

import com.devsu.cuenta.domain.model.EstadoCuentaLinea;
import com.devsu.cuenta.domain.model.TipoCuenta;
import com.devsu.cuenta.domain.port.ReporteRepository;
import com.devsu.cuenta.infrastructure.persistence.jpa.ReporteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReporteRepositoryAdapter implements ReporteRepository {

    private final ReporteJpaRepository jpa;

    @Override
    public List<EstadoCuentaLinea> generarEstadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        // finExclusive = fechaFin + 1 día para incluir todo el día de fechaFin.
        return jpa.estadoCuenta(clienteId, fechaInicio.atStartOfDay(), fechaFin.plusDays(1).atStartOfDay())
                .stream()
                .map(p -> EstadoCuentaLinea.builder()
                        .fecha(p.getFecha())
                        .cliente(p.getCliente())
                        .numeroCuenta(p.getNumeroCuenta())
                        .tipo(TipoCuenta.valueOf(p.getTipo()))
                        .saldoInicial(p.getSaldoInicial())
                        .estado(p.getEstado())
                        .movimiento(p.getMovimiento())
                        .saldoDisponible(p.getSaldoDisponible())
                        .build())
                .toList();
    }
}
