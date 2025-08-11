package com.cristao.inteligente.services;


import com.cristao.inteligente.dto.input.ConteudoDTO;
import com.cristao.inteligente.dto.input.TopicDTO;
import com.cristao.inteligente.model.Conteudo;
import com.cristao.inteligente.model.Topic;
import com.cristao.inteligente.model.TopicEnum;
import com.cristao.inteligente.repositories.ConteudoRepository;
import com.cristao.inteligente.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService implements ITopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ConteudoRepository conteudoRepository;

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
}



