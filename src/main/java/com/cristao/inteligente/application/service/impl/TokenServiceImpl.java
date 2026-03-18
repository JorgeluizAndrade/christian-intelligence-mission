package com.cristao.inteligente.application.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
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

	
	@Value("${api.security.token.secret}")
    private String secret;
    
    
    private Algorithm activeAlgorithm;
    
    private final JwtProperties jwtProperties;
    
    private static final Set<String> ALLOWED_ALGORITHMS = Set.of("HS256", "RS256");
    
    
    
    
    @PostConstruct
    public void init() {
        String alg = jwtProperties.getAlgorithm().toUpperCase();
        if (!ALLOWED_ALGORITHMS.contains(alg)) {
            throw new IllegalStateException(
                "Algoritmo JWT '" + alg + "' não está na lista branca. Use: " + ALLOWED_ALGORITHMS
            );
        }
        this.activeAlgorithm = Algorithm.HMAC256(secret);
    }
    
    

    @Override
    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
            	    .withIssuer("cristo-god")
            	    .withSubject(usuario.getEmail())
            	    .withClaim("role", String.valueOf(usuario.getRole()))
            	    .sign(activeAlgorithm);
            
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }
    
    
    


    @Override
    public boolean isTokenValid(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            JWT.require(algorithm)
                .withIssuer("cristo-god")
                .build()
                .verify(token);

            return true;

        } catch (JWTVerificationException e) {
            return false;
        }
    }


	@Override
	public String generateRefreshToken(String email) {
		 try {
	            Algorithm algorithm = Algorithm.HMAC256(secret);

	            String token = JWT.create()
	            		.withIssuedAt(new Date())
	                    .withSubject(email)
	                    .withJWTId(UUID.randomUUID().toString())
	                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpirationMs()))
	                    .sign(algorithm);
	            
	            return token;
	        } catch (JWTCreationException exception) {
	            throw new RuntimeException("Error while generating token", exception);
	        }
	}



	private DecodedJWT parseClaims(String token) {
	    Algorithm algorithm = Algorithm.HMAC256(secret);

	    JWTVerifier verifier = JWT.require(algorithm)
	        .build();

	    return verifier.verify(token);
	}



	@Override
	public String extractEmail(String token) {
		return parseClaims(token).getSubject();
	}



	@Override
	public String extractRole(String token) {
		return parseClaims(token).getClaim("role").asString();
	}

	

}
