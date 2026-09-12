# reactive-mongo-lab — Copilot instructions

## Stack (facts, don't re-derive)
- Java 25 toolchain, Spring Boot 4.1.1, **Gradle** (`build.gradle`, not Maven)
- Spring WebFlux + Reactor, Spring Data Reactive MongoDB, Lombok
- MongoDB via Docker, inspected with MongoDB Compass
- Package layout: `com.learning.reactive_mongo_lab.{controller,model,service,repository}`
- Existing skeleton classes (`Student`, `StudentController`, `StudentService`, `StudentRepository`) are the implementation surface — build into them, don't create parallel structures

## Response rules
- Answer in English.
- Be concise by default: code + at most a 1-line rationale. No theory dumps unless explicitly asked.
- Never scaffold the whole project at once. This is a learning lab — one exercise at a time.
- Don't execute any commands or run tests.

## Exercise workflow
- This repo is worked through progressively via `LAB_PROGRESS.md` (levels 1-5, see file for curriculum).
- Before answering exercise-related questions, check `LAB_PROGRESS.md` for current level/status.
- Use `/next-exercise` to get the next exercise, `/review-exercise` to review current work against the pending one.
- Don't jump ahead levels or give the full solution to a pending exercise unless explicitly asked.
