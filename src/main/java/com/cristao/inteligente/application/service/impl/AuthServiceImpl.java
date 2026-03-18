package com.cristao.inteligente.application.service.impl;

import com.cristao.inteligente.application.service.IAuthService;
import com.cristao.inteligente.application.service.ITokenService;
import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.shared.dto.LoginRequest;
import com.cristao.inteligente.shared.dto.LoginResponse;

import jakarta.servlet.http.HttpServletResponse;

import com.cristao.inteligente.infrastructure.repositories.UsuarioJPARepository;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.UsuarioEntityJPA;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager authenticationManager;

    private final ITokenService tokenService;
    
    private final UsuarioJPARepository usuarioRepository;
    
    private final PasswordEncoder passwordEncoder;



    AuthServiceImpl(AuthenticationManager authenticationManager, ITokenService tokenService, UsuarioJPARepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public LoginResponse login(LoginRequest req, HttpServletResponse response) {
        
    	 var usuario = usuarioRepository.findByEmail(req.getEmail())
    	            .orElseThrow(() -> new RuntimeException("Credenciais inválidas."));
    	
    	UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        
        if (!passwordEncoder.matches(req.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciais inválidas.");
        }
        
        
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return new LoginResponse(token);
    }
}
