package com.cristao.inteligente.application.service;


import com.cristao.inteligente.domain.entity.Usuario;


public interface ITokenService {
    public String generateToken(Usuario usuario);

    public boolean isTokenValid(String token);
    
    public String generateRefreshToken(String email);
       
    public String extractEmail(String token);
    
    public String extractRole(String token);

  

}
