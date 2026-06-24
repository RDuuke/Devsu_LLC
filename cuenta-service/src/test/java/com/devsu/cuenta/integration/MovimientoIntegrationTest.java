package com.devsu.cuenta.integration;

import com.devsu.cuenta.domain.model.TipoCuenta;
import com.devsu.cuenta.domain.model.TipoMovimiento;
import com.devsu.cuenta.presentation.dto.CuentaRequest;
import com.devsu.cuenta.presentation.dto.CuentaResponse;
import com.devsu.cuenta.presentation.dto.MovimientoRequest;
import com.devsu.cuenta.presentation.dto.MovimientoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true) // sin Docker disponible la prueba se omite, no falla
@SuppressWarnings("resource") // los contenedores los cierra la extensión @Testcontainers
class MovimientoIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("cuenta_db");

    @Container
    static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.13-management-alpine");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbit::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbit::getAdminPassword);
    }

    @Autowired
    private TestRestTemplate rest;

    @BeforeEach
    void addApiKeyHeader() {
        rest.getRestTemplate().getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("X-API-Key", "devsu-secret-key");
            return execution.execute(request, body);
        });
    }

    @Test
    void registrarMovimiento_persisteYActualizaSaldo() {
        // numero distinto al del seed para no chocar con datos precargados
        CuentaRequest cuentaReq = new CuentaRequest(
                "770001", TipoCuenta.AHORRO, new BigDecimal("100.00"), true, 1L);
        ResponseEntity<CuentaResponse> cuentaResp =
                rest.postForEntity("/cuentas", cuentaReq, CuentaResponse.class);
        assertThat(cuentaResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long cuentaId = cuentaResp.getBody().id();

        MovimientoRequest movReq = new MovimientoRequest(
                cuentaId, TipoMovimiento.DEPOSITO, new BigDecimal("600.00"));
        ResponseEntity<MovimientoResponse> movResp =
                rest.postForEntity("/movimientos", movReq, MovimientoResponse.class);

        assertThat(movResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(movResp.getBody().saldo()).isEqualByComparingTo("700.00");

        ResponseEntity<CuentaResponse> after =
                rest.getForEntity("/cuentas/" + cuentaId, CuentaResponse.class);
        assertThat(after.getBody().saldoActual()).isEqualByComparingTo("700.00");
    }

    @Test
    void retiroSinSaldo_devuelve422() {
        CuentaRequest cuentaReq = new CuentaRequest(
                "770002", TipoCuenta.CORRIENTE, new BigDecimal("50.00"), true, 1L);
        Long cuentaId = rest.postForEntity("/cuentas", cuentaReq, CuentaResponse.class).getBody().id();

        MovimientoRequest movReq = new MovimientoRequest(
                cuentaId, TipoMovimiento.RETIRO, new BigDecimal("100.00"));
        ResponseEntity<String> resp = rest.postForEntity("/movimientos", movReq, String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(resp.getBody()).contains("Saldo no disponible");
    }
}
