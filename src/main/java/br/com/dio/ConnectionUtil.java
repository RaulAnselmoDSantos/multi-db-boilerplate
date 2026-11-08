package br.com.dio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * Lê a configuração do banco de variáveis de ambiente e abre conexões JDBC.
 *
 * Obrigatórias: DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD
 * Opcionais:
 *   DB_TYPE   - mysql (padrão) ou postgres; define URL padrão, driver e pasta de migrations
 *   DB_URL    - URL JDBC completa (substitui a montada automaticamente)
 *   DB_DRIVER - classe do driver (substitui a padrão)
 */
public final class ConnectionUtil {

    // Para adicionar um banco: inclua aqui o modelo da URL e o driver.
    private static final Map<String, String> URL_TEMPLATES = Map.of(
            "mysql", "jdbc:mysql://%s:%s/%s",
            "postgres", "jdbc:postgresql://%s:%s/%s");
    private static final Map<String, String> DRIVERS = Map.of(
            "mysql", "com.mysql.cj.jdbc.Driver",
            "postgres", "org.postgresql.Driver");

    private final String type = env("DB_TYPE", "mysql").toLowerCase();
    private final String url;
    private final String user = required("DB_USER");
    private final String password = required("DB_PASSWORD");

    public ConnectionUtil() {
        if (!URL_TEMPLATES.containsKey(type)) {
            throw new IllegalStateException("DB_TYPE não suportado: " + type);
        }
        String customUrl = env("DB_URL", null);
        this.url = customUrl != null ? customUrl : URL_TEMPLATES.get(type)
                .formatted(required("DB_HOST"), required("DB_PORT"), required("DB_NAME"));
        try {
            Class.forName(env("DB_DRIVER", DRIVERS.get(type)));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Driver JDBC não encontrado no classpath", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public String getUrl() { return url; }
    public String getUser() { return user; }
    public String getPassword() { return password; }

    /** Pasta de migrations deste banco, ex.: classpath:db/migration/mysql */
    public String getMigrationLocation() { return "classpath:db/migration/" + type; }

    private static String required(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Variável de ambiente obrigatória não definida: " + name);
        }
        return value;
    }

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
