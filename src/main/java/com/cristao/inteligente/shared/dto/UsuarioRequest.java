package com.cristao.inteligente.shared.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioRequest {
    private String nome;
    private String email;
    private String password;
}
