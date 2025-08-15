package com.cristao.inteligente.shared.dto;

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
