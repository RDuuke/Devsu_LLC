plugins {
    java
}

dependencies {
    testImplementation("com.intuit.karate:karate-junit5:1.4.1")
}

tasks.test {
    useJUnitPlatform()
    onlyIf { project.hasProperty("e2e") }
    systemProperty("cliente.url", System.getProperty("cliente.url", "http://localhost:8081"))
    systemProperty("cuenta.url", System.getProperty("cuenta.url", "http://localhost:8082"))
    outputs.upToDateWhen { false }
}
