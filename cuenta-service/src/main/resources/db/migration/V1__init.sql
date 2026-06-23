-- Esquema inicial de cuenta-service (gestionado por Flyway).

CREATE TABLE cliente_view (
    cliente_id BIGINT PRIMARY KEY,
    nombre     VARCHAR(150) NOT NULL,
    estado     BOOLEAN NOT NULL
);

CREATE TABLE cuenta (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    numero_cuenta VARCHAR(50) NOT NULL UNIQUE,
    tipo_cuenta   VARCHAR(20) NOT NULL,
    saldo_inicial NUMERIC(19, 2) NOT NULL,
    saldo_actual  NUMERIC(19, 2) NOT NULL,
    estado        BOOLEAN NOT NULL DEFAULT TRUE,
    cliente_id    BIGINT NOT NULL,
    version       BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE movimiento (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    fecha           TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor           NUMERIC(19, 2) NOT NULL,
    saldo           NUMERIC(19, 2) NOT NULL,
    cuenta_id       BIGINT NOT NULL REFERENCES cuenta(id) ON DELETE CASCADE
);

-- Idempotencia del consumidor de eventos.
CREATE TABLE processed_event (
    event_id     VARCHAR(64) PRIMARY KEY,
    processed_at TIMESTAMPTZ NOT NULL
);

-- Índices para el reporte F4 (filtro por cliente y rango de fechas).
CREATE INDEX idx_cuenta_cliente ON cuenta (cliente_id);
CREATE INDEX idx_movimiento_cuenta_fecha ON movimiento (cuenta_id, fecha);
