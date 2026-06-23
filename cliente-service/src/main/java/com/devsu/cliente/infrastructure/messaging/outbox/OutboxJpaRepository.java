package com.devsu.cliente.infrastructure.messaging.outbox;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Lee los eventos pendientes en orden de creación. */
public interface OutboxJpaRepository extends JpaRepository<OutboxEvent, String> {
    List<OutboxEvent> findByPublishedAtIsNullOrderByCreatedAtAsc(Limit limit);
}
