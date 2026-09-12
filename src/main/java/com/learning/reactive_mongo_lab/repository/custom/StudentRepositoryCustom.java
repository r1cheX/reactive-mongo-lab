package com.learning.reactive_mongo_lab.repository.custom;


import com.learning.reactive_mongo_lab.dto.SearchFilterDto;
import com.learning.reactive_mongo_lab.dto.SkillCountDto;
import com.learning.reactive_mongo_lab.dto.StudentProjectionDto;
import com.learning.reactive_mongo_lab.model.StudentDocument;
import org.bson.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface StudentRepositoryCustom  {
    Flux<StudentDocument> findBySearchFilter(SearchFilterDto searchFilterDto);

    Mono<StudentDocument> partialUpdateStudent(String id, Map<String, Object> updates);

    Flux<StudentProjectionDto> findProjectedStudents();

    Flux<SkillCountDto> countStudentsBySkill();
}
