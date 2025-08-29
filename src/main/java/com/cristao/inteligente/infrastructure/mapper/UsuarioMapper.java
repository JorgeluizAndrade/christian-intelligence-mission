package com.cristao.inteligente.infrastructure.mapper;


import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.UsuarioEntityJPA;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioEntityJPA toEntity(Usuario usuario) {
        UsuarioEntityJPA entity = new UsuarioEntityJPA();
        entity.setId(usuario.getId());
        entity.setEmail(usuario.getEmail());
        entity.setNome(usuario.getNome());
        entity.setPassword(usuario.getPassword());
        entity.setRole(usuario.getRole());
        return entity;
    }

    public Usuario toDomain(UsuarioEntityJPA entity) {
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNome(entity.getNome());
        usuario.setEmail(entity.getEmail());
        usuario.setPassword(entity.getPassword());
        usuario.setRole(entity.getRole());
        return usuario;
    }

}
