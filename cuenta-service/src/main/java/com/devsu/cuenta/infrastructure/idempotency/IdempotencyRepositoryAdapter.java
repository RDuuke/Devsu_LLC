package com.devsu.cuenta.infrastructure.idempotency;

import com.devsu.cuenta.domain.port.IdempotencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdempotencyRepositoryAdapter implements IdempotencyRepository {

    private final IdempotencyKeyJpaRepository jpa;

    @Override
    public Optional<Long> findMovimientoId(String idempotencyKey) {
        return jpa.findById(idempotencyKey).map(IdempotencyKeyEntity::getMovimientoId);
    }

    @Override
    public void save(String idempotencyKey, Long movimientoId) {
        jpa.save(new IdempotencyKeyEntity(idempotencyKey, movimientoId, Instant.now()));
    }
}
