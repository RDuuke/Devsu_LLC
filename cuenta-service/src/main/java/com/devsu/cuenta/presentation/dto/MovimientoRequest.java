package com.devsu.cuenta.presentation.dto;

import com.devsu.cuenta.domain.model.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/** El valor es la magnitud positiva; el signo lo decide el tipo de movimiento. */
public record MovimientoRequest(
        @NotNull Long cuentaId,
        @NotNull TipoMovimiento tipoMovimiento,
        @NotNull @Positive BigDecimal valor
) {
}
