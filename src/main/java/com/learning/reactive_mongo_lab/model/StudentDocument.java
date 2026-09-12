package com.learning.reactive_mongo_lab.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "students")
@Builder
@Getter
@Setter
public class StudentDocument {
    @Id
    private String id;
    private String name;
    private Integer age;
    private boolean active;
    private List<String> skills;

    @Override
    public String toString() {
        return "StudentDocument{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", active=" + active +
                ", skills=" + skills +
                '}';
    }
}
