package com.devsu.cuenta.application.command;

import com.devsu.cuenta.domain.model.TipoCuenta;

public record ActualizarCuentaCommand(
        TipoCuenta tipoCuenta,
        Boolean estado
) {
}
