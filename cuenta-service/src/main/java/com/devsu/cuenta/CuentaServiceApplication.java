package com.devsu.cuenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Punto de entrada del microservicio de cuentas y movimientos. */
@SpringBootApplication
public class CuentaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CuentaServiceApplication.class, args);
    }
}
