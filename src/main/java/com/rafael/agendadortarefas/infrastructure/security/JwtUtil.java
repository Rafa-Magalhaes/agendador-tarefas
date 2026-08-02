package com.rafael.agendadortarefas.infrastructure.security;

import com.rafael.agendadortarefas.infrastructure.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
=======
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
>>>>>>> origin/develop
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@SuppressWarnings("deprecation")
@Component
<<<<<<< HEAD
=======
@Slf4j
>>>>>>> origin/develop
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generateServiceToken() {
        long expiration = jwtProperties.getServiceExpirationMs();

        return Jwts.builder()
                .subject("agendador-tarefas")
                .claim("serviceName", "agendador-tarefas")
                .claim("type", "SERVICE")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isValidServiceToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
<<<<<<< HEAD
            String type = claims.get("type", String.class);
            return "SERVICE".equals(type);
        } catch (Exception e) {
=======
            String type = claims.get("tokentype", String.class);

            log.info(">>> [Agendador] Token recebido. Claim 'tokentype' = {}", type);

            boolean valido = "SERVICE".equals(type);
            log.info(">>> [Agendador] Token válido? {}", valido);

            return valido;
        } catch (Exception e) {
            log.error(">>> [Agendador] Erro ao validar Service Token: {}", e.getMessage());
>>>>>>> origin/develop
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

<<<<<<< HEAD
    public String extractTokenType(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("type", String.class);
        } catch (Exception e) {
            return null;
        }
    }

=======
>>>>>>> origin/develop
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
<<<<<<< HEAD
=======
    }

    @PostConstruct
    public void init() {
        String secret = jwtProperties.getSecret();

        if (secret != null && secret.length() >= 10) {
            log.info(">>> [Agendador] JWT Secret sendo usado (primeiros 10 chars): {}",
                    secret.substring(0, 10) + "...");
        } else {
            log.warn(">>> [Agendador] JWT Secret está NULO ou muito curto!");
        }
>>>>>>> origin/develop
    }
}