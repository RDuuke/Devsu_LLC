package com.devsu.cliente.infrastructure.messaging.outbox;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<OutboxEvent, String> {
    List<OutboxEvent> findByPublishedAtIsNullOrderByCreatedAtAsc(Limit limit);
}
