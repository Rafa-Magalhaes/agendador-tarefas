package com.rafael.agendadortarefas.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${spring.application.name:agendador-tarefas}")
    private String serviceName;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ====================== GERAÇÃO DE TOKEN ======================
    public String generateServiceToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "SERVICE");
        claims.put("serviceName", serviceName);
        claims.put("scope", "internal:read-users");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(serviceName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ====================== EXTRAÇÃO DE DADOS ======================
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()                    // ← Esta linha estava faltando
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractTokenType(String token) {
        return extractAllClaims(token).get("tokenType", String.class);
    }

    // ====================== VALIDAÇÕES ======================
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public boolean isServiceToken(String token) {
        return "SERVICE".equals(extractTokenType(token));
    }
}