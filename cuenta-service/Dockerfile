# ---- Build stage: compila con JDK 21 (incluido en la imagen, no usa el Java local) ----
FROM gradle:8.14.3-jdk21 AS build
WORKDIR /app
COPY settings.gradle.kts build.gradle.kts gradle.properties ./
COPY cuenta-service ./cuenta-service
COPY cliente-service/build.gradle.kts ./cliente-service/
COPY e2e-tests/build.gradle.kts ./e2e-tests/
RUN gradle :cuenta-service:bootJar --no-daemon

# ---- Runtime stage: solo JRE 21 (imagen ligera) ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S app && adduser -S app -G app
COPY --from=build /app/cuenta-service/build/libs/*.jar app.jar
USER app
EXPOSE 8082
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
