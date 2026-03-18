package com.cristao.inteligente.application.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.cristao.inteligente.application.service.ITokenService;
import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.infrastructure.config.JwtProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements ITokenService {

    private static final String ISSUER = "cristo-god";
    private static final Set<String> ALLOWED_ALGORITHMS = Set.of("HS256", "RS256");

    @Value("${api.security.token.secret:}")
    private String legacySecret;

    private final JwtProperties jwtProperties;

    private Algorithm activeAlgorithm;

    @PostConstruct
    public void init() {
        String configuredAlg = jwtProperties.getAlgorithm().toUpperCase();

        if (!ALLOWED_ALGORITHMS.contains(configuredAlg)) {
            throw new IllegalStateException("Algoritmo JWT não permitido: " + configuredAlg + ". Permitidos: " + ALLOWED_ALGORITHMS);
        }

        if ("RS256".equals(configuredAlg)) {
            throw new IllegalStateException("RS256 está na whitelist, mas requer configuração de chaves pública/privada.");
        }

        String secret = resolveSecret();
        this.activeAlgorithm = Algorithm.HMAC256(secret);
    }

    @Override
    public String generateToken(Usuario usuario) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getEmail())
                    .withClaim("role", String.valueOf(usuario.getRole()))
                    .withClaim("type", "access")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpirationMs()))
                    .sign(activeAlgorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar access token.", exception);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            String tokenAlg = JWT.decode(token).getAlgorithm();
            if (!ALLOWED_ALGORITHMS.contains(tokenAlg)) {
                return false;
            }

            JWT.require(activeAlgorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);

            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public String generateRefreshToken(String email, String role) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(email)
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("role", role)
                    .withClaim("type", "refresh")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpirationMs()))
                    .sign(activeAlgorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar refresh token.", exception);
        }
    }

    @Override
    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    @Override
    public String extractRole(String token) {
        return parseClaims(token).getClaim("role").asString();
    }

    private DecodedJWT parseClaims(String token) {
        JWTVerifier verifier = JWT.require(activeAlgorithm)
                .withIssuer(ISSUER)
                .build();

        return verifier.verify(token);
    }

    private String resolveSecret() {
        if (jwtProperties.getSecret() != null && !jwtProperties.getSecret().isBlank()) {
            return jwtProperties.getSecret();
        }
        if (legacySecret != null && !legacySecret.isBlank()) {
            return legacySecret;
        }
        throw new IllegalStateException("Secret JWT não configurado. Defina security.jwt.secret ou api.security.token.secret.");
    }
}
