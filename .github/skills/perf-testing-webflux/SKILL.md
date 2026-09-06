---
name: perf-testing-webflux
description: Index/explain workflow, k6 concurrency testing snippets, and JFR/jcmd/VisualVM thread-inspection steps for comparing non-blocking vs blocking WebFlux endpoints. Use for Level 5 exercises in reactive-mongo-lab.
---

# Performance + threads workflow

## Index/explain loop
1. Run query without index: `db.students.find({age: {$gt: 25}}).explain("executionStats")`
2. Note `totalDocsExamined` vs `nReturned`.
3. Create index: `db.students.createIndex({age: 1})`
4. Re-run the same query + explain, compare `totalDocsExamined`.

## k6 load test skeleton
```javascript
import http from 'k6/http';
export const options = { vus: 100, duration: '30s' };
export default function () {
  http.get('http://localhost:8080/v1/students');
}
```
Run: `k6 run --vus 10 script.js`, then repeat with `--vus 100`, `--vus 500`.

## Non-blocking vs blocking comparison endpoints
```java
@GetMapping("/non-blocking")
public Flux<Student> nonBlocking() { return studentRepository.findAll(); }

@GetMapping("/blocking-demo")
public Flux<Student> blockingDemo() {
    return studentRepository.findAll()
        .doOnNext(s -> { try { Thread.sleep(200); } catch (InterruptedException ignored) {} }); // intentional: simulates blocking work on event loop
}
```
Stress both with k6 and compare latency/throughput.

## Observing threads/JVM
- `jcmd <pid> Thread.print` — see if Netty event-loop threads are blocked (blocking endpoint should show them stuck in `Thread.sleep`).
- JFR: `jcmd <pid> JFR.start duration=60s filename=recording.jfr`, open in JDK Mission Control.
- VisualVM: attach to the running process, watch Threads tab under load.
