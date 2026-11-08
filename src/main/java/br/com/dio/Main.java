package br.com.dio;

import br.com.dio.database.ConnectionFactory;
import br.com.dio.config.DatabaseConfig;
import br.com.dio.database.MigrationRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        DatabaseConfig config = new DatabaseConfig();
        ConnectionFactory factory = new ConnectionFactory(config);

        System.out.println("Connecting to database...");


        MigrationRunner.run(config);

        try (Connection connection = factory.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Conexão OK");
            }
        }
    }
}
