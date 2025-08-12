package com.cristao.inteligente.services;

import com.cristao.inteligente.model.Usuario;

public interface ITokenService {
    public String generateToken(Usuario usuario);
    public String validateToken(String token);
}
