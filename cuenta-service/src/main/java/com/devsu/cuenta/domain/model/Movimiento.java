package com.devsu.cuenta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** {@code valor} se almacena con signo (+ depósito, - retiro); {@code saldo} es el resultante. */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    private Long id;
    private LocalDateTime fecha;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private Long cuentaId;
}
