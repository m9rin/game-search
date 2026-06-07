# Game Search API

API REST para cadastro, autenticacao e pesquisa de jogos por categorias e plataformas.

O projeto foi desenvolvido em Java com Spring Boot e refatorado para uma **Clean Architecture pragmatica**, separando dominio, casos de uso, infraestrutura e interface REST.

## Tecnologias

- Java 17
- Spring Boot 3
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- Flyway
- H2 para testes
- Maven
- Docker Compose
- Swagger/OpenAPI
- JUnit 5 + AssertJ

## Arquitetura

```text
dev.java10x.gamesearch
├── domain
│   ├── model
│   └── exception
├── application
│   ├── gateway
│   └── usecase
├── infrastructure
│   ├── persistence
│   │   ├── entity
│   │   ├── repository
│   │   ├── mapper
│   │   └── gateway
│   └── security
├── interfaceadapter
│   └── rest
│       ├── controller
│       ├── docs
│       ├── dto
│       └── mapper
└── config
```

Fluxo principal:

```text
Controller -> UseCase -> Gateway -> Infrastructure -> JpaRepository
```

## Principais Recursos

- Registro de usuario
- Login com JWT
- CRUD de categorias
- CRUD de plataformas
- CRUD de jogos
- Busca de jogos por categoria
- Busca de jogos por plataforma
- Validacao de requests
- Respostas de erro padronizadas
- Seed inicial com jogos, categorias e plataformas
- Documentacao Swagger

## Como Rodar

### 1. Clone o repositorio

```bash
git clone https://github.com/m9rin/game-search.git
cd game-search
```

### 2. Configure as variaveis de ambiente

Crie um arquivo `.env` na raiz:

```env
DB_NAME=gamesearch
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=change-me
```

A aplicacao tambem aceita:

```env
DB_URL=jdbc:postgresql://localhost:5432/gamesearch
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=change-me
```

### 3. Suba o banco

```bash
docker compose up -d
```

### 4. Rode a aplicacao

Linux/macOS/WSL:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8080
```

## Swagger

Documentacao interativa:

```text
http://localhost:8080/swagger/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/api/api-docs
```

## Autenticacao

Registre um usuario:

```http
POST /auth/register
```

Exemplo:

```json
{
  "name": "Marin",
  "email": "marin@email.com",
  "password": "123456"
}
```

Faca login:

```http
POST /auth/login
```

Exemplo:

```json
{
  "email": "marin@email.com",
  "password": "123456"
}
```

Resposta:

```json
{
  "token": "jwt-token"
}
```

Use o token nas rotas protegidas:

```http
Authorization: Bearer jwt-token
```

## Endpoints

### Auth

```text
POST /auth/register
POST /auth/login
```

### Categories

```text
POST   /gamesearch/category
GET    /gamesearch/category
GET    /gamesearch/category/{id}
PUT    /gamesearch/category/{id}
DELETE /gamesearch/category/{id}
```

### Platforms

```text
POST   /gamesearch/platform
GET    /gamesearch/platform
GET    /gamesearch/platform/{id}
PUT    /gamesearch/platform/{id}
DELETE /gamesearch/platform/{id}
```

### Games

```text
POST   /gamesearch/game
GET    /gamesearch/game
GET    /gamesearch/game/{id}
PUT    /gamesearch/game/{id}
DELETE /gamesearch/game/{id}
GET    /gamesearch/game/search/category?category={id}
GET    /gamesearch/game/search/platform?platform={id}
```

## Exemplo de Criacao de Jogo

```http
POST /gamesearch/game
```

```json
{
  "title": "The Witcher 3: Wild Hunt",
  "genre": "RPG",
  "releaseDate": "2015-05-19",
  "rating": 9.8,
  "description": "Open-world fantasy RPG.",
  "developer": "CD Projekt Red",
  "publisher": "CD Projekt",
  "categories": [1, 2],
  "platforms": [1, 2, 3]
}
```

## Banco de Dados

As tabelas sao versionadas com Flyway:

```text
src/main/resources/db/migration
```

O projeto cria automaticamente:

- categorias
- plataformas
- jogos
- relacionamento jogo-categoria
- relacionamento jogo-plataforma
- usuarios

Tambem ha seed inicial com exemplos de jogos, categorias e plataformas.

Usuarios devem ser criados pela API em `/auth/register`.

## Testes

Rodar todos os testes:

Linux/macOS/WSL:

```bash
./mvnw clean test
```

Windows:

```bash
mvnw.cmd clean test
```

Os testes usam profile `test` com H2 em memoria.

## Estrategia de Testes

O projeto prioriza testes de use cases, mantendo a regra de negocio testavel sem subir Spring.

Coberturas principais:

- Category use case
- Platform use case
- Game use case
- Register user use case
- Login use case
- Application context com profile de teste

## Erros da API

Os erros seguem formato padronizado:

```json
{
  "timestamp": "2026-06-07T00:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "fields": {
    "email": "Invalid email format"
  }
}
```

Exemplos de status:

```text
400 Bad Request
401 Unauthorized
404 Not Found
409 Conflict
```

## Decisoes de Arquitetura

- O dominio nao depende de Spring, JPA ou HTTP.
- Use cases dependem de gateways, nao de repositories.
- JPA fica isolado em `infrastructure.persistence`.
- JWT e BCrypt ficam em `infrastructure.security`.
- DTOs ficam apenas na camada REST.
- Controllers chamam use cases, nao repositories.
- Regras de negocio sao testadas sem banco real.

## Estrutura Resumida

```text
domain/model
```

Modelos puros da aplicacao.

```text
application/usecase
```

Regras e fluxos de aplicacao.

```text
application/gateway
```

Contratos usados pelos use cases.

```text
infrastructure/persistence
```

JPA, repositories, entities, mappers e gateways concretos.

```text
infrastructure/security
```

JWT e criptografia de senha.

```text
interfaceadapter/rest
```

Controllers, DTOs, mappers e documentacao Swagger.

## Status do Projeto

Projeto em evolucao, com foco em:

- arquitetura limpa
- testes
- seguranca
- documentacao
- boas praticas com Spring Boot

## Autor

Desenvolvido por [m9rin](https://github.com/m9rin).
