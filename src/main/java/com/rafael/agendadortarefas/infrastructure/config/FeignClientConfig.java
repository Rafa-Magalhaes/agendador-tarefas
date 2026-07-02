package com.rafael.agendadortarefas.infrastructure.config;

import com.rafael.agendadortarefas.infrastructure.security.JwtUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FeignClientConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            try {
                String token = jwtUtil.generateServiceToken();
                template.header("Authorization", "Bearer " + token);
                log.debug(">>> [Feign] Token de serviço adicionado para chamada outgoing");
            } catch (Exception e) {
                log.error(">>> [Feign] Falha ao gerar token de serviço para chamada outgoing", e);
            }
        };
    }
}