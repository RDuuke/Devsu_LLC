INSERT INTO persona (id, nombre, genero, edad, identificacion, direccion, telefono)
OVERRIDING SYSTEM VALUE
VALUES (1, 'Marianela Montalvo', 'FEMENINO', 30, '0102030405', 'Quito', '0991234567');

INSERT INTO cliente (id, cliente_id, contrasena, estado)
VALUES (1, 'marianela', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE);

-- Evita colisión del IDENTITY con el id sembrado.
ALTER TABLE persona ALTER COLUMN id RESTART WITH 2;
