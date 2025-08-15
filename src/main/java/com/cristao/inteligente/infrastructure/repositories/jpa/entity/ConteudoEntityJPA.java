package com.cristao.inteligente.infrastructure.repositories.jpa.entity;

import com.cristao.inteligente.domain.valueobject.TipoConteudo;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "conteudo")
@Data
public class ConteudoEntityJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conteudo", nullable = false)
    private TipoConteudo tipo;


    @ManyToOne
    @JoinColumn(name = "topico_id")
    private TopicEntityJPA topico;
}
