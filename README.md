# User Service

Microservice for user management with event-driven communication via Apache Kafka.

## 📝 Description

User Service is a RESTful microservice for user management that provides the following capabilities:

- ✅ User creation
- ✅ Get user by ID
- ✅ Get list of all users
- ✅ Update user information
- ✅ Delete user
- ✅ Asynchronous event publishing to Kafka for all operations

The service uses PostgreSQL for data storage and Apache Kafka for event-driven communication. 
All user operations automatically publish events to Kafka, which can be processed by other services.

## 🏗️ Architecture

The project is built on a modular architecture with layer separation:

```
user-service/
├── user-service-db/          # Database migration module (Liquibase)
├── user-service-domain/      # Domain layer (entities, repositories)
└── user-service-impl/        # Implementation layer (controllers, services, Kafka)
```

### Architecture Layers:

1. **Controller Layer** — REST API controllers
2. **Service Layer** — business logic and operation processing
3. **Repository Layer** — data access via JPA
4. **Kafka Layer** — event publishing and consumption via Kafka

## 🛠️ Technology Stack

### Core Technologies:

- **Java 21** — programming language
- **Spring Boot 3.2.5** — framework for microservice development
- **Spring Data JPA** — database operations
- **PostgreSQL 15** — relational database
- **Apache Kafka 7.5.0** — message broker for event-driven communication
- **Liquibase** — database migration management
- **Docker & Docker Compose** — containerization

### Additional Libraries:

- **Lombok** — reducing boilerplate code
- **MapStruct** — mapping between DTOs and entities
- **SpringDoc OpenAPI** — API documentation (Swagger)
- **Spring HATEOAS** — HATEOAS support in REST API

## 📝 License

This project is created for educational purposes.

## 👤 Author

**Iakov Lysenko**

---

## 🔗 Useful Links

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Liquibase Documentation](https://www.liquibase.org/documentation)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
