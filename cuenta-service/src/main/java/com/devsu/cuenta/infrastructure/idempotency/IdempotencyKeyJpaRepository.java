package com.devsu.cuenta.infrastructure.idempotency;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyKeyJpaRepository extends JpaRepository<IdempotencyKeyEntity, String> {
}
