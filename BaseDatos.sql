-- ============================================================================
-- BaseDatos.sql — Script consolidado (entregable)
-- Prueba Técnica Devsu - Arquitectura de Microservicios
--
-- Nota: en ejecución normal, Flyway gestiona y versiona estos esquemas
-- automáticamente al arrancar cada servicio. Este archivo se entrega como
-- referencia consolidada del modelo de datos (database-per-service).
-- ============================================================================

-- ----------------------------------------------------------------------------
-- Base de datos: cliente_db  (cliente-service)
-- ----------------------------------------------------------------------------
-- CREATE DATABASE cliente_db;
-- \c cliente_db

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
    contrasena VARCHAR(100) NOT NULL,   -- hash BCrypt
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

-- ----------------------------------------------------------------------------
-- Base de datos: cuenta_db  (cuenta-service)
-- ----------------------------------------------------------------------------
-- CREATE DATABASE cuenta_db;
-- \c cuenta_db

CREATE TABLE cliente_view (
    cliente_id BIGINT PRIMARY KEY,       -- proyección local (read-model)
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
    version       BIGINT NOT NULL DEFAULT 0   -- bloqueo optimista
);

CREATE TABLE movimiento (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    fecha           TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor           NUMERIC(19, 2) NOT NULL,   -- con signo: + depósito, - retiro
    saldo           NUMERIC(19, 2) NOT NULL,
    cuenta_id       BIGINT NOT NULL REFERENCES cuenta(id) ON DELETE CASCADE
);

CREATE TABLE processed_event (
    event_id     VARCHAR(64) PRIMARY KEY,      -- idempotencia del consumidor
    processed_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_cuenta_cliente ON cuenta (cliente_id);
CREATE INDEX idx_movimiento_cuenta_fecha ON movimiento (cuenta_id, fecha);
