-- cliente_view se siembra directamente; en operación normal llega por evento
-- desde cliente-service.
INSERT INTO cliente_view (cliente_id, nombre, estado)
VALUES (1, 'Marianela Montalvo', TRUE);

INSERT INTO cuenta (id, numero_cuenta, tipo_cuenta, saldo_inicial, saldo_actual, estado, cliente_id, version)
OVERRIDING SYSTEM VALUE
VALUES (1, '225487', 'CORRIENTE', 100.00, 100.00, TRUE, 1, 0);

-- Evita colisión del IDENTITY con el id sembrado.
ALTER TABLE cuenta ALTER COLUMN id RESTART WITH 2;
