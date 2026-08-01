# AI_NOTES.md

# AI Notes

## AI Usage

I used ChatGPT as a development assistant throughout this project. Since I was already familiar with Java, Spring Boot, and basic REST API development, I mainly used AI to discuss implementation approaches, review code, troubleshoot errors, and speed up development.

Instead of directly copying generated code, I integrated the suggestions into my project, modified them according to my requirements, and verified every feature through testing using Postman, Swagger, Docker, and JUnit.

---

# 1. Which parts of the code were AI-generated vs. written by you?

## How AI Helped

AI mainly helped me by:

- Reviewing my implementation and suggesting improvements.
- Helping troubleshoot Spring Boot and Maven issues.
- Suggesting cleaner implementations for some methods.
- Explaining different approaches for implementing REST APIs.
- Assisting with Docker configuration.
- Assisting with Swagger (OpenAPI) integration.
- Helping create JUnit test cases.
- Helping improve project documentation.

## Implemented and Modified by Me

After implementing the basic CRUD operations, I made several improvements to make the application closer to a real-world REST API.

- Implemented a separate PATCH endpoint for partial updates.
- Added GET Expense by ID endpoint.
- Added Search Expenses endpoint.
- Added Monthly Summary endpoint.
- Improved Global Exception Handling using `@RestControllerAdvice`.
- Modified validation to return all validation errors together instead of only the first validation error.
- Implemented PUT according to REST principles for complete resource replacement.
- Implemented JSON file persistence using `expenses.json` to store expense data instead of using a database.
- Integrated Swagger and verified all endpoints.
- Dockerized the application by creating the Docker image and running the application inside a Docker container.
- Tested all APIs using Postman, Swagger UI, Docker, and JUnit.

---

## 1. Implemented PATCH Endpoint

The initial implementation only supported updating an expense using the PUT method.

I decided to implement a separate PATCH endpoint because PUT and PATCH serve different purposes.

### PUT

PUT replaces the entire resource.

Example:

Current Expense

```json
{
    "id": 1,
    "title": "Pizza",
    "amount": 450,
    "category": "Food",
    "date": "2026-08-02"
}
```

PUT Request

```json
{
    "title": "Burger",
    "amount": 600,
    "category": "Fast Food",
    "date": "2026-08-10"
}
```

Result

```json
{
    "id": 1,
    "title": "Burger",
    "amount": 600,
    "category": "Fast Food",
    "date": "2026-08-10"
}
```

Everything is replaced.

### PATCH

PATCH updates only the fields sent in the request.

Example:

Current Expense

```json
{
    "id": 1,
    "title": "Pizza",
    "amount": 450,
    "category": "Food",
    "date": "2026-08-02"
}
```

PATCH Request

```json
{
    "amount": 600
}
```

Result

```json
{
    "id": 1,
    "title": "Pizza",
    "amount": 600,
    "category": "Food",
    "date": "2026-08-02"
}
```

Only the amount changes while the remaining fields stay unchanged.

I implemented PATCH because it follows REST best practices and avoids sending unnecessary data when only a few fields need to be updated.

---

## 2. Get Expense by ID

I added an endpoint to retrieve a single expense using its unique ID.

```
GET /expenses/{id}
```

This returns the complete information of the requested expense.

---

## 3. Improved Validation Response

Initially, validation returned only the first validation error.

Example request

```json
{
    "title": "",
    "amount": -100,
    "category": "",
    "date": null
}
```

Earlier response

```json
{
    "timestamp": "2026-08-01T22:13:29",
    "status": 400,
    "error": "Bad Request",
    "message": "Category is required"
}
```

I modified the Global Exception Handler so that all validation errors are returned together.

Current response

```json
{
    "timestamp": "2026-08-02T10:15:00",
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

This reduces unnecessary API calls because the client can fix all invalid fields in a single request.

---

## 4. Global Exception Handling

I implemented centralized exception handling using `@RestControllerAdvice`.

The application now handles:

- Validation errors
- Resource not found exceptions
- Internal server errors

and returns consistent JSON responses.

---

## 5. Swagger Documentation

I integrated Swagger (OpenAPI) into the project.

Swagger automatically generates documentation for every endpoint and also allows API testing directly from the browser.

Useful URLs

```
http://localhost:8080/swagger-ui/index.html
```

```
http://localhost:8080/v3/api-docs
```

---

## 6. Docker Support

I containerized the application using Docker.

This included:

- Creating a Dockerfile.
- Building a Docker image.
- Running the application inside a Docker container.
- Verifying that all APIs work correctly from the container.

---

## 7. Additional Features

Apart from the basic CRUD operations, I also implemented:

- Get Expense by ID
- Search Expenses
- Monthly Summary Endpoint
- PUT Endpoint
- PATCH Endpoint
- JSON File Persistence using `expenses.json`
- JUnit Integration Tests

---

# 2. What did you validate, test, or change in the AI's output, and why?

## Suggestions I Modified

I did not use every AI suggestion exactly as generated.

Before adding any code into the project, I:

- Reviewed the generated implementation.
- Modified it according to the project requirements.
- Fixed compilation and runtime issues.
- Refactored repeated code where necessary.
- Improved validation handling.
- Improved exception handling.
- Verified every endpoint manually using Postman, Swagger, and Docker.
- Verified that data is correctly stored and retrieved from `expenses.json`.
- Verified that Create (POST), Update (PUT), Partial Update (PATCH), and Delete (DELETE) operations correctly update `expenses.json`.
- Ran JUnit test cases before finalizing the implementation.

---

# 3. Any AI suggestion you decided not to use, and why?

## Suggestions I Chose Not to Use

Some suggestions were intentionally not implemented because they were outside the scope of this assignment.

### Authentication and Login

I did not implement authentication because this project is designed as a personal expense tracker API.

The assignment focuses on expense management and REST API functionality rather than user management.

Adding authentication would increase the complexity without providing significant value for the current project.

---

### Database Integration

I intentionally used `expenses.json` for data persistence instead of MySQL or PostgreSQL.

The assignment did not require a database, and using a JSON file keeps the project lightweight, simple to set up, and easy to run without any additional database configuration while still demonstrating persistent CRUD operations.

---

### Pagination

I chose not to implement pagination because the application is expected to manage a relatively small number of expense records.

Returning the complete expense list is sufficient for the current use case.

---

### Duplicate Expense Validation

I considered preventing duplicate expense entries.

However, two expenses can legitimately have the same title, amount, category, and even the same date.

For example, purchasing coffee twice on the same day should still be recorded as two separate expenses.

For this reason, I decided not to restrict duplicate entries.

---

### Additional Logging Framework

I did not add a separate logging framework because Spring Boot's built-in logging is sufficient for the requirements of this project.

---

## Testing and Verification

After implementing every feature, I verified the application by:

- Testing every REST endpoint using Postman.
- Testing endpoints using Swagger UI.
- Verifying JSON file persistence after create, update, delete, PUT, and PATCH operations.
- Verifying that all changes are correctly reflected in `expenses.json`.
- Testing validation using invalid request bodies.
- Testing exception handling.
- Running JUnit test cases.
- Building and running the application using Docker.

---

## Summary

AI was used as a development assistant to review ideas, discuss implementation approaches, troubleshoot issues, and improve the overall quality of the project.

The final implementation was integrated, modified, tested, and refined by me. I implemented JSON file persistence using `expenses.json`, verified all CRUD operations, added PATCH support, improved validation responses, implemented GET by ID, Search, Monthly Summary, integrated Swagger and Docker, and tested the application using Postman, Swagger, Docker, and JUnit before submission.