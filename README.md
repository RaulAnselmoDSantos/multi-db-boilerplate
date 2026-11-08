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
