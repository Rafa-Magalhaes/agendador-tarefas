package com.rafael.agendadortarefas.infrastructure.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    @Primary
    public MongoDatabaseFactory mongoDatabaseFactory() {
        // Força o uso do database db_agendador
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), "db_agendador");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory,
                                       MongoMappingContext context,
                                       MongoCustomConversions conversions) {

        MappingMongoConverter converter = new MappingMongoConverter(
                new org.springframework.data.mongodb.core.convert.DefaultDbRefResolver(mongoDatabaseFactory),
                context
        );

        // Remove o campo _class
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.setCustomConversions(conversions);
        converter.afterPropertiesSet();

        return new MongoTemplate(mongoDatabaseFactory, converter);
    }
}