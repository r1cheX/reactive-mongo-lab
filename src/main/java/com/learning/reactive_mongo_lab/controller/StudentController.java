package com.learning.reactive_mongo_lab.controller;

import com.learning.reactive_mongo_lab.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/students")
public class StudentController {

    @PostMapping
    public Mono<ResponseEntity<Student>> createStudent(@RequestBody Student student) {
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(student));
    }

//    @GetMapping("/{id}")
//    public Mono<ResponseEntity<Student>> getStudent(@PathVariable String id) {
//        return studentService.getStudent(id)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/{id}")
//    public Mono<ResponseEntity<Student>> updateStudent(@PathVariable String id, @RequestBody Student student) {
//        return studentService.updateStudent(id, student)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
}
