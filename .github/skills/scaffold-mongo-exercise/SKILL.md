---
name: scaffold-mongo-exercise
description: 'Scaffolds a lab exercise by generating all HTTP, Controller, DTO, and Service method signatures, leaving only the core Reactive Mongo / Reactor logic as a TODO for the user to implement.'
argument-hint: 'Exercise name, topic, or HTTP endpoint to scaffold'
user-invocable: true
---

# Scaffold Mongo Exercise

## Purpose
Accelerate learning in `reactive-mongo-lab` by handling all repetitive boilerplate (Controller mappings, HTTP request/response handling, DTO creation, and Service interface signatures), leaving only the core **Reactive Mongo** or **Reactor** logic for the user to write.

## When to Use
Use this skill when:
- The user asks to start, prepare, or scaffold a new exercise (e.g., "scaffold GET /students/active", "prepare the exercise for dynamic criteria", "scaffold exercise Level 2").
- The user wants to focus strictly on MongoDB / Reactive logic without spending time writing web boilerplate.
- The user uses keywords like "partial exercise", "scaffold exercise", or "focus on mongo".

## Procedure

1. **Identify Exercise & Scope**
   - Check `LAB_PROGRESS.md` or the user's prompt to determine the exercise requirements (HTTP method, URI, inputs, outputs).
   - Identify which layer contains the MongoDB / Reactor focus area (usually `StudentRepository`, `StudentServiceImpl`, or `StudentController`).

2. **Generate Non-Mongo Boilerplate**
   - **DTOs / Models**: Create or update DTO classes (`StudentDto`, request/response payloads) with appropriate Lombok annotations if needed.
   - **Controller Layer**: Implement the REST endpoint in `StudentController.java` with annotations (`@GetMapping`, `@PostMapping`, `@PathVariable`, `@RequestParam`), proper status codes, and HTTP responses wrapped in `Mono<ResponseEntity<T>>` or `Flux<T>`.
   - **Service Layer Signature**: Add the method signature to `StudentService.java` interface and stub out `StudentServiceImpl.java`.

3. **Insert Core MongoDB / Reactor TODOs & Method Stubs**
   - In the target location (Repository method or Service implementation), place a clear `// TODO [USER FOCUS]:` comment describing the exact query or operator logic to implement.
   - For reactive returns, stub the method returning `Mono.error(new UnsupportedOperationException("Not implemented yet"))` or `Flux.error(new UnsupportedOperationException("Not implemented yet"))`.
   - Example `TODO` formats:
     - Repository: `// TODO [USER FOCUS]: Define reactive query method using Spring Data derived naming or @Query`
     - MongoTemplate: `// TODO [USER FOCUS]: Build Criteria and Query object using ReactiveMongoTemplate`
     - Reactor Operators: `// TODO [USER FOCUS]: Implement transformation using map/flatMap/switchIfEmpty/onErrorResume`

4. **Update Lab Progress Tracking**
   - In `LAB_PROGRESS.md`, find the exercise being scaffolded and set its status to `pending` with today's date in the `Started` column.

5. **Present the Scaffolded Exercise to User**
   - List the files created or modified.
   - Highlight the exact file(s) and line number(s) where the `// TODO [USER FOCUS]:` is located.
   - Provide a sample Gradle / curl command or test setup for the user to verify their solution once they complete the `TODO`.

## Boilerplate vs Focus Rules

| Component | Handled by Skill (Boilerplate) | Left for User (Core Focus) |
|---|---|---|
| **Controller** | Mappings, HTTP Status, path/query param extraction | Mapping to Service calls (if exercise targets Controller) |
| **DTO / Model** | Fields, Getters/Setters/Lombok, conversion helpers | Custom projection interfaces or Mongo document mappings |
| **Repository** | Interface structure, standard CRUD signatures | Custom `@Query`, derived query names, or ReactiveMongoRepository methods |
| **Service Implementation** | Method signatures, dependency injection | MongoTemplate `Criteria`/`Update`/`Aggregation` or Reactor operator chains (`flatMap`, `switchIfEmpty`, etc.) |

## Example Output Structure

When scaffolding an exercise like `GET /students/older-than/{age}`:

- **Controller** (`StudentController.java`): Fully implemented endpoint calling `studentService.getStudentsOlderThan(age)`.
- **Service** (`StudentService.java` & `StudentServiceImpl.java`): Method defined returning `Flux<StudentDto>`.
- **Implementation Placeholder**:
  ```java
  @Override
  public Flux<StudentDto> getStudentsOlderThan(int age) {
      // TODO [USER FOCUS]: Call repository/template to fetch students older than age and map to StudentDto
      return Flux.error(new UnsupportedOperationException("Not implemented yet"));
  }
  ```
