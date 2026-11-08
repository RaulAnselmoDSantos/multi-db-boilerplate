package br.com.dio;

import org.flywaydb.core.Flyway;

/** Executa as migrations do Flyway usando a configuração do ConnectionUtil. */
public final class MigrationRunner {

    private MigrationRunner() {}

    public static void run(ConnectionUtil db) {
        Flyway.configure()
                .dataSource(db.getUrl(), db.getUser(), db.getPassword())
                .locations(db.getMigrationLocation())
                .load()
                .migrate();
    }
}
