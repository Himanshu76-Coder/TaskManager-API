# TaskManager-API

A beginner-level REST API built with Java and Spring Boot for managing tasks.
It supports full CRUD operations, task status and priority management, filtering, and title search.
This project was built to learn and practice backend development fundamentals.

---

## Table of Contents

1. [Project Description](#project-description)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Project Structure](#project-structure)
5. [API Endpoints](#api-endpoints)
6. [Sample API Responses](#sample-api-responses)
7. [Database Schema](#database-schema)
8. [How to Run the Project](#how-to-run-the-project)
9. [Testing the API](#testing-the-api)
10. [Learning Objectives](#learning-objectives)
11. [Author](#author)

---

## Project Description

TaskManager-API is a simple backend REST API that lets you create, read, update, and delete tasks — just like a to-do list app, but without any frontend.

The purpose of this project is to learn how backend systems work by building something practical from scratch. It was built to understand how a Spring Boot application is structured, how data flows between layers, and how a Java app communicates with a database.

**Backend concepts this project demonstrates:**
- Building REST APIs using Spring Boot
- Layered architecture (Controller → Service → Repository)
- Database integration using Spring Data JPA and Hibernate
- Input validation and error handling
- Filtering and searching data using query parameters
- Testing APIs manually using Postman

---

## Features

- **Create a Task** — Add a new task with title, description, status, priority, and due date
- **Get All Tasks** — Retrieve a list of all tasks stored in the database
- **Get Task by ID** — Fetch a single task using its unique ID
- **Update a Task** — Modify any field of an existing task
- **Delete a Task** — Permanently remove a task from the database
- **Task Status Management** — Each task has a status: `PENDING`, `IN_PROGRESS`, or `COMPLETED`
- **Task Priority Management** — Each task has a priority: `LOW`, `MEDIUM`, or `HIGH`
- **Filter by Status** — Get only tasks that match a specific status
- **Filter by Priority** — Get only tasks that match a specific priority
- **Search by Title** — Find tasks whose title contains a keyword (case-insensitive)
- **Due Date Support** — Optionally set a deadline for each task
- **Input Validation** — Title is required; invalid status and priority values are rejected
- **Error Handling** — Clear and consistent JSON error responses for all failure cases

---

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 3 | Backend framework |
| Spring Web | Building REST API endpoints |
| Spring Data JPA | Database operations |
| Hibernate | ORM — maps Java classes to database tables |
| H2 Database | In-memory database for development and testing |
| MySQL | Relational database for production use |
| Maven | Build tool and dependency management |
| Lombok | Reduces boilerplate code (getters, setters, constructors) |
| Validation | Input validation using `@NotBlank` and `@Valid` |
| Postman | Manual API testing |
| IntelliJ IDEA | IDE used for development |

---

## Project Structure

```
TaskManager-API/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── taskmanager/
│   │   │           └── taskmanager_api/
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   └── TaskController.java               # REST endpoints
│   │   │               ├── dto/
│   │   │               │   ├── TaskRequestDTO.java               # Input DTO
│   │   │               │   └── TaskResponseDTO.java              # Output DTO
│   │   │               ├── entity/
│   │   │               │   └── Task.java                         # JPA entity
│   │   │               ├── exception/
│   │   │               │   ├── GlobalExceptionHandler.java       # Global exception handler
│   │   │               │   └── ResourceNotFoundException.java    # Custom exception
│   │   │               ├── repository/
│   │   │               │   └── TaskRepository.java               # JPA repository
│   │   │               ├── service/
│   │   │               │   └── TaskService.java                  # Business logic
│   │   │               │
│   │   │               └── TaskmanagerApiApplication.java        # Main class
│   │   │
│   │   └── resources/
│   │       └── application.properties                       # Configuration
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── taskmanager/
│                   └── taskmanager_api/
│                       └── TaskmanagerApiApplicationTests.java    # Integration tests
│
├── .gitattributes                                                 # Git attributes
├── .gitignore                                                     # Git ignore rules
├── mvnw                                                           # Maven wrapper (Unix)
├── mvnw.cmd                                                       # Maven wrapper (Windows)
├── pom.xml                                                        # Maven dependencies
└── README.md                                                      # Readme file
```

**Layer responsibilities:**

- **Controller** — Receives HTTP requests, calls the service, returns the response. No business logic here.
- **Service** — Handles all the logic: validation, processing, and calling the repository.
- **Repository** — Directly communicates with the database using Spring Data JPA.
- **Entity** — A Java class that maps directly to the `tasks` table in the database.
- **DTO** — Simple classes used to transfer data between the client and the API cleanly.
- **Exception** — Custom exception class and a global handler that returns clean JSON error messages.

---

## API Endpoints

### Base URL
```
http://localhost:8080/api/tasks
```

### Endpoints Table

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get a task by ID |
| PUT | `/api/tasks/{id}` | Update a task by ID |
| DELETE | `/api/tasks/{id}` | Delete a task by ID |
| GET | `/api/tasks?status=PENDING` | Filter tasks by status |
| GET | `/api/tasks?priority=HIGH` | Filter tasks by priority |
| GET | `/api/tasks?title=keyword` | Search tasks by title keyword |

---

### Endpoint Details

#### POST `/api/tasks` — Create a Task

Request Body:
```json
{
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-12-31"
}
```

| Field | Required | Description |
|---|---|---|
| title | Yes | Name of the task |
| description | No | Optional details |
| status | No | `PENDING` (default), `IN_PROGRESS`, `COMPLETED` |
| priority | No | `LOW`, `MEDIUM`, `HIGH` |
| dueDate | No | Format: `YYYY-MM-DD` |

---

#### GET `/api/tasks` — Get All Tasks

No request body needed. Returns a list of all tasks.

---

#### GET `/api/tasks/{id}` — Get Task by ID

No request body needed. Replace `{id}` with the task ID.
Example: `GET /api/tasks/1`

---

#### PUT `/api/tasks/{id}` — Update a Task

Request Body (same structure as create):
```json
{
  "title": "Buy groceries and vegetables",
  "description": "Milk, eggs, bread, carrots",
  "status": "IN_PROGRESS",
  "priority": "MEDIUM",
  "dueDate": "2025-12-31"
}
```

---

#### DELETE `/api/tasks/{id}` — Delete a Task

No request body needed. Replace `{id}` with the task ID.
Example: `DELETE /api/tasks/1`

---

#### GET `/api/tasks?status=` — Filter by Status

Valid values: `PENDING`, `IN_PROGRESS`, `COMPLETED`

```
GET /api/tasks?status=PENDING
GET /api/tasks?status=IN_PROGRESS
GET /api/tasks?status=COMPLETED
```

---

#### GET `/api/tasks?priority=` — Filter by Priority

Valid values: `LOW`, `MEDIUM`, `HIGH`

```
GET /api/tasks?priority=LOW
GET /api/tasks?priority=MEDIUM
GET /api/tasks?priority=HIGH
```

---

#### GET `/api/tasks?title=` — Search by Title

Returns all tasks whose title contains the keyword (case-insensitive).

```
GET /api/tasks?title=groceries
GET /api/tasks?title=buy
```

---

## Sample API Responses

### Success Response — Create Task (`201 Created`)

```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-12-31",
  "createdAt": "2025-01-01T10:00:00",
  "updatedAt": null
}
```

### Success Response — Delete Task (`200 OK`)

```json
{
  "message": "Task deleted successfully"
}
```

### Error Response — Task Not Found (`404 Not Found`)

```json
{
  "timestamp": "2025-01-01T10:05:00",
  "status": 404,
  "message": "Task not found with id: 99"
}
```

### Error Response — Validation Failed (`400 Bad Request`)

```json
{
  "timestamp": "2025-01-01T10:05:00",
  "status": 400,
  "message": "Title is required"
}
```

### Error Response — Invalid Status or Priority (`400 Bad Request`)

```json
{
  "timestamp": "2025-01-01T10:05:00",
  "status": 400,
  "message": "Invalid status value: UNKNOWN"
}
```

---

## Database Schema

### Table: `tasks`

| Column | Data Type | Constraints | Description |
|---|---|---|---|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique ID for each task |
| title | VARCHAR(255) | NOT NULL | Title of the task |
| description | TEXT | NULLABLE | Optional task details |
| status | VARCHAR(50) | NOT NULL, DEFAULT 'PENDING' | Task status |
| priority | VARCHAR(50) | NULLABLE | Task priority level |
| due_date | DATE | NULLABLE | Optional deadline |
| created_at | DATETIME | NOT NULL | Auto-set on creation |
| updated_at | DATETIME | NULLABLE | Auto-set on update |

### Status Values

| Value | Meaning |
|---|---|
| `PENDING` | Task created but not started |
| `IN_PROGRESS` | Task is currently being worked on |
| `COMPLETED` | Task has been finished |

### Priority Values

| Value | Meaning |
|---|---|
| `LOW` | Not urgent |
| `MEDIUM` | Moderately important |
| `HIGH` | Urgent and important |

### SQL Schema

```sql
CREATE TABLE tasks (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(50)  NOT NULL DEFAULT 'PENDING',
    priority    VARCHAR(50),
    due_date    DATE,
    created_at  DATETIME     NOT NULL,
    updated_at  DATETIME
);
```

> Note: You do not need to run this SQL manually.
> Hibernate automatically creates the table when the application starts
> because `spring.jpa.hibernate.ddl-auto=update` is set in `application.properties`.

---

## How to Run the Project

### Prerequisites

Make sure you have the following installed:
- Java 17
- Maven
- IntelliJ IDEA (or any Java IDE)
- Postman (for testing)
- MySQL (if switching from H2 to MySQL)

---

### Step 1 — Clone the Repository

```bash
git clone https://github.com/Himanshu76-Coder/TaskManager-API.git
cd TaskManager-API
```

---

### Step 2 — Open the Project

Open the project folder in IntelliJ IDEA.
Wait for Maven to download all dependencies automatically.

---

### Step 3 — Configure the Database

The project is currently configured to use **H2 in-memory database** by default.
No setup is needed — just run the app and it works.

**To switch to MySQL:**

1. Create a database in MySQL:
```sql
CREATE DATABASE taskmanager_db;
```

2. Open `src/main/resources/application.properties` and update it:

```properties
# Comment out H2 config
# spring.datasource.url=jdbc:h2:mem:taskmanager_db
# spring.datasource.driver-class-name=org.h2.Driver
# spring.h2.console.enabled=true

# Uncomment MySQL config
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

---

### Step 4 — Run the Application

In IntelliJ IDEA, open `TaskmanagerApiApplication.java` and click the **Run** button.

Or run from terminal:
```bash
mvn spring-boot:run
```

You should see this in the console:
```
Started TaskmanagerApiApplication in X seconds
```

The API is now running at: `http://localhost:8080`

---

### Step 5 — Test with Postman

Open Postman and start sending requests to `http://localhost:8080/api/tasks`.

---

### H2 Console (Development Only)

When using H2, you can view the database in the browser:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:taskmanager_db`
- Username: `root`
- Password: *(leave empty)*

---

## Testing the API

### Base URL
```
http://localhost:8080/api/tasks
```

### Recommended Testing Flow

Follow this order to test all features properly:

**1. Create a few tasks first (POST)**
```
POST http://localhost:8080/api/tasks
```
Create 3–4 tasks with different statuses and priorities so you have data to work with.

**2. Get all tasks (GET)**
```
GET http://localhost:8080/api/tasks
```
Confirm all created tasks appear in the response.

**3. Get a task by ID (GET)**
```
GET http://localhost:8080/api/tasks/1
```
Confirm the correct task is returned.

**4. Update a task (PUT)**
```
PUT http://localhost:8080/api/tasks/1
```
Change the status to `IN_PROGRESS` and confirm the `updatedAt` field is now set.

**5. Filter by status (GET)**
```
GET http://localhost:8080/api/tasks?status=PENDING
```
Confirm only PENDING tasks are returned.

**6. Filter by priority (GET)**
```
GET http://localhost:8080/api/tasks?priority=HIGH
```
Confirm only HIGH priority tasks are returned.

**7. Search by title (GET)**
```
GET http://localhost:8080/api/tasks?title=buy
```
Confirm tasks with "buy" in the title are returned (case-insensitive).

**8. Delete a task (DELETE)**
```
DELETE http://localhost:8080/api/tasks/1
```
Then call `GET /api/tasks/1` to confirm it returns `404 Not Found`.

### Testing Error Cases

| Scenario | Request | Expected Response |
|---|---|---|
| Empty title | `POST` with `"title": ""` | `400` — Title is required |
| Invalid status | `POST` with `"status": "DONE"` | `400` — Invalid status value |
| Invalid priority | `POST` with `"priority": "URGENT"` | `400` — Invalid priority value |
| Task not found | `GET /api/tasks/999` | `404` — Task not found with id: 999 |
| Delete not found | `DELETE /api/tasks/999` | `404` — Task not found with id: 999 |
| No title match | `GET /api/tasks?title=xyz` | `200` — Empty list `[]` |

---

## Learning Objectives

This project was built to practice and demonstrate the following backend skills:

| Skill | How It Is Demonstrated |
|---|---|
| REST API Development | All CRUD endpoints built using Spring Boot `@RestController` |
| CRUD Operations | Create, Read, Update, Delete fully implemented |
| Layered Architecture | Controller → Service → Repository separation |
| Database Integration | Spring Data JPA with H2 and MySQL support |
| Entity Mapping | `Task.java` mapped to `tasks` table using JPA annotations |
| Input Validation | `@NotBlank` on title, custom validation for status and priority |
| Exception Handling | `GlobalExceptionHandler` returns clean JSON error responses |
| DTO Pattern | `TaskRequestDTO` and `TaskResponseDTO` separate API data from entity |
| Query Parameters | Filtering and searching using `@RequestParam` |
| Automatic Timestamps | `@PrePersist` and `@PreUpdate` for `createdAt` and `updatedAt` |

---

## Author

**Himanshu Kumavat**
- This is a beginner-level project built to practice Java backend development with Spring Boot
- GitHub: https://github.com/Himanshu76-Coder
