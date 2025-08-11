package com.cristao.inteligente.graphql;


import com.cristao.inteligente.dto.input.TopicDTO;
import com.cristao.inteligente.model.Topic;
import com.cristao.inteligente.services.ITopicService;
import com.cristao.inteligente.services.TopicService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TopicGraphQL {

    private final ITopicService topicService;

    public TopicGraphQL(TopicService topicService) {
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

    @MutationMapping
    public Topic createTopic(@Argument TopicDTO topic) {
        return topicService.createTopic(topic);
    }

}
