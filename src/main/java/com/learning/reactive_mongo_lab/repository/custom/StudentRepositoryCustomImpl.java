package com.learning.reactive_mongo_lab.repository.custom;

import com.learning.reactive_mongo_lab.dto.SearchFilterDto;
import com.learning.reactive_mongo_lab.dto.SkillCountDto;
import com.learning.reactive_mongo_lab.dto.StudentProjectionDto;
import com.learning.reactive_mongo_lab.model.StudentDocument;
import org.bson.Document;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
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

    @Override
    public Mono<StudentDocument> partialUpdateStudent(String id, Map<String, Object> updates) {
        Query query = new Query();

        Criteria idCriteria = Criteria.where("_id").is(id);
        query.addCriteria(idCriteria);

        Update update = new Update();
        updates.forEach(update::set);

        // * Return the latest version of the student with returnNew = true
        return reactiveMongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), StudentDocument.class);
    }

    @Override
    public Flux<StudentProjectionDto> findProjectedStudents() {
        Query query = new Query();

        query.fields()
                .include("name")
                .include("age")
                .exclude("_id");

        // * The second parameter specifies the target type you want the query result to be mapped to.
        return reactiveMongoTemplate.find(
                query,
                StudentProjectionDto.class,
                "students"
        );
    }

    // * Aggregation: List Operations to perform in the data
    // * Pipeline de transformación de datos.
    @Override
    public Flux<SkillCountDto> countStudentsBySkill() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("skills"),

                Aggregation.group("skills")
                        .count()
                        .as("count"),

                // * MongoDb interpreta la clave del groupBY como _id
                Aggregation.project()
                        .and("_id").as("skill")
                        .and("count").as("total")
                        .andExclude("_id")
        );

        return reactiveMongoTemplate.aggregate(
                aggregation,
                StudentDocument.class,
                SkillCountDto.class
        );
    }
}
