package com.devsu.cuenta.infrastructure.persistence.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Proyección de solo lectura del reporte F4, mapeada por alias de la query nativa. */
public interface EstadoCuentaProjection {
    LocalDate getFecha();

    String getCliente();

    String getNumeroCuenta();

    String getTipo();

    BigDecimal getSaldoInicial();

    boolean getEstado();

    BigDecimal getMovimiento();

    BigDecimal getSaldoDisponible();
}
