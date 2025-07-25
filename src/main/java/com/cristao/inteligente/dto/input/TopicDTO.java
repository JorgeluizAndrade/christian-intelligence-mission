package com.cristao.inteligente.dto.input;

import lombok.Data;

import java.util.List;


@Data
public class TopicDTO {
private String nome;

private String descTopic;

private Integer topicpai;


private List<TopicDTO> filhos;

private List<LivroDTO> livros;

}
