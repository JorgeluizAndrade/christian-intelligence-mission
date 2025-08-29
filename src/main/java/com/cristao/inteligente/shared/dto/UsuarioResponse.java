package com.cristao.inteligente.shared.dto;

import com.cristao.inteligente.domain.valueobject.Role;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Role role
) {
}
