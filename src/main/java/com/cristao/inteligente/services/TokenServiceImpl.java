package com.cristao.inteligente.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cristao.inteligente.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenServiceImpl implements ITokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create().withIssuer("cristo-god")
                    .withSubject(String.valueOf(usuario.getId()))
                    .withSubject(usuario.getNome())
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }


    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm).withIssuer("cristo-god").build().verify(token).getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("ERROR valid token: ", e);
        }

    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
