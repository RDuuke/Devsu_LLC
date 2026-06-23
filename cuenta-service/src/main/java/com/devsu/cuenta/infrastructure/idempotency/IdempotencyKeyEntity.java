package com.devsu.cuenta.infrastructure.idempotency;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/** Mapea clave de idempotencia al movimiento creado. */
@Entity
@Table(name = "idempotency_key")
@Getter
@Setter
@NoArgsConstructor
public class IdempotencyKeyEntity {

    @Id
    @Column(name = "idem_key")
    private String key;

    @Column(name = "movimiento_id", nullable = false)
    private Long movimientoId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public IdempotencyKeyEntity(String key, Long movimientoId, Instant createdAt) {
        this.key = key;
        this.movimientoId = movimientoId;
        this.createdAt = createdAt;
    }
}
