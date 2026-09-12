package com.learning.reactive_mongo_lab.service;

import com.learning.reactive_mongo_lab.dto.SearchFilterDto;
import com.learning.reactive_mongo_lab.dto.SkillCountDto;
import com.learning.reactive_mongo_lab.dto.StudentDto;
import com.learning.reactive_mongo_lab.dto.StudentProjectionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Mono<StudentDto> save(StudentDto student);

    Flux<StudentDto> getStudents();

    Mono<StudentDto> getStudent(String id);

    Mono<StudentDto> updateStudent(String id, StudentDto student);

    Mono<Boolean> deleteStudent(String id);

    Flux<StudentDto> getActiveStudents();

    Flux<StudentDto> getStudentsOlderThan(int age);

    Flux<StudentDto> getStudentsBySkill(String skill);

    Flux<StudentDto> getStudentsBySkills(List<String> skills);

    Flux<StudentDto> searchStudents(SearchFilterDto searchFilterDto);

    Mono<StudentDto> partialUpdateStudent(String id, Map<String, Object> updates);

    Flux<StudentProjectionDto> getStudentProjection();

    Flux<SkillCountDto> getSkillSummary();
}
