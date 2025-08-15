package com.cristao.inteligente.application.service.impl;


import com.cristao.inteligente.application.service.ITopicService;
import com.cristao.inteligente.domain.entity.Conteudo;
import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.domain.repository.ConteudoRepository;
import com.cristao.inteligente.domain.repository.TopicRepository;
import com.cristao.inteligente.domain.valueobject.TopicEnum;
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

    private static List<Topic> getTopicFilho(Topic topic, TopicDTO dto) {
        List<Topic> filhos = new ArrayList<>();

        if (dto.getFilhos() != null) {
            for (TopicDTO filhoInput : dto.getFilhos()) {
                Topic filho = new Topic();
                filho.setNome(filhoInput.getNome());
                filho.setDescTopic(filhoInput.getDescTopic());
                filho.setTopicoPai(topic); // importante: pai é o topic atual
                topic.setTipo(TopicEnum.TOPIC_FILHO);
                filhos.add(filho);
            }
        }
        return filhos;
    }

    private static List<Conteudo> getConteudos(Topic topic, TopicDTO dto) {
        List<Conteudo> conteudos = new ArrayList<>();

        if (dto.getConteudos() != null) {
            for (ConteudoDTO conteudoInput : dto.getConteudos()) {
                Conteudo conteudo = new Conteudo();
                conteudo.setTitulo(conteudoInput.getTitulo());
                conteudo.setDescricao(conteudoInput.getDescricao());
                conteudo.setAutor(conteudoInput.getAutor());
                conteudo.setLink(conteudoInput.getLink());
                conteudo.setTipo(conteudoInput.getTipo());
                conteudo.setTopico(topic);
                conteudos.add(conteudo);
            }
        }

        topic.setConteudos(conteudos);
        return conteudos;
    }

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

        Topic topic = new Topic(
                input.getAutor(),
                input.getNome(),
                input.getDescTopic()
        );

        typeTopicAndTopicPai(topic, input.getTopicpai());

        List<Conteudo> conteudos = getConteudos(topic, input);

        topic.setConteudos(conteudos);
        conteudos.forEach(c -> c.setTopico(topic));

        List<Topic> filhos = getTopicFilho(topic, input);

        topic.setFilhos(filhos);


        return topicRepository.save(topic);
    }


    ///  tenho q fazer mappers
    /// qual a melhor abordagem para o mapper?
    /// tem 2 "problemas":
    /// 1 -> auto-relacionamento com topicPai
    /// 2 -> auto-relacionamento com filho
    /// Isso faz com que eu n possa "setar" "getar" de maneiera normal
    /// aparentemente tenho que fazer uma função recursiva para os 2...
    /// algo do tipo:
    /// if(pai != null) {topic t = new topic;
    /// topt.setpai(entidy.pai.id)}
    ///  algo desse tipo... bem estranho


}



