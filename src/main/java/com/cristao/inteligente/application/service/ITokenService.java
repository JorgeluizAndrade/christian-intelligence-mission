package com.cristao.inteligente.application.service;

import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.UsuarioEntityJPA;

public interface ITokenService {
    public String generateToken(UsuarioEntityJPA usuario);

    public String validateToken(String token);
}
