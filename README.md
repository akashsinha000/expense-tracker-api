# Smart Expense Tracker API

A RESTful API built using **Spring Boot** for managing personal expenses. The application allows users to create, retrieve, update, search, summarize, and delete expenses. Expense data is stored in a local JSON file (`expenses.json`), so no external database is required.

---

## Features

- Add a new expense
- View all expenses
- View expense by ID
- Filter expenses by category
- Search expenses by title or category
- Calculate total expenses
- Calculate total expenses by category
- View monthly expense summary
- Replace an expense using PUT
- Partially update an expense using PATCH
- Delete an expense
- Input validation
- Global exception handling
- Swagger/OpenAPI documentation
- Docker support
- JUnit test cases

---

## Technologies Used

- Java 17
- Spring Boot 3.5.5
- Maven
- Jackson
- Jakarta Validation
- SpringDoc OpenAPI (Swagger)
- JUnit 5
- Docker

---

## Project Structure

```
expense-tracker-api
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.expensetracker.expensetrackerapi
│   │   │       ├── config
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       ├── exception
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── service
│   │   │       └── ExpenseTrackerApiApplication.java
│   │   └── resources
│   │
│   └── test
│       └── java
│           └── com.expensetracker.expensetrackerapi
│               └── ExpenseControllerTest.java
│
├── expenses.json
├── Dockerfile
├── pom.xml
├── README.md
└── AI_NOTES.md
```

---

# Getting Started

## Clone the Repository

```bash
git clone https://github.com/akashsinha000/expense-tracker-api.git
```

Move into the project directory

```bash
cd expense-tracker-api
```

---

# Spring Boot Commands

### Clean the project

```bash
mvn clean
```

### Compile the project

```bash
mvn compile
```

### Run the application

```bash
mvn spring-boot:run
```

Application URL

```
http://localhost:8080
```

### Run all tests

```bash
mvn test
```

### Package the application

```bash
mvn package
```

or

```bash
mvn clean package
```

Generated JAR

```
target/expense-tracker-api-0.0.1-SNAPSHOT.jar
```

### Run the JAR

```bash
java -jar target/expense-tracker-api-0.0.1-SNAPSHOT.jar
```

### Install dependencies and build

```bash
mvn clean install
```

---

# Swagger Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI Specification

```
http://localhost:8080/v3/api-docs
```

---

# Docker Commands

### Build Docker Image

```bash
docker build -t expense-tracker .
```

### View Images

```bash
docker images
```

### Run Container

```bash
docker run -p 8080:8080 expense-tracker
```

If port 8080 is already in use

```bash
docker run -p 8081:8080 expense-tracker
```

### View Running Containers

```bash
docker ps
```

### View All Containers

```bash
docker ps -a
```

### Stop Container

```bash
docker stop <container_id>
```

### Remove Container

```bash
docker rm <container_id>
```

### Remove Docker Image

```bash
docker rmi expense-tracker
```

---

# Git Commands

### Check Status

```bash
git status
```

### Add Files

```bash
git add .
```

### Commit Changes

```bash
git commit -m "Commit message"
```

### Push Changes

```bash
git push
```

### Pull Latest Changes

```bash
git pull
```

---

# API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/expenses` | Add a new expense |
| GET | `/expenses` | Get all expenses |
| GET | `/expenses/{id}` | Get expense by ID |
| GET | `/expenses?category=Food` | Filter by category |
| GET | `/expenses/search?keyword=food` | Search by title or category |
| GET | `/expenses/total` | Get total expenses |
| GET | `/expenses/total?category=Food` | Get total by category |
| GET | `/expenses/monthly-summary?year=2026&month=8` | Monthly expense summary |
| PUT | `/expenses/{id}` | Replace an expense |
| PATCH | `/expenses/{id}` | Partially update an expense |
| DELETE | `/expenses/{id}` | Delete an expense |

---

# Sample Request

### Add Expense

**POST** `/expenses`

```json
{
    "title": "Groceries",
    "amount": 850,
    "category": "Food",
    "date": "2026-08-02"
}
```

---

# Sample Response

```json
{
    "id": 1,
    "title": "Groceries",
    "amount": 850,
    "category": "Food",
    "date": "2026-08-02"
}
```

---

# Validation

The application validates user input before processing requests.

Validation rules:

- Title cannot be empty
- Amount must be greater than 0
- Category cannot be empty
- Date is required

Example validation response

```json
{
    "timestamp": "2026-08-02T01:20:00",
    "status": 400,
    "error": "Bad Request",
    "errors": {
        "title": "Title is required",
        "amount": "Amount must be greater than 0",
        "category": "Category is required",
        "date": "Date is required"
    }
}
```

---

# Exception Handling

The application uses a global exception handler (`@RestControllerAdvice`) to return consistent error responses.

Handled exceptions include:

- 400 Bad Request
- 404 Not Found
- 500 Internal Server Error

---

# Data Storage

Expense data is stored locally in:

```
expenses.json
```

No external database is required to run the application.

---

# Running Tests

Execute all test cases using:

```bash
mvn test
```

The project includes JUnit integration tests for:

- Add Expense
- View Expenses
- Get Expense by ID
- Search Expenses
- Monthly Summary
- PUT
- PATCH
- DELETE
- Validation

---

# Author

**Akash Sinha**

GitHub: https://github.com/akashsinha000
