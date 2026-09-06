package com.learning.reactive_mongo_lab.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
public class MongoConfig {
    @Bean
    InitializingBean configureMongoTypeMapper(MappingMongoConverter converter) {
        return () -> converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
}