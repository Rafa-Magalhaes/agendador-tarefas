package com.rafael.agendadortarefas.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
<<<<<<< HEAD
@ConfigurationProperties(prefix = "app.jwt")
=======
@ConfigurationProperties(prefix = "jwt")
>>>>>>> origin/develop
public class JwtProperties {

    private String secret;
    private long expirationMs = 3600000;
    private long serviceExpirationMs = 1800000;
}