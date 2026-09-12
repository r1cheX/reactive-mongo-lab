package com.learning.reactive_mongo_lab.repository.custom;

import com.learning.reactive_mongo_lab.dto.SearchFilterDto;
import com.learning.reactive_mongo_lab.model.StudentDocument;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public StudentRepositoryCustomImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Flux<StudentDocument> findBySearchFilter(SearchFilterDto searchFilterDto) {
        Query query = new Query();

        if(searchFilterDto.active() != null){
            CriteriaDefinition activeCriteria = Criteria.where("active").is(searchFilterDto.active());
            query.addCriteria(activeCriteria);
        }

        if(searchFilterDto.olderThan() != null){
            CriteriaDefinition activeCriteria = Criteria.where("age").gt(searchFilterDto.olderThan());
            query.addCriteria(activeCriteria);
        }

        if(searchFilterDto.skill() != null){
            CriteriaDefinition activeCriteria = Criteria.where("skills").in(searchFilterDto.skill());
            query.addCriteria(activeCriteria);
        }

        return reactiveMongoTemplate.find(query, StudentDocument.class);
    }
}
