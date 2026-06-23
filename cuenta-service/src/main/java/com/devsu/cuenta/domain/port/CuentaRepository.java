package com.devsu.cuenta.domain.port;

import com.devsu.cuenta.domain.model.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository {

    Cuenta save(Cuenta cuenta);

    Optional<Cuenta> findById(Long id);

    /** Carga con bloqueo pesimista para serializar movimientos concurrentes. */
    Optional<Cuenta> findByIdForUpdate(Long id);

    List<Cuenta> findAll(int page, int size);

    boolean existsByNumeroCuenta(String numeroCuenta);
}
