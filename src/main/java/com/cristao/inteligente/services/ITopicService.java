package com.cristao.inteligente.services;

import com.cristao.inteligente.dto.input.TopicDTO;
import com.cristao.inteligente.model.Topic;

import java.util.List;

public interface ITopicService {

    public Topic findTopicOrElseThrow(Long id);

    public List<Topic> findAllTopics();

    public Topic createTopic(TopicDTO input);
}