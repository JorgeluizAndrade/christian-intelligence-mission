package com.cristao.inteligente.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "security.jwt")
@Getter
@Setter
public class JwtProperties {

    private List<String> allowedAlgorithms = Arrays.asList("HS256", "RS256");
    private String algorithm = "HS256";
    private String secret;
    private long accessTokenExpirationMs = 900_000L;
    private long refreshTokenExpirationMs = 604_800_000L;
    private boolean cookieSecure = true;
    private String cookieSameSite = "Strict";
}