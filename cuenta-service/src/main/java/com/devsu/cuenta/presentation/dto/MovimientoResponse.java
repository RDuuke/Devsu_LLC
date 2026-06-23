package com.devsu.cuenta.presentation.dto;

import com.devsu.cuenta.domain.model.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponse(
        Long id,
        LocalDateTime fecha,
        TipoMovimiento tipoMovimiento,
        BigDecimal valor,
        BigDecimal saldo,
        Long cuentaId
) {
}
