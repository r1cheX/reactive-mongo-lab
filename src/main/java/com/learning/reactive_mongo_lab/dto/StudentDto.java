package com.learning.reactive_mongo_lab.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class StudentDto {

    @NotNull
    private String name;

    @Positive
    private Integer age;

    @NotNull
    private boolean active;

    @NotEmpty
    private List<String> skills;
}
