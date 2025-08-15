package com.cristao.inteligente.infrastructure.security;

import com.cristao.inteligente.domain.repository.UsuarioRepository;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.UsuarioEntityJPA;
import com.cristao.inteligente.infrastructure.repositories.UsuarioJPARepository;
import com.cristao.inteligente.application.service.ITokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class FilterJWT extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ITokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = this.recoverToken(request);

        if (token != null) {
            String email = tokenService.validateToken(token);
            if (email != null) {
                UsuarioEntityJPA user = usuarioRepository.findByEmail(email);
                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new RuntimeException("User not found with email: " + email);
                }
            } else {
                throw new RuntimeException("Token is invalid or expired");
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }

}