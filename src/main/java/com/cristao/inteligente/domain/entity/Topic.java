package com.cristao.inteligente.domain.entity;

import com.cristao.inteligente.domain.valueobject.TopicEnum;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    private Long id;
    private String autor;
    private String nome;
    private String descTopic;
    private Topic topicoPai;
    private List<Topic> filhos = new ArrayList<>();
    private TopicEnum tipo;
    private List<Conteudo> conteudos = new ArrayList<>();

    public Topic() {}

    public Topic(String autor, String nome, String descTopic) {
        if (autor == null || autor.length() < 3) {
            throw new IllegalArgumentException("Nome Autor inválido.");
        }
        if (nome == null || nome.length() < 5) {
            throw new IllegalArgumentException("Nome Topic inválido.");
        }
        if (descTopic == null || descTopic.length() < 20) {
            throw new IllegalArgumentException("Descrição topic inválida.");
        }

        this.autor = autor;
        this.nome = nome;
        this.descTopic = descTopic;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescTopic() { return descTopic; }
    public void setDescTopic(String descTopic) { this.descTopic = descTopic; }

    public Topic getTopicoPai() { return topicoPai; }
    public void setTopicoPai(Topic topicoPai) { this.topicoPai = topicoPai; }

    public List<Topic> getFilhos() { return filhos; }
    public void setFilhos(List<Topic> filhos) { this.filhos = filhos; }

    public TopicEnum getTipo() { return tipo; }
    public void setTipo(TopicEnum tipo) { this.tipo = tipo; }

    public List<Conteudo> getConteudos() { return conteudos; }
    public void setConteudos(List<Conteudo> conteudos) { this.conteudos = conteudos; }
}
