package com.cristao.inteligente.services;


import com.cristao.inteligente.dto.input.LivroDTO;
import com.cristao.inteligente.dto.input.TopicDTO;
import com.cristao.inteligente.model.Livro;
import com.cristao.inteligente.model.Topic;
import com.cristao.inteligente.model.TopicEnum;
import com.cristao.inteligente.repositories.LivroRepository;
import com.cristao.inteligente.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LivroRepository livroRepository;

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

    private static List<Livro> getLivros(Topic topic, TopicDTO dto) {
        List<Livro> livros = new ArrayList<>();

        if (dto.getLivros() != null) {
            for (LivroDTO livroInput : dto.getLivros()) {
                Livro createLivro = new Livro();
                createLivro.setNomeLivro(livroInput.getNomeLivro());
                createLivro.setDescLivro(livroInput.getDescLivro());
                createLivro.setAutor(livroInput.getAutor());
                createLivro.setLink(livroInput.getLink());
                createLivro.setTopico(topic);
                livros.add(createLivro);
            }
        }
        topic.setLivros(livros);

        return livros;

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
        Topic topic = new Topic();

        topic.setNome(input.getNome());
        topic.setDescTopic(input.getDescTopic());

        typeTopicAndTopicPai(topic, input.getTopicpai());

        List<Livro> livros = getLivros(topic, input);

        topic.setLivros(livros);

        List<Topic> filhos = getTopicFilho(topic, input);

        topic.setFilhos(filhos);

        return topicRepository.save(topic);
    }
}



