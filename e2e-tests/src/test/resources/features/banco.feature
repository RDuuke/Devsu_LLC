Feature: Flujo bancario end-to-end (clientes, cuentas, movimientos, reporte)

  Background:
    * url clienteUrl

  Scenario: Ciclo completo con comunicación asíncrona entre microservicios
    # 1) Crear cliente en cliente-service
    * def nombreCliente = 'Marianela Montalvo ' + java.lang.System.currentTimeMillis()
    * def ident = '' + java.lang.System.currentTimeMillis()
    Given path 'clientes'
    And request { nombre: '#(nombreCliente)', genero: 'FEMENINO', edad: 30, identificacion: '#(ident)', direccion: 'Quito', telefono: '0991234567', clienteId: '#(ident)', contrasena: 'clave1234', estado: true }
    When method post
    Then status 201
    * def clienteId = response.id

    # 2) Crear cuenta. La proyección del cliente llega por evento async, así que
    #    reintentamos hasta que cuenta-service ya conozca al cliente (si no, da 404).
    * def numCuenta = '' + java.lang.System.currentTimeMillis()
    * configure retry = { count: 10, interval: 1000 }
    Given url cuentaUrl
    And path 'cuentas'
    And request { numeroCuenta: '#(numCuenta)', tipoCuenta: 'CORRIENTE', saldoInicial: 100, estado: true, clienteId: '#(clienteId)' }
    And retry until responseStatus == 201
    When method post
    Then status 201
    * def cuentaId = response.id
    And match response.saldoActual == 100

    # 3) Depósito 600 -> saldo 700
    Given url cuentaUrl
    And path 'movimientos'
    And request { cuentaId: '#(cuentaId)', tipoMovimiento: 'DEPOSITO', valor: 600 }
    When method post
    Then status 201
    And match response.saldo == 700

    # 4) Retiro 200 -> saldo 500
    Given url cuentaUrl
    And path 'movimientos'
    And request { cuentaId: '#(cuentaId)', tipoMovimiento: 'RETIRO', valor: 200 }
    When method post
    Then status 201
    And match response.saldo == 500

    # 5) Retiro que excede el saldo -> 422 "Saldo no disponible" (F3)
    Given url cuentaUrl
    And path 'movimientos'
    And request { cuentaId: '#(cuentaId)', tipoMovimiento: 'RETIRO', valor: 999999 }
    When method post
    Then status 422
    And match response.message == 'Saldo no disponible'

    # 6) Reporte F4 - la proyección de cliente llega por evento asíncrono; reintentamos
    Given url cuentaUrl
    And path 'reportes'
    And param cliente = clienteId
    And param fechaInicio = '2000-01-01'
    And param fechaFin = '2999-12-31'
    And retry until responseStatus == 200 && response.length >= 2
    When method get
    Then status 200
    And match response[0].cliente == nombreCliente
    And match response[*].numeroCuenta contains numCuenta
