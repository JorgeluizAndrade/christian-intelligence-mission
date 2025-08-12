package com.cristao.inteligente.services;

import com.cristao.inteligente.dto.input.LoginRequest;
import com.cristao.inteligente.dto.input.LoginResponse;
import com.cristao.inteligente.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements IAuthService{
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
