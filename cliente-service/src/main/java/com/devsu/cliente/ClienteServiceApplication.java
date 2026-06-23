package com.devsu.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // necesario para el relay del Outbox
public class ClienteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClienteServiceApplication.class, args);
    }
}
