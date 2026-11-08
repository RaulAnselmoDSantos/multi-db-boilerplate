package br.com.dio;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        ConnectionUtil db = new ConnectionUtil();

        MigrationRunner.run(db);

        try (Connection connection = db.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Conexão OK");
            }
        }
    }
}
