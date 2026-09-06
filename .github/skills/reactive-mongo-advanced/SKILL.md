---
name: reactive-mongo-advanced
description: ReactiveMongoRepository vs ReactiveMongoTemplate, dynamic Criteria queries, updates, projections, and minimal aggregation. Use for Level 2/4 exercises in reactive-mongo-lab.
---

# Reactive Mongo: Repository vs Template

## When Repository is enough
- Fixed-shape derived queries: `findByActiveTrue()`, `findByAgeGreaterThan(int age)`, `findBySkillsContaining(String skill)`.
- No conditional/optional filters.

## When MongoTemplate earns its keep
- Query shape depends on which filters are present (optional name/age/skill combos).
- You need partial updates (`update()`) instead of full document `save()`.
- You need field projections or a small aggregation pipeline.

```java
// Dynamic criteria: only add clauses for non-null filters
Criteria criteria = new Criteria();
List<Criteria> clauses = new ArrayList<>();
if (active != null) clauses.add(Criteria.where("active").is(active));
if (minAge != null) clauses.add(Criteria.where("age").gte(minAge));
if (!clauses.isEmpty()) criteria = criteria.andOperator(clauses.toArray(new Criteria[0]));
Query query = new Query(criteria);

reactiveMongoTemplate.find(query, Student.class);
```

```java
// Partial update instead of full save()
Update update = new Update().set("active", false);
reactiveMongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), update, Student.class);
```

```java
// Projection: only fetch name + age
Query query = new Query().fields().include("name", "age");
```

```java
// Minimal aggregation: count students per skill
Aggregation agg = Aggregation.newAggregation(
    Aggregation.unwind("skills"),
    Aggregation.group("skills").count().as("total")
);
reactiveMongoTemplate.aggregate(agg, Student.class, Document.class);
```

## Index basics
- `@Indexed` on a field, or compound index via `@CompoundIndex` on the class.
- Verify with Compass → Indexes tab, or `db.students.getIndexes()`.
