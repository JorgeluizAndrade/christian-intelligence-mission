package com.cristao.inteligente.application.service;

import com.cristao.inteligente.domain.entity.Usuario;

public interface ITokenService {
    public String generateToken(Usuario usuario);

    public String validateToken(String token);
}
