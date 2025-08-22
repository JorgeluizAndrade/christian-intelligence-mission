package com.cristao.inteligente.application.usecase.impl;


import com.cristao.inteligente.application.usecase.ICreateConteudoUseCase;
import com.cristao.inteligente.domain.entity.Conteudo;
import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.domain.repository.ConteudoRepository;
import com.cristao.inteligente.domain.repository.TopicRepository;
import com.cristao.inteligente.shared.dto.ConteudoDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CreateConteudoUseCase  implements ICreateConteudoUseCase {

    private ConteudoRepository conteudoRepository;

    private TopicRepository topicRepository;

    public CreateConteudoUseCase(ConteudoRepository conteudoRepository, TopicRepository topicRepository){
        this.conteudoRepository = conteudoRepository;
        this.topicRepository = topicRepository;
    }


    @Override
    public Conteudo createConteudo(Topic topic, ConteudoDTO dto) {
        Conteudo conteudo = new Conteudo(dto.getTitulo(), dto.getDescricao(),
                dto.getAutor(), dto.getLink(), dto.getTipo());

        return conteudoRepository.create(conteudo);
    }
}
