package com.learning.reactive_mongo_lab.repository.custom;


import com.learning.reactive_mongo_lab.dto.SearchFilterDto;
import com.learning.reactive_mongo_lab.model.StudentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface StudentRepositoryCustom  {
    Flux<StudentDocument> findBySearchFilter(SearchFilterDto searchFilterDto);
}
