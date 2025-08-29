package com.cristao.inteligente.application.service;

import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.shared.dto.TopicDTO;

import java.util.List;

public interface ITopicService {

    public Topic findTopicOrElseThrow(Long id);

    public List<Topic> findAllTopics();

    public Topic createTopic(TopicDTO input);
}