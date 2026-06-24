CREATE TABLE idempotency_key (
    idem_key     VARCHAR(100) PRIMARY KEY,
    movimiento_id BIGINT NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL
);
