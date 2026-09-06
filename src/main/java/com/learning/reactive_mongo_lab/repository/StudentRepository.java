package com.learning.reactive_mongo_lab.repository;


import com.learning.reactive_mongo_lab.model.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
	Flux<Student> findByActiveTrue();

	Flux<Student> findByAgeGreaterThan(int age);

	Flux<Student> findBySkillsContaining(String skill);

	Flux<Student> findBySkillsIn(Collection<List<String>> skills);

	Flux<Student> findByActiveAndAgeGreaterThanAndSkillsContains(Boolean active, Integer age, String skill);
}
