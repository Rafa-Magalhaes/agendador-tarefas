package com.rafael.agendadortarefas.infrastructure.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Bean
    @Primary
    public MongoDatabaseFactory mongoDatabaseFactory() {
        // Força o uso do database db_agendador
        return new SimpleMongoClientDatabaseFactory(
                MongoClients.create("mongodb://localhost:27017/db_agendador"),
                "db_agendador"
        );
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDatabaseFactory());
    }
}