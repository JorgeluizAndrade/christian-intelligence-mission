package com.cristao.inteligente.infrastructure.repositories.jpa;

import com.cristao.inteligente.infrastructure.repositories.jpa.entity.UsuarioEntityJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioEntityJPA, Long> {
    UsuarioEntityJPA findByEmail(String email);
}

