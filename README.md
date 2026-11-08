# multi-db-boilerplate 🗄️

Um boilerplate evolutivo desenvolvido para praticar operações de CRUD e consolidar conceitos de persistência de dados em Java. O objetivo principal é demonstrar como criar uma arquitetura flexível onde o banco de dados (SQL ou NoSQL) pode ser substituído ou atualizado sem impactar as regras da aplicação.

## 🚀 O que este projeto demonstra?
* **CRUD Genérico:** Operações fundamentais de persistência (Create, Read, Update, Delete) isoladas da lógica principal.
* **Arquitetura Desacoplada:** Uso do padrão *Repository/DAO* para isolar o acesso a dados através de interfaces.
* **Linha de Aprendizado Real:** Evolução prática saindo do gerenciamento manual de conexões até a automação com frameworks de mercado.

## 🗺️ Roadmap de Evolução
- [ ] **Fase 1 (Atual):** CRUD via JDBC puro com MySQL (Gerenciamento manual de conexões, PreparedStatements e SQL puro). 🚧
- [ ] **Fase 2:** CRUD via NoSQL (MongoDB) utilizando drivers nativos. ⏳
- [ ] **Fase 3:** Migração para o ecossistema Spring Boot (Inversão de Controle, Spring Data JPA e Spring Data MongoDB). ⏳

## Arquitetura

```
.env → (Gradle / IntelliJ) → variáveis de ambiente
                                   │
                                   ▼
                            DatabaseConfig      lê DB_TYPE, host, porta, banco, user, senha
                            (monta a URL)       e entrega: getUrl(), getUser(), getPassword(),
                                   │            getMigrationLocation()
                    ┌──────────────┴──────────────┐
                    ▼                             ▼
            ConnectionFactory              MigrationRunner
            abre a Connection              roda as migrations do Flyway
                    │                             │
                    ▼                             ▼
          Main / DAOs (JDBC)            pasta db/migration/<DB_TYPE>
```

| Classe | Pacote | Responsabilidade |
|---|---|---|
| `DatabaseConfig` | `br.com.dio.config` | Só lê as variáveis e monta a URL JDBC |
| `ConnectionFactory` | `br.com.dio.config` | Só abre a conexão usando o config |
| `MigrationRunner` | `br.com.dio.migration.MigrationRunner` | Só executa o Flyway usando o config |

## Variáveis de ambiente (.env)

| Variável | Obrigatória | Exemplo |
|---|---|---|
| `DB_TYPE` | não (padrão `mysql`) | `mysql` ou `postgres` |
| `DB_HOST` | sim | `192.168.x.x` |
| `DB_PORT` | sim | `3307` |
| `DB_NAME` | sim | `aulas` |
| `DB_USER` | sim | `dev` |
| `DB_PASSWORD` | sim | (nunca commitar) |

Copie `.env.example` para `.env` e preencha.

## Migrations por tipo de banco

As migrations ficam em pastas separadas por banco:

```
src/main/resources/db/migration/
 ├─ mysql/      V1__create_table.sql, V2__...
 └─ postgres/   V1__create_table.sql, ...
```

- O `DB_TYPE` define **qual pasta** o Flyway lê (`classpath:db/migration/<DB_TYPE>`).
- Só uma pasta é executada por vez, no banco ao qual o programa está conectado.
- Cada banco tem seu próprio histórico (`flyway_schema_history`) e sua própria numeração.
- Migrations rodam **ao iniciar o programa** (`MigrationRunner.run(...)` na `Main`).
- Nome: `V<número>__<descrição>.sql` (dois underscores). Migration já aplicada não se edita: mudança nova = arquivo novo.

## Como adicionar um novo banco

1. Dependências no `build.gradle.kts`: driver JDBC + módulo do Flyway.
2. Incluir a URL do banco no `URL_TEMPLATES` do `DatabaseConfig`.
3. Criar `db/migration/<tipo>/V1__...sql`.
4. Definir `DB_TYPE=<tipo>` no `.env`.