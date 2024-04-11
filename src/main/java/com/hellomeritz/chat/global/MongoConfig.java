package com.hellomeritz.chat.global;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    private static final String MONGODB_URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "meritz";

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create(MONGODB_URL);
    }

    @Bean
    MongoOperations mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, DATABASE_NAME);
    }
}
