package com.learning.reactive_mongo_lab.controller;

import com.learning.reactive_mongo_lab.dto.SearchFilterDto;
import com.learning.reactive_mongo_lab.dto.StudentDto;
import com.learning.reactive_mongo_lab.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public Mono<ResponseEntity<StudentDto>> createStudent(@Valid @RequestBody StudentDto studentDto) {
        return studentService.save(studentDto)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }

    @GetMapping
    public Flux<StudentDto> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/active")
    public Flux<StudentDto> getActiveStudents() {
        return studentService.getActiveStudents();
    }

    @GetMapping("/older-than/{age}")
    public Flux<StudentDto> getStudentsOlderThan(@PathVariable int age) {
        return studentService.getStudentsOlderThan(age);
    }

    @GetMapping("/skill/{skill}")
    public Flux<StudentDto> getStudentsBySkill(@PathVariable String skill) {
        return studentService.getStudentsBySkill(skill);
    }

    @PostMapping("/skills")
    public Flux<StudentDto> getStudentsBySkills(@RequestBody List<String> skills) {
        return studentService.getStudentsBySkills(skills);
    }

    @GetMapping("/search")
    public Flux<StudentDto> searchStudents(@ModelAttribute SearchFilterDto searchFilterDto) {
        return studentService.searchStudents(searchFilterDto);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<StudentDto>> getStudent(@PathVariable String id) {
        return studentService.getStudent(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<StudentDto>> updateStudent(
            @PathVariable String id,
            @Valid @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(id, studentDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable String id) {
        return studentService.deleteStudent(id)
                .filter(Boolean::booleanValue)
                .map(deleted -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
