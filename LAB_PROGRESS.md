# Lab progress — reactive-mongo-lab

Current level: **1 — CRUD reactivo**

## Level 1 — CRUD reactivo
| Exercise | Status | Started | Done |
|---|---|---|---|
| POST /students | done | 2026-09-06 | 2026-09-06 |
| GET /students | done | 2026-09-06 | 2026-09-06 |
| GET /students/{id} | done | 2026-09-06 | 2026-09-06 |
| PUT /students/{id} | done | 2026-09-06 | 2026-09-06 |
| DELETE /students/{id} | done | 2026-09-06 | 2026-09-06 |

## Level 2 — Queries
| Exercise | Status | Started | Done |
|---|---|---|---|
| GET /students/active | done | 2026-09-06 | 2026-09-06 |
| GET /students/older-than/{age} | done | 2026-09-06 | 2026-09-06 |
| GET /students/skill/{skill} | done | 2026-09-06 | 2026-09-06 |
| combined filters (Repository vs Mongo-side) | done | 2026-09-12 | 2026-09-12 |

## Level 3 — Reactor operators
| Exercise | Status | Started | Done |
|---|---|---|---|
| map vs flatMap case | standby | | |
| switchIfEmpty case | standby | | |
| onErrorResume case | standby | | |
| zip case | standby | | |

## Level 4 — Reactive Mongo avanzado
| Exercise | Status | Started | Done |
|---|---|---|---|
| dynamic criteria query (MongoTemplate) | done | 2026-09-12 | 2026-09-12 |
| partial update (MongoTemplate) | done | 2026-09-12 | 2026-09-12 |
| projection | done | 2026-09-12 | 2026-09-12 |
| minimal aggregation | done | 2026-09-12 | 2026-09-12 |

## Level 5 — MongoDB Query Performance

| Exercise | Status | Started | Done |
|---|---|---|---|
| seed synthetic dataset (100k–500k customers) |  |  |  |
| query without index + `explain()` |  |  |  |
| inspect `COLLSCAN` vs `IXSCAN`, `docsExamined` vs `nReturned` |  |  |  |
| create simple/compound index + re-run `explain()` |  |  |  |
| pagination + sort on dynamic query |  |  |  |
| regex search vs indexed exact/prefix search |  |  |  |
| k6 load test: 10 / 100 / 500 VUs |  |  |  |
| compare blocking `Thread.sleep` vs non-blocking endpoint |  |  |  |
