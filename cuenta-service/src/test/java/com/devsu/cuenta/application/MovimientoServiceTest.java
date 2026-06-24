package com.devsu.cuenta.application;

import com.devsu.cuenta.application.command.RegistrarMovimientoCommand;
import com.devsu.cuenta.domain.exception.SaldoInsuficienteException;
import com.devsu.cuenta.domain.model.Cuenta;
import com.devsu.cuenta.domain.model.Movimiento;
import com.devsu.cuenta.domain.model.TipoCuenta;
import com.devsu.cuenta.domain.model.TipoMovimiento;
import com.devsu.cuenta.domain.port.CuentaRepository;
import com.devsu.cuenta.domain.port.IdempotencyRepository;
import com.devsu.cuenta.domain.port.MovimientoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private MovimientoRepository movimientoRepository;
    @Mock
    private IdempotencyRepository idempotencyRepository;

    @InjectMocks
    private MovimientoService movimientoService;

    private Cuenta cuenta(BigDecimal saldo) {
        return Cuenta.builder()
                .id(1L).numeroCuenta("225487").tipoCuenta(TipoCuenta.AHORRO)
                .saldoInicial(saldo).saldoActual(saldo).estado(true).clienteId(1L)
                .build();
    }

    @Test
    void registrarDeposito_actualizaSaldoYPersiste() {
        when(cuentaRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(cuenta(new BigDecimal("100.00"))));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(inv -> inv.getArgument(0));

        Movimiento result = movimientoService.registrar(
                new RegistrarMovimientoCommand(1L, TipoMovimiento.DEPOSITO, new BigDecimal("50.00")));

        assertThat(result.getSaldo()).isEqualByComparingTo("150.00");
        verify(cuentaRepository).save(any(Cuenta.class));
        verify(movimientoRepository).save(any(Movimiento.class));
    }

    @Test
    void registrarRetiroSinSaldo_lanzaExcepcionYNoPersiste() {
        when(cuentaRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(cuenta(new BigDecimal("100.00"))));

        assertThatThrownBy(() -> movimientoService.registrar(
                new RegistrarMovimientoCommand(1L, TipoMovimiento.RETIRO, new BigDecimal("500.00"))))
                .isInstanceOf(SaldoInsuficienteException.class);

        verify(movimientoRepository, never()).save(any());
    }
}
