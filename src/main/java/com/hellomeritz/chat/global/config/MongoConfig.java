package com.hellomeritz.chat.global.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${mongodb.url}")
    private String MONGODB_URL;

    @Value("${mongodb.database}")
    private String DATABASE_NAME;

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create(MONGODB_URL);
    }

    @Bean
    MongoOperations mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, DATABASE_NAME);
    }
}
