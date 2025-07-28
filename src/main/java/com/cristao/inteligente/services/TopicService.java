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

    public Topic findTopicOrElseThrow(Long id) {
        return this.topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic com: " + id + " não encontrado"));
    }

    private Livro findLivroOrElseThrow(Long id) {
        return this.livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro com: " + id + "  não encontrado"));
    }


    public List<Topic> findAllTopics() {
        return topicRepository.findAll();
    }

    public Topic createTopic(TopicDTO input) {
        Topic topic = new Topic();

        TopicEnum filhoenum = TopicEnum.TOPIC_FILHO;
        TopicEnum pai = TopicEnum.TOPIC_PAI;

        topic.setNome(input.getNome());
        topic.setDescTopic(input.getDescTopic());

        if (input.getTopicpai() != null) {
            var topicPai = findTopicOrElseThrow(input.getTopicpai());
            topic.setTopicoPai(topicPai);
            topic.setTipo(filhoenum);
        }

        if(topic.getTopicoPai() == null){
            topic.setTipo(pai);
        }

        List<Livro> livros = new ArrayList<>();


        if (input.getLivros() != null) {
            for (LivroDTO livroInput : input.getLivros()) {
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

        List<Topic> filhos = new ArrayList<>();

        if (input.getFilhos() != null) {
            for (TopicDTO filhoInput : input.getFilhos()) {
                Topic filho = new Topic();
                filho.setNome(filhoInput.getNome());
                filho.setDescTopic(filhoInput.getDescTopic());
                filho.setTopicoPai(topic); // importante: pai é o topic atual
                topic.setTipo(filhoenum);
                filhos.add(filho);
            }
        }
        topic.setFilhos(filhos);

        return topicRepository.save(topic);
    }
}



