package com.cristao.inteligente.services;


import com.cristao.inteligente.dto.input.TopicDTO;
import com.cristao.inteligente.model.Topic;
import com.cristao.inteligente.repositories.LivroRepository;
import com.cristao.inteligente.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LivroRepository livroRepository;


    public Topic createTopic(TopicDTO input) {
        return null;
    }
}
