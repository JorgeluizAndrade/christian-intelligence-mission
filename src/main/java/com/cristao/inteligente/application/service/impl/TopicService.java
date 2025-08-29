package com.cristao.inteligente.application.service.impl;


import com.cristao.inteligente.application.service.ITopicService;
import com.cristao.inteligente.application.usecase.ICreateConteudoUseCase;
import com.cristao.inteligente.domain.entity.Conteudo;
import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.domain.repository.TopicRepository;
import com.cristao.inteligente.domain.valueobject.TopicEnum;
import com.cristao.inteligente.infrastructure.mapper.TopicMapper;
import com.cristao.inteligente.shared.dto.ConteudoDTO;
import com.cristao.inteligente.shared.dto.TopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService implements ITopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ICreateConteudoUseCase createConteudoUseCase;

    @Autowired
    private TopicMapper topicMapper;

    public Topic findTopicOrElseThrow(Long id) {
        return this.topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic com: " + id + " não encontrado"));
    }

    private void typeTopicAndTopicPai(Topic topic, Long id) {
        TopicEnum filho = TopicEnum.TOPIC_FILHO;
        TopicEnum pai = TopicEnum.TOPIC_PAI;

        if (id != null) {
            var topicPai = findTopicOrElseThrow(id);
            topic.setTopicoPai(topicPai);
            topic.setTipo(filho);
        } else {
            topic.setTipo(pai);
        }
    }

    public List<Topic> findAllTopics() {
        return topicRepository.findAll();
    }

    public Topic createTopic(TopicDTO input) {
        Topic topic = new Topic(input.getAutor(), input.getNome(), input.getDescTopic());



        if (input.getTopicpai() != null) {
            Topic pai =  findTopicOrElseThrow(input.getTopicpai());

            topic.setTopicoPai(pai);
            topic.setTipo(TopicEnum.TOPIC_FILHO);
        } else {
            topic.setTipo(TopicEnum.TOPIC_PAI);
        }

        if (input.getConteudos() != null) {
            topic.setConteudos(mapConteudos(input.getConteudos(), topic));
        }

        if (input.getFilhos() != null) {
            List<Topic> filhos = new ArrayList<>();
            for (TopicDTO filhoDTO : input.getFilhos()) {
                Topic filho = new Topic(filhoDTO.getAutor(), filhoDTO.getNome(), filhoDTO.getDescTopic());
                filho.setTopicoPai(topic);
                filho.setTipo(TopicEnum.TOPIC_FILHO);

                if (filhoDTO.getConteudos() != null) {
                    List<Conteudo> conteudosFilho = new ArrayList<>();
                    for (ConteudoDTO dto : filhoDTO.getConteudos()) {
                        Conteudo c = new Conteudo(dto.getTitulo(), dto.getDescricao(), dto.getAutor(), dto.getLink(), dto.getTipo());
                        c.setTopico(filho);  // associar ao filho
                        conteudosFilho.add(c);
                    }
                    filho.setConteudos(conteudosFilho);
                }

                filhos.add(filho);
            }
            topic.setFilhos(filhos);
        }

        return topicRepository.save(topic);
    }

    private List<Conteudo> mapConteudos(List<ConteudoDTO> conteudoDTOs, Topic topic) {
        List<Conteudo> conteudos = new ArrayList<>();
        for (ConteudoDTO dto : conteudoDTOs) {
            Conteudo c = new Conteudo(dto.getTitulo(), dto.getDescricao(), dto.getAutor(), dto.getLink(), dto.getTipo());
            c.setTopico(topic);
            conteudos.add(c);
        }
        return conteudos;
    }
}