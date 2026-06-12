package com.rafael.agendadortarefas.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDebugConfig {

    @Value("${spring.data.mongodb.uri:URI_NAO_ENCONTRADA}")
    private String mongoUri;

    @Value("${spring.profiles.active:nenhum}")
    private String activeProfiles;

    @PostConstruct
    public void debugMongoConfig() {
        System.out.println("========================================");
        System.out.println(">>> PERFIL ATIVO: " + activeProfiles);
        System.out.println(">>> MONGO URI SENDO USADA: " + mongoUri);
        System.out.println("========================================");
    }
}