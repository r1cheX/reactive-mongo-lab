package com.learning.reactive_mongo_lab.dto;

import lombok.Builder;

@Builder
public record StudentProjectionDto(String name, Integer age) {
}
