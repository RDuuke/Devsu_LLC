package com.devsu.cuenta.application;

import com.devsu.cuenta.application.command.RegistrarMovimientoCommand;
import com.devsu.cuenta.domain.exception.CuentaNotFoundException;
import com.devsu.cuenta.domain.exception.MovimientoNotFoundException;
import com.devsu.cuenta.domain.exception.SaldoInsuficienteException;
import com.devsu.cuenta.domain.model.Cuenta;
import com.devsu.cuenta.domain.model.Movimiento;
import com.devsu.cuenta.domain.model.TipoMovimiento;
import com.devsu.cuenta.domain.port.CuentaRepository;
import com.devsu.cuenta.domain.port.IdempotencyRepository;
import com.devsu.cuenta.domain.port.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final IdempotencyRepository idempotencyRepository;

    @Transactional
    public Movimiento registrar(RegistrarMovimientoCommand cmd) {
        return registrar(cmd, null);
    }

    @Transactional
    public Movimiento registrar(RegistrarMovimientoCommand cmd, @Nullable String idempotencyKey) {
        boolean useKey = idempotencyKey != null && !idempotencyKey.isBlank();
        if (useKey) {
            var previo = idempotencyRepository.findMovimientoId(idempotencyKey);
            if (previo.isPresent()) {
                return movimientoRepository.findById(previo.get())
                        .orElseThrow(() -> new MovimientoNotFoundException(previo.get()));
            }
        }

        Cuenta cuenta = cuentaRepository.findByIdForUpdate(cmd.cuentaId())
                .orElseThrow(() -> new CuentaNotFoundException(cmd.cuentaId()));

        Movimiento movimiento = cuenta.registrarMovimiento(
                cmd.tipoMovimiento(), cmd.valor(), LocalDateTime.now());

        cuentaRepository.save(cuenta);
        Movimiento guardado = movimientoRepository.save(movimiento);

        if (useKey) {
            idempotencyRepository.save(idempotencyKey, guardado.getId());
        }
        return guardado;
    }

    @Transactional(readOnly = true)
    public Movimiento obtener(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new MovimientoNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Movimiento> listar(int page, int size) {
        return movimientoRepository.findAll(page, size);
    }

    /** Ajusta el saldo por la diferencia; no recalcula los movimientos posteriores. */
    @Transactional
    public Movimiento actualizar(Long id, TipoMovimiento tipo, BigDecimal valorAbsoluto) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new MovimientoNotFoundException(id));
        Cuenta cuenta = cuentaRepository.findByIdForUpdate(movimiento.getCuentaId())
                .orElseThrow(() -> new CuentaNotFoundException(movimiento.getCuentaId()));

        BigDecimal nuevoMonto = (tipo == TipoMovimiento.DEPOSITO) ? valorAbsoluto : valorAbsoluto.negate();
        BigDecimal delta = nuevoMonto.subtract(movimiento.getValor());
        BigDecimal nuevoSaldo = cuenta.getSaldoActual().add(delta);
        if (nuevoSaldo.signum() < 0) {
            throw new SaldoInsuficienteException();
        }

        cuenta.setSaldoActual(nuevoSaldo);
        movimiento.setTipoMovimiento(tipo);
        movimiento.setValor(nuevoMonto);
        movimiento.setSaldo(nuevoSaldo);

        cuentaRepository.save(cuenta);
        return movimientoRepository.save(movimiento);
    }
}
