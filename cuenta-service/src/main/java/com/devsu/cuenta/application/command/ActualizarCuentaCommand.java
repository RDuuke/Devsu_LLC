package com.devsu.cuenta.application.command;

import com.devsu.cuenta.domain.model.TipoCuenta;

/** Los campos nulos no se modifican. */
public record ActualizarCuentaCommand(
        TipoCuenta tipoCuenta,
        Boolean estado
) {
}
