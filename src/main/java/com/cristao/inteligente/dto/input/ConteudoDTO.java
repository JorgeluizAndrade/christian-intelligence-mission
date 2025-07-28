package com.cristao.inteligente.dto.input;


import com.cristao.inteligente.model.TipoConteudo;
import lombok.Data;

@Data
public class ConteudoDTO {

    private String titulo;

    private String descricao;

    private String autor;

    private String link;

    private TipoConteudo tipo;
}
