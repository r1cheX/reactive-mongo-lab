package com.learning.reactive_mongo_lab.service;

import com.learning.reactive_mongo_lab.dto.StudentDto;
import com.learning.reactive_mongo_lab.model.Student;
import com.learning.reactive_mongo_lab.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public Mono<StudentDto> save(StudentDto student) {
        Student entity = Student.builder().name(student.getName()).age(student.getAge()).active(student.isActive()).skills(student.getSkills()).build();
        return studentRepository.save(entity)
                        .doOnNext(savedStudent -> log.info("Saved student: {}", savedStudent))
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
                .flatMap(existingStudent -> {
                    existingStudent.setName(student.getName());
                    existingStudent.setAge(student.getAge());
                    existingStudent.setActive(student.isActive());
                    existingStudent.setSkills(student.getSkills());
                    return studentRepository.save(existingStudent);
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
    public Flux<StudentDto> searchStudents(Boolean active, Integer olderThan, String skill) {
        return studentRepository.findByActiveAndAgeGreaterThanAndSkillsContains(active, olderThan, skill).map(this::mapTo);
    }

    private StudentDto mapTo(Student savedStudent) {
        return StudentDto.builder()
                .name(savedStudent.getName())
                .age(savedStudent.getAge())
                .active(savedStudent.isActive())
                .skills(savedStudent.getSkills())
                .build();
    }
}
