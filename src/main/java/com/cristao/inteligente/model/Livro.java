package com.cristao.inteligente.model;


import jakarta.persistence.*;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_livro", nullable = false)
    private String nomeLivro;

    @Column(name = "desc_livro")
    private String descLivro;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topic topico;

}
