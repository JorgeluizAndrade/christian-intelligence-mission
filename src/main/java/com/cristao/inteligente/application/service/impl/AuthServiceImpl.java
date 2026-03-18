package com.cristao.inteligente.application.service.impl;

import com.cristao.inteligente.application.service.IAuthService;
import com.cristao.inteligente.application.service.ITokenService;
import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.domain.valueobject.Role;
import com.cristao.inteligente.infrastructure.config.JwtProperties;
import com.cristao.inteligente.shared.dto.LoginRequest;
import com.cristao.inteligente.shared.dto.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private static final String ACCESS_TOKEN_COOKIE = "access_token";
    private static final String REFRESH_TOKEN_COOKIE = "refresh_token";

    private final AuthenticationManager authenticationManager;
    private final ITokenService tokenService;
    private final JwtProperties jwtProperties;

    @Override
    public LoginResponse login(LoginRequest req, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String email = auth.getName();
        Role role = resolveRole(auth);

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setRole(role);

        String accessToken = tokenService.generateToken(usuario);
        String refreshToken = tokenService.generateRefreshToken(email, role.name());

        addCookie(response, ACCESS_TOKEN_COOKIE, accessToken, jwtProperties.getAccessTokenExpirationMs());
        addCookie(response, REFRESH_TOKEN_COOKIE, refreshToken, jwtProperties.getRefreshTokenExpirationMs());

        return new LoginResponse("Login realizado com sucesso.");
    }

    @Override
    public LoginResponse refreshAccessToken(String refreshToken, HttpServletResponse response) {
        if (!tokenService.isTokenValid(refreshToken)) {
            throw new RuntimeException("Refresh token inválido ou expirado.");
        }

        String email = tokenService.extractEmail(refreshToken);

        Role role = Role.valueOf(tokenService.extractRole(refreshToken));

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setRole(role);

        String newAccessToken = tokenService.generateToken(usuario);
        String newRefreshToken = tokenService.generateRefreshToken(email, role.name());

        addCookie(response, ACCESS_TOKEN_COOKIE, newAccessToken, jwtProperties.getAccessTokenExpirationMs());
        addCookie(response, REFRESH_TOKEN_COOKIE, newRefreshToken, jwtProperties.getRefreshTokenExpirationMs());

        return new LoginResponse("Token renovado com sucesso.");
    }

    @Override
    public void logout(HttpServletResponse response) {
        addCookie(response, ACCESS_TOKEN_COOKIE, "", 0);
        addCookie(response, REFRESH_TOKEN_COOKIE, "", 0);
    }

    private Role resolveRole(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(Role::valueOf)
                .findFirst()
                .orElse(Role.ROLE_COLABORADOR);
    }

    private void addCookie(HttpServletResponse response, String name, String value, long maxAgeInMs) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(jwtProperties.isCookieSecure())
                .sameSite(jwtProperties.getCookieSameSite())
                .path("/")
                .maxAge(Duration.ofMillis(maxAgeInMs))
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
