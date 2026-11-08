# multi-db-boilerplate 🗄️

Um boilerplate evolutivo desenvolvido para praticar operações de CRUD e consolidar conceitos de persistência de dados em Java. O objetivo principal é demonstrar como criar uma arquitetura flexível onde o banco de dados (SQL ou NoSQL) pode ser substituído ou atualizado sem impactar as regras da aplicação.

## 🚀 O que este projeto demonstra?
* **CRUD Genérico:** Operações fundamentais de persistência (Create, Read, Update, Delete) isoladas da lógica principal.
* **Arquitetura Desacoplada:** Uso do padrão *Repository* para isolar o acesso a dados através de interfaces.
* **Linha de Aprendizado Real:** Evolução prática saindo do gerenciamento manual de conexões até a automação com frameworks de mercado.

## 🗺️ Roadmap de Evolução
- [ ] **Fase 1 (Atual):** CRUD via JDBC puro com MySQL (Gerenciamento manual de conexões, PreparedStatements e SQL puro). 🚧
    - [x] Conexão via `.env` + Flyway por `DB_TYPE`
    - [ ] Entity `Aluno`
    - [ ] `AlunoRepository` + `AlunoRepositoryJdbc` (CRUD)
    - [ ] `AlunoService` com regras simples
    - [ ] Datafaker para popular dados
    - [ ] Postgres (`DB_TYPE=postgres`)
    - [ ] Ao concluir: `git tag fase-jdbc`
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
        AlunoRepositoryJdbc (SQL)         pasta db/migration/<DB_TYPE>
                    ▲
                    │ implementa
            AlunoRepository (interface)
                    ▲
                    │ usa
              AlunoService  ◄── Main
```

Fluxo: `Main → AlunoService → AlunoRepository (interface) → AlunoRepositoryJdbc → ConnectionFactory → DatabaseConfig → .env`

## Estrutura de pacotes

```
br.com.dio
 ├─ Main.java
 ├─ config/        DatabaseConfig                      lê variáveis, monta a URL
 ├─ database/      ConnectionFactory, MigrationRunner  abre conexão, roda Flyway
 ├─ entity/        Aluno                               espelho da tabela
 ├─ repository/    AlunoRepository (interface)         contrato do CRUD
 │   └─ jdbc/      AlunoRepositoryJdbc                 implementação JDBC (SQL manual)
 └─ service/       AlunoService                        regras de negócio
```

| Pacote | Responsabilidade | Sobrevive ao Spring/JPA? |
|---|---|---|
| `config` | Ler variáveis e montar a URL | Não (vira `application.yml`) |
| `database` | Conexão e migrations em código | Não (Spring assume) |
| `entity` | Dados da tabela, sem SQL | **Sim** (ganha `@Entity`) |
| `repository` | Acesso a dados, atrás de interface | Interface sim, implementação troca |
| `service` | Regras de negócio, sem SQL | **Sim** |

### Regras do projeto

- SQL fica **somente** na implementação do repositório. Nunca no service nem na entity.
- O service depende da **interface**, nunca da implementação.
- A entity usa `Long id` (nulo antes de salvar), construtor sem argumentos, getters e setters (Lombok). Sem anotações JPA enquanto o projeto for JDBC.
- Migrations (`db/migration/<DB_TYPE>`) não mudam na migração para Spring.

## Implementações de repositório

| Implementação | Status | Observação |
|---|---|---|
| `repository/jdbc/AlunoRepositoryJdbc` | Ativa (Fase 1) | SQL manual com `PreparedStatement` |
| `repository/mongo/AlunoRepositoryMongo` | Planejada (Fase 2) | Driver nativo do MongoDB |
| `repository/jpa/AlunoRepositoryJpa` | Planejada (Fase 3) | Spring Data JPA |

Ao migrar para Spring (Fase 3):
- `config/` e `database/` deixam de ser usados.
- `entity/`, `service/` e as migrations permanecem.
- As implementações anteriores ficam como referência, **sem serem registradas como bean** (sem `@Repository`/`@Component`).

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