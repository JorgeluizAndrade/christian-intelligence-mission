package com.cristao.inteligente.shared.dto;


import com.cristao.inteligente.domain.valueobject.TipoConteudo;
import lombok.Data;

@Data
public class ConteudoDTO {

    private String titulo;

    private String descricao;

    private String autor;

    private String link;

    private TipoConteudo tipo;
}
