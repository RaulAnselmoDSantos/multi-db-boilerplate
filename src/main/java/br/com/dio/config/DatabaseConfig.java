package br.com.dio.config;

import lombok.Getter;

import java.util.Map;

public class DatabaseConfig {

    private static String optional(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null ? defaultValue : value;
    }

    private static String required(String key) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(key + " is required");
        }
        return value;
    }

    private static final Map<String, String> URL_TEMPLATES = Map.of(
            "mysql", "jdbc:mysql://%s:%s/%s",
            "postgres", "jdbc:postgresql://%s:%s/%s");

    private final String type;
    private final String host;
    private final String port;
    private final String name;

    @Getter private final String user;
    @Getter private final String password;

    public DatabaseConfig() {
        this.type = optional("DB_TYPE", "mysql").toLowerCase();
        if (!URL_TEMPLATES.containsKey(type)) {
            throw new IllegalArgumentException("Database type not found: " + type
            + " (aceitos: " + URL_TEMPLATES.keySet() + ")");
        }
        this.host = required("DB_HOST");
        this.port = required("DB_PORT");
        this.name = required("DB_NAME");
        this.user = required("DB_USER");
        this.password = required("DB_PASSWORD");

        }

        public String getUrl(){
            return URL_TEMPLATES.get(type).formatted(host, port, name);
        }

        /** Pasta de migrations do banco escolhido, ex.: classpath:db/migration/mysql */
        public String getMigrationLocation() {
            return "classpath:db/migration/" + type;
        }
}
