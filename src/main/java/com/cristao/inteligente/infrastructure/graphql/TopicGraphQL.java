package com.cristao.inteligente.infrastructure.graphql;


import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.shared.dto.TopicDTO;
import com.cristao.inteligente.application.service.ITopicService;
import com.cristao.inteligente.application.service.impl.TopicService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TopicGraphQL {

    private final ITopicService topicService;

    public TopicGraphQL(ITopicService topicService) {
        this.topicService = topicService;
    }

    @QueryMapping
    public List<Topic> findAllTopics() {
        return topicService.findAllTopics();
    }

    @QueryMapping
    public Topic findTopicById(@Argument Long id) {
        return topicService.findTopicOrElseThrow(id);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('COLABORADOR')")
    @MutationMapping
    public Topic createTopic(@Argument TopicDTO topic) {
        return topicService.createTopic(topic);
    }

}
