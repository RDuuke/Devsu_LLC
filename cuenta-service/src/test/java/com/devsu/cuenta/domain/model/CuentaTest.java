package com.devsu.cuenta.domain.model;

import com.devsu.cuenta.domain.exception.CuentaInactivaException;
import com.devsu.cuenta.domain.exception.SaldoInsuficienteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CuentaTest {

    private Cuenta cuentaActiva(BigDecimal saldo) {
        return Cuenta.builder()
                .id(1L)
                .numeroCuenta("225487")
                .tipoCuenta(TipoCuenta.AHORRO)
                .saldoInicial(saldo)
                .saldoActual(saldo)
                .estado(true)
                .clienteId(1L)
                .build();
    }

    @Test
    @DisplayName("Depósito aumenta el saldo")
    void depositoAumentaSaldo() {
        Cuenta cuenta = cuentaActiva(new BigDecimal("100.00"));

        Movimiento mov = cuenta.registrarMovimiento(
                TipoMovimiento.DEPOSITO, new BigDecimal("600.00"), LocalDateTime.now());

        assertThat(cuenta.getSaldoActual()).isEqualByComparingTo("700.00");
        assertThat(mov.getValor()).isEqualByComparingTo("600.00");
        assertThat(mov.getSaldo()).isEqualByComparingTo("700.00");
    }

    @Test
    @DisplayName("Retiro disminuye el saldo")
    void retiroDisminuyeSaldo() {
        Cuenta cuenta = cuentaActiva(new BigDecimal("700.00"));

        Movimiento mov = cuenta.registrarMovimiento(
                TipoMovimiento.RETIRO, new BigDecimal("200.00"), LocalDateTime.now());

        assertThat(cuenta.getSaldoActual()).isEqualByComparingTo("500.00");
        assertThat(mov.getValor()).isEqualByComparingTo("-200.00");
        assertThat(mov.getSaldo()).isEqualByComparingTo("500.00");
    }

    @Test
    @DisplayName("Retiro mayor al saldo lanza SaldoInsuficienteException (F3)")
    void retiroExcedeSaldoLanzaExcepcion() {
        Cuenta cuenta = cuentaActiva(new BigDecimal("100.00"));

        assertThatThrownBy(() -> cuenta.registrarMovimiento(
                TipoMovimiento.RETIRO, new BigDecimal("150.00"), LocalDateTime.now()))
                .isInstanceOf(SaldoInsuficienteException.class)
                .hasMessage("Saldo no disponible");

        assertThat(cuenta.getSaldoActual()).isEqualByComparingTo("100.00");
    }

    @Test
    @DisplayName("Operar sobre cuenta inactiva lanza excepción")
    void cuentaInactivaLanzaExcepcion() {
        Cuenta cuenta = cuentaActiva(new BigDecimal("100.00"));
        cuenta.setEstado(false);

        assertThatThrownBy(() -> cuenta.registrarMovimiento(
                TipoMovimiento.DEPOSITO, new BigDecimal("10.00"), LocalDateTime.now()))
                .isInstanceOf(CuentaInactivaException.class);
    }
}
