# DTS Developer Technical Test

A Spring Boot REST API for managing tasks, built as a coding challenge for MOJ Senior Developer.

## Features

- Create, retrieve, update, and delete tasks
- Task status management (`PENDING`, `IN_PROGRESS`, `COMPLETED`)
- Validation for task creation and editing
- Exception handling with meaningful error responses
- CORS configuration for frontend integration
- OpenAPI/Swagger UI documentation

## Tech Stack

- Java 21
- Spring Boot 3.5.x
- Spring Data JPA
- H2 (in-memory, for development)
- Maven
- Lombok
- Swagger/OpenAPI

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+

### Build & Run

```sh
./mvnw clean install
./mvnw spring-boot:run
```

The application will start on [http://localhost:8080](http://localhost:8080).

### API Documentation

After starting, visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) for interactive API docs.

### Example Endpoints

- `GET /tasks/` - List all tasks
- `GET /tasks/{id}` - Get a task by ID
- `POST /tasks/` - Create a new task
- `PUT /tasks/{id}` - Edit an existing task
- `DELETE /tasks/{id}` - Delete a task

### Configuration

See [`src/main/resources/application.properties`](src/main/resources/application.properties) for database and logging settings.

### Running Tests

```sh
./mvnw test
```

## Project Structure

- `src/main/java/com/demo/dts_dev_challenge/` - Main source code
- `src/test/java/com/demo/dts_dev_challenge/` - Unit and integration tests

## Areas for Improvement

- User login, assignment of tasks
- Audit records and versioning
- Validation on progression of Task Status