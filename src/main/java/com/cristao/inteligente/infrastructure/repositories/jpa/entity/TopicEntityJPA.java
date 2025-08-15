package com.cristao.inteligente.infrastructure.repositories.jpa.entity;


import com.cristao.inteligente.domain.valueobject.TopicEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "topic")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TopicEntityJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic_autor", nullable = false)
    private String autor;

    @Column(name = "topic_nome", nullable = false)
    private String nome;

    @Column(name = "topic_desc", nullable = false)
    private String descTopic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_pai_id")
    @JsonIgnore
    private TopicEntityJPA topicoPai; // (auto-relacionamento) Muitos topics podem ter um único pai. Nenhum topic filho pode ter 2 pais.

    @OneToMany(mappedBy = "topicoPai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TopicEntityJPA> filhos = new ArrayList<>(); // muitos filhos pertencem a um pai. um topic pai pode ter varios filhos.

    @Enumerated(EnumType.STRING)
    private TopicEnum tipo;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConteudoEntityJPA> conteudos = new ArrayList<>();

    public TopicEntityJPA() {
    }

    public TopicEntityJPA(String autor, String nome, String descTopic) {
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

}
