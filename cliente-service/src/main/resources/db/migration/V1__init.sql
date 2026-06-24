CREATE TABLE persona (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre         VARCHAR(150) NOT NULL,
    genero         VARCHAR(20),
    edad           INTEGER,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    direccion      VARCHAR(255),
    telefono       VARCHAR(30)
);

CREATE TABLE cliente (
    id         BIGINT PRIMARY KEY REFERENCES persona(id) ON DELETE CASCADE,
    cliente_id VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    estado     BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE outbox_event (
    event_id       VARCHAR(64) PRIMARY KEY,
    aggregate_type VARCHAR(50) NOT NULL,
    type           VARCHAR(50) NOT NULL,
    routing_key    VARCHAR(100) NOT NULL,
    payload        TEXT NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL,
    published_at   TIMESTAMPTZ
);

CREATE INDEX idx_outbox_unpublished ON outbox_event (created_at) WHERE published_at IS NULL;
