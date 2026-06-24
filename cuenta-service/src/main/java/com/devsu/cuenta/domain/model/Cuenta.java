package com.devsu.cuenta.domain.model;

import com.devsu.cuenta.domain.exception.CuentaInactivaException;
import com.devsu.cuenta.domain.exception.SaldoInsuficienteException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    private Long id;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoActual;
    private boolean estado;
    private Long clienteId;

    private Long version;

    public Movimiento registrarMovimiento(TipoMovimiento tipo, BigDecimal valorAbsoluto, LocalDateTime fecha) {
        if (!estado) {
            throw new CuentaInactivaException(numeroCuenta);
        }
        if (valorAbsoluto == null || valorAbsoluto.signum() <= 0) {
            throw new IllegalArgumentException("El valor del movimiento debe ser positivo");
        }

        BigDecimal monto = (tipo == TipoMovimiento.DEPOSITO) ? valorAbsoluto : valorAbsoluto.negate();
        BigDecimal nuevoSaldo = saldoActual.add(monto);

        if (nuevoSaldo.signum() < 0) {
            throw new SaldoInsuficienteException();
        }

        this.saldoActual = nuevoSaldo;

        return Movimiento.builder()
                .fecha(fecha)
                .tipoMovimiento(tipo)
                .valor(monto)
                .saldo(nuevoSaldo)
                .cuentaId(id)
                .build();
    }
}
