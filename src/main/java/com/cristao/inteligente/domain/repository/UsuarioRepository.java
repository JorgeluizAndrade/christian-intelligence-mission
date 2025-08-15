package com.cristao.inteligente.domain.repository;

import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.UsuarioEntityJPA;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    UsuarioEntityJPA findByEmail(String email);
}
