package com.cristao.inteligente.infrastructure.repositories;

import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.domain.repository.UsuarioRepository;
import com.cristao.inteligente.infrastructure.mapper.UsuarioMapper;
import com.cristao.inteligente.infrastructure.repositories.jpa.SpringDataUsuarioRepository;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.UsuarioEntityJPA;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioJPARepository implements UsuarioRepository {

    private final SpringDataUsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioJPARepository(SpringDataUsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Usuario save(Usuario usuario) {

        UsuarioEntityJPA entity = usuarioMapper.toEntity(usuario);
        entity = usuarioRepository.save(entity);
        // Converter de volta para domínio e retornar
        return usuarioMapper.toDomain(entity);

    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDomain);
    }

    @Override
    public UsuarioEntityJPA findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }





}