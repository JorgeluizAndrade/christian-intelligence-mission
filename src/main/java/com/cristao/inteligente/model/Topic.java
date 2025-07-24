package com.cristao.inteligente.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "topic_nome", nullable = false)
    private String nome;


    @Column(name = "topic_desc", nullable = false)
    private String descTopic;

    @ManyToOne
    @JoinColumn(name = "topico_pai_id")
    private Topic topicoPai;


    @OneToMany(mappedBy = "topicoPai", cascade = CascadeType.ALL)
    private List<Topic> filhos = new ArrayList<>();


    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Livro> livros = new ArrayList<>();

}
