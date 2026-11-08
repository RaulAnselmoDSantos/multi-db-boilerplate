package br.com.dio.database;

import br.com.dio.config.DatabaseConfig;
import org.flywaydb.core.Flyway;

public class MigrationRunner {

    private MigrationRunner() {}

    public static void run(DatabaseConfig config){
        Flyway.configure()
                .dataSource(config.getUrl(), config.getUser(), config.getPassword())
                .locations(config.getMigrationLocation())
                .load()
                .migrate();
    }
}
