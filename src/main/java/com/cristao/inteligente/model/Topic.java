package com.cristao.inteligente.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic_autor", nullable = false, insertable = true)
    private String autor;

    @Column(name = "topic_nome", nullable = false)
    private String nome;

    @Column(name = "topic_desc", nullable = false)
    private String descTopic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_pai_id")
    private Topic topicoPai; // (auto-relacionamento) Muitos topics podem ter um único pai. Nenhum topic filho pode ter 2 pais.

    @OneToMany(mappedBy = "topicoPai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> filhos = new ArrayList<>(); // muitos filhos pertencem a um pai. um topic pai pode ter varios filhos.

    @Enumerated(EnumType.STRING)
    private TopicEnum tipo;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conteudo> conteudos = new ArrayList<>();

}
