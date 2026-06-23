plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    // Web + JPA + validación
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Mensajería asíncrona (RabbitMQ)
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    // Resiliencia: retry para el Outbox relay
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework:spring-aspects")
    implementation("io.github.resilience4j:resilience4j-spring-boot3:2.2.0")

    // Migraciones de esquema
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    // Hash de contraseñas (solo módulo crypto, sin Spring Security completo)
    implementation("org.springframework.security:spring-security-crypto:6.3.4")

    // Documentación OpenAPI / Swagger UI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // Driver PostgreSQL
    runtimeOnly("org.postgresql:postgresql")

    // Lombok (menos boilerplate)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // MapStruct (mappers sin boilerplate)
    implementation("org.mapstruct:mapstruct:1.6.2")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.testcontainers:junit-jupiter:1.20.4")
    testImplementation("org.testcontainers:postgresql:1.20.4")
    testImplementation("org.testcontainers:rabbitmq:1.20.4")
}
