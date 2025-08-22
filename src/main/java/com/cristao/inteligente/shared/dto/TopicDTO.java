package com.cristao.inteligente.shared.dto;

import lombok.Data;

import java.util.List;


@Data
public class TopicDTO {
    private String autor;
    private String nome;
    private String descTopic;
    private Long topicpai;
    private List<TopicDTO> filhos;
    private List<ConteudoDTO> conteudos;

}
