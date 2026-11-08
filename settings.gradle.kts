/*
 * settings.gradle.kts: define o nome do projeto (projeto único, sem multi-módulo).
 */

plugins {
    // Permite ao Gradle baixar automaticamente o JDK 21 (toolchain) caso não esteja instalado.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "multi-db-boilerplate"
