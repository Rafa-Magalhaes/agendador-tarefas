package com.rafael.agendadortarefas.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String path = request.getRequestURI();
        log.info(">>> [JwtRequestFilter] Path recebido: [{}]", path);

        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn(">>> [JwtRequestFilter] Header Authorization ausente ou inválido.");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token ausente");
            return;
        }

        final String token = authHeader.substring(7);

        try {
            if (!jwtUtil.isValidServiceToken(token)) {
                log.warn(">>> [JwtRequestFilter] Token de serviço inválido (assinatura ou tipo incorreto).");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token de serviço inválido");
                return;
            }

            if (jwtUtil.isTokenExpired(token)) {
                log.warn(">>> [JwtRequestFilter] Token expirado.");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token expirado");
                return;
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken("service", null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(">>> [JwtRequestFilter] Token SERVICE validado com sucesso.");

        } catch (Exception e) {
            log.error(">>> [JwtRequestFilter] Erro ao validar token: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token inválido");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/actuator")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }
}