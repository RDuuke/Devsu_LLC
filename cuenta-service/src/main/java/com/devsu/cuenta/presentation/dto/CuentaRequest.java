package com.devsu.cuenta.presentation.dto;

import com.devsu.cuenta.domain.model.TipoCuenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CuentaRequest(
        @NotBlank String numeroCuenta,
        @NotNull TipoCuenta tipoCuenta,
        @NotNull @PositiveOrZero BigDecimal saldoInicial,
        Boolean estado,
        @NotNull Long clienteId
) {
}
