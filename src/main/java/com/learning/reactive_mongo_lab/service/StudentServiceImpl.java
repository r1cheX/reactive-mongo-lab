package com.learning.reactive_mongo_lab.service;

import com.learning.reactive_mongo_lab.dto.SearchFilterDto;
import com.learning.reactive_mongo_lab.dto.SkillCountDto;
import com.learning.reactive_mongo_lab.dto.StudentDto;
import com.learning.reactive_mongo_lab.dto.StudentProjectionDto;
import com.learning.reactive_mongo_lab.model.StudentDocument;
import com.learning.reactive_mongo_lab.repository.StudentRepository;
import com.learning.reactive_mongo_lab.util.exceptions.StudentNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public Mono<StudentDto> save(StudentDto student) {
        StudentDocument entity = StudentDocument.builder().name(student.getName()).age(student.getAge()).active(student.isActive()).skills(student.getSkills()).build();
        return studentRepository.save(entity)
                        .doOnNext(savedStudentDocument -> log.info("Saved student: {}", savedStudentDocument))
                        .map(this::mapTo);
    }

    @Override
    public Flux<StudentDto> getStudents() {
        return studentRepository.findAll()
                .map(this::mapTo);
    }

    @Override
    public Mono<StudentDto> getStudent(String id) {
        return studentRepository.findById(id)
                .map(this::mapTo);
    }

    @Override
    public Mono<StudentDto> updateStudent(String id, StudentDto student) {
        return studentRepository.findById(id)
                .flatMap(existingStudentDocument -> {
                    existingStudentDocument.setName(student.getName());
                    existingStudentDocument.setAge(student.getAge());
                    existingStudentDocument.setActive(student.isActive());
                    existingStudentDocument.setSkills(student.getSkills());
                    return studentRepository.save(existingStudentDocument);
                })
                .map(this::mapTo);
    }

    @Override
    public Mono<Boolean> deleteStudent(String id) {
        return studentRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return studentRepository.deleteById(id).thenReturn(true);
                    } else {
                        return Mono.just(false);
                    }
                });
    }

    @Override
    public Flux<StudentDto> getActiveStudents() {
        return studentRepository.findByActiveTrue()
                .map(this::mapTo);
    }

    @Override
    public Flux<StudentDto> getStudentsOlderThan(int age) {
        return studentRepository.findByAgeGreaterThan(age).map(this::mapTo);
    }

    @Override
    public Flux<StudentDto> getStudentsBySkill(String skill) {
        return studentRepository.findBySkillsContaining(skill).map(this::mapTo);
    }

    @Override
    public Flux<StudentDto> getStudentsBySkills(List<String> skills) {
        return studentRepository.findBySkillsIn(Collections.singleton(skills)).map(this::mapTo);
    }

    @Override
    public Flux<StudentDto> searchStudents(SearchFilterDto searchFilterDto) {
        return studentRepository.findBySearchFilter(searchFilterDto).map(this::mapTo);
    }

    @Override
    public Mono<StudentDto> partialUpdateStudent(String id, Map<String, Object> updates) {
        return studentRepository.partialUpdateStudent(id, updates)
                .map(this::mapTo)
                .switchIfEmpty(Mono.error(new StudentNotFoundException(id)));
    }

    @Override
    public Flux<StudentProjectionDto> getStudentProjection() {
        return studentRepository.findProjectedStudents();
    }

    @Override
    public Flux<SkillCountDto> getSkillSummary() {
        return studentRepository.countStudentsBySkill();
    }

    private StudentDto mapTo(StudentDocument savedStudentDocument) {
        return StudentDto.builder()
                .name(savedStudentDocument.getName())
                .age(savedStudentDocument.getAge())
                .active(savedStudentDocument.isActive())
                .skills(savedStudentDocument.getSkills())
                .build();
    }



}
