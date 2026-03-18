package com.cristao.inteligente.application.service;

import com.cristao.inteligente.domain.entity.Usuario;

public interface ITokenService {
    String generateToken(Usuario usuario);

    boolean isTokenValid(String token);

    String generateRefreshToken(String email, String role);

    String extractEmail(String token);

    String extractRole(String token);
}
