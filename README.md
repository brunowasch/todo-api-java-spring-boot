
# ToDo API - Spring Boot + JWT

## Tecnologias
- Java 17
- Spring Boot
- Spring Security
- JWT
- JPA/Hibernate
- H2 Database
- Swagger

## Executar
```bash
mvn spring-boot:run
```

## Swagger
http://localhost:8080/swagger-ui.html

## Endpoints

### Auth
POST /auth/register

POST /auth/login

### Tasks
GET /tasks

GET /tasks/{id}

POST /tasks

PATCH /tasks/{id}

DELETE /tasks/{id}
