plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "devsu-bank"

include("cliente-service")
include("cuenta-service")
include("e2e-tests")
