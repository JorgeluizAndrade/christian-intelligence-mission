package com.cristao.inteligente.dto.input;

import com.cristao.inteligente.model.Role;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Role role
) {
}
