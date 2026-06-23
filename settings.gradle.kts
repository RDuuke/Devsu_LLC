plugins {
    // Auto-provisiona el JDK 21 (toolchain) si no está instalado localmente.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "devsu-bank"

include("cliente-service")
include("cuenta-service")
include("e2e-tests")
