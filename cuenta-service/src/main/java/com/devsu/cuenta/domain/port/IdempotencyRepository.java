package com.devsu.cuenta.domain.port;

import java.util.Optional;

public interface IdempotencyRepository {

    Optional<Long> findMovimientoId(String idempotencyKey);

    void save(String idempotencyKey, Long movimientoId);
}
