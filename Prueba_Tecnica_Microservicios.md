# Prueba Técnica - Arquitectura de Microservicios

## Objetivo

Desarrollar una solución basada en microservicios para la gestión de clientes, cuentas y movimientos bancarios.

---

## Microservicios

### Cliente Service

Responsable de:

- Persona
- Cliente

### Cuenta Service

Responsable de:

- Cuenta
- Movimiento

### Comunicación

Se debe implementar comunicación asíncrona entre ambos microservicios.

---

## Entidades

### Persona

- id
- nombre
- genero
- edad
- identificacion
- direccion
- telefono

### Cliente

Hereda de Persona.

- id
- clienteId
- contraseña
- estado

### Cuenta

- id
- numeroCuenta
- tipoCuenta
- saldoInicial
- estado

### Movimiento

- id
- fecha
- tipoMovimiento
- valor
- saldo

---

## Funcionalidades

### F1 - CRUD

#### Clientes

- GET /clientes
- GET /clientes/{id}
- POST /clientes
- PUT /clientes/{id}
- DELETE /clientes/{id}

#### Cuentas

- GET /cuentas
- GET /cuentas/{id}
- POST /cuentas
- PUT /cuentas/{id}

#### Movimientos

- GET /movimientos
- GET /movimientos/{id}
- POST /movimientos
- PUT /movimientos/{id}

---

### F2 - Registro de Movimientos

#### Depósito

saldo = saldo + valor

#### Retiro

saldo = saldo - valor

Al registrar un movimiento:

- Actualizar saldo disponible.
- Registrar la transacción.
- Mantener histórico.

---

### F3 - Saldo No Disponible

Cuando un retiro exceda el saldo disponible:

```json
{
  "message": "Saldo no disponible"
}
```

Sugerencia:

- HTTP 409 Conflict
- HTTP 422 Unprocessable Entity

---

### F4 - Reporte Estado de Cuenta

Endpoint:

```http
GET /reportes?cliente={id}&fechaInicio={fecha}&fechaFin={fecha}
```

Ejemplo:

```json
[
  {
    "fecha": "2022-02-10",
    "cliente": "Marianela Montalvo",
    "numeroCuenta": "225487",
    "tipo": "Corriente",
    "saldoInicial": 100,
    "estado": true,
    "movimiento": 600,
    "saldoDisponible": 700
  }
]
```

---

### F5 - Prueba Unitaria

Implementar mínimo una prueba unitaria.

Casos sugeridos:

- Depósito aumenta saldo.
- Retiro disminuye saldo.
- Validación de saldo insuficiente.

---

### F6 - Prueba de Integración

Validar:

1. Registro de movimiento.
2. Persistencia.
3. Actualización de saldo.
4. Respuesta HTTP correcta.

---

### F7 - Docker

Contenedores sugeridos:

- cliente-service
- cuenta-service
- postgres
- rabbitmq

Ejecución:

```bash
docker-compose up
```

---

## Tecnologías Recomendadas

- Java 21
- Spring Boot 3
- PostgreSQL
- RabbitMQ
- JPA/Hibernate
- Flyway
- OpenAPI / Swagger
- JUnit 5
- Testcontainers
- Docker Compose

---

## Entregables

### Base de Datos

Archivo:

```text
BaseDatos.sql
```

### Postman

Colección JSON con todos los endpoints.

### GitHub

Repositorio público con:

- Código fuente
- Docker
- Scripts SQL
- README
- Colección Postman

### Comprimido

Adjuntar:

```text
.zip o .rar
```

---

## Observaciones

- La solución debe funcionar completamente en Docker.
- Documentar el despliegue en README.
- Aplicar Clean Code.
- Aplicar Clean Architecture.
- Aplicar manejo de excepciones.
- Utilizar patrón Repository.
