package com.cristao.inteligente.domain.entity;

import com.cristao.inteligente.domain.valueobject.TipoConteudo;

public class Conteudo {

    private Long id;
    private String titulo;
    private String descricao;
    private String autor;
    private String link;
    private TipoConteudo tipo;
    private Topic topico;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public TipoConteudo getTipo() { return tipo; }
    public void setTipo(TipoConteudo tipo) { this.tipo = tipo; }

    public Topic getTopico() { return topico; }
    public void setTopico(Topic topico) { this.topico = topico; }
}

