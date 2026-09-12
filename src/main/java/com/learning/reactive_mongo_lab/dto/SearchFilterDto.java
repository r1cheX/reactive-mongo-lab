package com.learning.reactive_mongo_lab.dto;

import lombok.Builder;

@Builder
public record SearchFilterDto(Boolean active, Integer olderThan, String skill) {
}

