package com.devsu.cuenta.application;

import com.devsu.cuenta.application.command.ActualizarCuentaCommand;
import com.devsu.cuenta.application.command.CrearCuentaCommand;
import com.devsu.cuenta.domain.exception.ClienteInexistenteException;
import com.devsu.cuenta.domain.exception.CuentaNotFoundException;
import com.devsu.cuenta.domain.exception.DuplicateCuentaException;
import com.devsu.cuenta.domain.model.Cuenta;
import com.devsu.cuenta.domain.port.ClienteViewRepository;
import com.devsu.cuenta.domain.port.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteViewRepository clienteViewRepository;

    @Transactional
    public Cuenta crear(CrearCuentaCommand cmd) {
        if (cuentaRepository.existsByNumeroCuenta(cmd.numeroCuenta())) {
            throw new DuplicateCuentaException(cmd.numeroCuenta());
        }
        // El cliente debe existir en la proyección local antes de abrirle una cuenta.
        if (clienteViewRepository.findById(cmd.clienteId()).isEmpty()) {
            throw new ClienteInexistenteException(cmd.clienteId());
        }
        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(cmd.numeroCuenta())
                .tipoCuenta(cmd.tipoCuenta())
                .saldoInicial(cmd.saldoInicial())
                .saldoActual(cmd.saldoInicial())   // al crear, disponible = inicial
                .estado(cmd.estado() == null || cmd.estado())
                .clienteId(cmd.clienteId())
                .build();
        return cuentaRepository.save(cuenta);
    }

    @Transactional(readOnly = true)
    public Cuenta obtener(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Cuenta> listar(int page, int size) {
        return cuentaRepository.findAll(page, size);
    }

    @Transactional
    public Cuenta actualizar(Long id, ActualizarCuentaCommand cmd) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException(id));
        if (cmd.tipoCuenta() != null) cuenta.setTipoCuenta(cmd.tipoCuenta());
        if (cmd.estado() != null) cuenta.setEstado(cmd.estado());
        return cuentaRepository.save(cuenta);
    }
}
