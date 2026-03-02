# Identity Service

A REST API built with Spring Boot that implements an OAuth2 Authorization Server and Resource Server, backed by MongoDB.

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.3**
- **Spring Security** — OAuth2 Authorization Server & Resource Server
- **MongoDB** — persistence layer
- **Lombok** — boilerplate reduction
- **SpringDoc OpenAPI** — API documentation (Swagger UI)
- **Spring Actuator** — health and metrics endpoints
- **Testcontainers** — isolated MongoDB instance for tests
- **Docker Compose** — local MongoDB container

---

## Prerequisites

Ensure the following are installed before running the project:

- [Java 21](https://adoptium.net/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Git](https://git-scm.com/)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/tbarland77/identity-service.git
cd identity-service
```

### 2. Configure local credentials

Create the following file (it is gitignored and should never be committed):

**`src/main/resources/application-local.yml`**
```yaml
spring:
  mongodb:
    uri: mongodb://root:secret@localhost:27017/identity_db?authSource=admin
```

### 3. Start MongoDB

```bash
docker-compose -f compose.yml up -d
```

### 4. Run the application

```bash
./gradlew bootRun --args='--spring.profiles.active=dev,local'
```

The application will start on `http://localhost:8080`.

---

## API Documentation

Swagger UI is available once the application is running:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Health Check

Spring Actuator exposes a health endpoint at:

```
http://localhost:8080/actuator/health
```

---

## Running Tests

Tests use Testcontainers to spin up an isolated MongoDB instance automatically — no running Docker Compose instance is required.

```bash
./gradlew test
```

Test reports are generated at:

```
build/reports/tests/test/index.html
```

---

## Project Structure

```
src/
├── main/
│   ├── java/io/github/tbarland/identityservice/
│   └── resources/
│       └── application.yml       # Environment-based configuration
└── test/
    └── java/io/github/tbarland/identityservice/
compose.yml                       # Local MongoDB container
```

---

## Environment Profiles

| Profile | Purpose                          |
|---------|----------------------------------|
| `dev`   | Local development configuration  |
| `prod`  | Production configuration         |
| `local` | Gitignored local credential overrides |

Set the active profile via:

```bash
--spring.profiles.active=dev,local
```

Or via environment variable:

```bash
SPRING_PROFILES_ACTIVE=prod
```
