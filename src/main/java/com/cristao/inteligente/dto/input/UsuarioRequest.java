package com.cristao.inteligente.dto.input;

import com.cristao.inteligente.model.Role;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioRequest {
    private Long id;
    private String nome;
    private String email;
    private String password;
}
