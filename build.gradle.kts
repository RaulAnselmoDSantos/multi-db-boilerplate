// ---------------------------------------------------------------
// build.gradle.kts: configuração do build (única parte em Kotlin DSL).
// Todo o código do projeto é Java.
// ---------------------------------------------------------------

// Plugins: adicionam funcionalidades ao build.
plugins {
    java          // compila código Java
    application   // permite rodar o app com ./gradlew run
}

// Identificação do projeto.
group = "br.com.dio"
version = "1.0-SNAPSHOT"

// De onde o Gradle baixa as bibliotecas (Maven Central).
repositories {
    mavenCentral()
}

// Bibliotecas usadas pelo projeto.
dependencies {
    // Driver JDBC do MySQL.
    implementation("com.mysql:mysql-connector-j:8.4.0")

    // Flyway: executa as migrations (scripts SQL versionados).
    // flyway-core e flyway-mysql devem ter a MESMA versão.
    implementation("org.flywaydb:flyway-core:10.12.0")
    implementation("org.flywaydb:flyway-mysql:10.12.0")

    // Datafaker: gera dados falsos para testes.
    implementation("net.datafaker:datafaker:2.2.2")

    // Lombok: reduz código repetitivo (getters, construtores...).
    // compileOnly = só na compilação; annotationProcessor = processa as anotações.
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}

// Versão do Java usada para compilar e rodar (Java 21).
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// Classe principal executada pelo ./gradlew run.
application {
    mainClass = "br.com.dio.Main"
}

// Garante que o código-fonte seja lido como UTF-8 (acentos).
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
