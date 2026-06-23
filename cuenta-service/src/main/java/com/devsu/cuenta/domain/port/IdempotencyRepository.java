package com.devsu.cuenta.domain.port;

import java.util.Optional;

/** Mapea una clave de idempotencia al movimiento ya creado. */
public interface IdempotencyRepository {

    Optional<Long> findMovimientoId(String idempotencyKey);

    void save(String idempotencyKey, Long movimientoId);
}
