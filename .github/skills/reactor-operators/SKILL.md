---
name: reactor-operators
description: Reference for the 6 highest-value Reactor operators (map, filter, flatMap, switchIfEmpty, onErrorResume, zip) with Mongo-flavored examples and a map-vs-flatMap decision checklist. Use for Level 3 exercises in reactive-mongo-lab.
---

# Reactor operators (the 20% that matters)

## map vs flatMap — decision checklist
1. Does the transform return a plain value (not a `Mono`/`Flux`)? → `map`
2. Does the transform call another reactive source (repository, service, another Mono)? → `flatMap`
3. Using `map` with a function that returns `Mono<X>` produces `Mono<Mono<X>>` — a common bug. If you see nested reactive types, you needed `flatMap`.

```java
// map: sync transform
studentMono.map(Student::getName);

// flatMap: chains another reactive call
studentRepository.findById(id)
    .flatMap(student -> otherRepo.findRelated(student.getId()));
```

## filter
```java
studentFlux.filter(Student::isActive);
```

## switchIfEmpty
```java
studentRepository.findById(id)
    .switchIfEmpty(Mono.error(new NotFoundException(id)));
// or provide a fallback value instead of erroring:
studentRepository.findById(id)
    .switchIfEmpty(Mono.just(Student.guest()));
```

## onErrorResume
```java
studentRepository.findById(id)
    .onErrorResume(DataAccessException.class, e -> Mono.empty());
```

## zip
```java
Mono.zip(studentRepository.findById(id1), studentRepository.findById(id2))
    .map(tuple -> combine(tuple.getT1(), tuple.getT2()));
```

## Common lab exercise shapes
- Transform a `Student` into a DTO → `map`
- Look up a related document by id found on the first result → `flatMap`
- Return 404 when a `findById` is empty → `switchIfEmpty`
- Swallow/convert a Mongo error into an empty result or fallback → `onErrorResume`
- Combine two independent reactive calls into one response → `zip`
