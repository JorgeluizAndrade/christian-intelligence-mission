package com.cristao.inteligente.application.service.impl;

import com.cristao.inteligente.application.service.IAuthService;
import com.cristao.inteligente.application.service.ITokenService;
import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.shared.dto.LoginRequest;
import com.cristao.inteligente.shared.dto.LoginResponse;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.UsuarioEntityJPA;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager authenticationManager;

    private final ITokenService tokenService;


    AuthServiceImpl(AuthenticationManager authenticationManager, ITokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    @Override
    public LoginResponse login(LoginRequest req) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return new LoginResponse(token);
    }
}
