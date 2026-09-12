package com.learning.reactive_mongo_lab.repository;


import com.learning.reactive_mongo_lab.model.StudentDocument;
import com.learning.reactive_mongo_lab.repository.custom.StudentRepositoryCustom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends ReactiveMongoRepository<StudentDocument, String>, StudentRepositoryCustom {
	Flux<StudentDocument> findByActiveTrue();

	Flux<StudentDocument> findByAgeGreaterThan(int age);

	Flux<StudentDocument> findBySkillsContaining(String skill);

	Flux<StudentDocument> findBySkillsIn(Collection<List<String>> skills);
}
