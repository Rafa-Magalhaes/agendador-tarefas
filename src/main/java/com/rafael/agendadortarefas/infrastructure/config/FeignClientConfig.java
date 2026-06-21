package com.rafael.agendadortarefas.infrastructure.config;

import com.rafael.agendadortarefas.infrastructure.security.JwtUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String token = jwtUtil.generateServiceToken();
                template.header("Authorization", "Bearer " + token);
            }
        };
    }
}