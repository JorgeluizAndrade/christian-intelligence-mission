package com.cristao.inteligente.infrastructure.repositories;

import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.domain.repository.TopicRepository;
import com.cristao.inteligente.infrastructure.mapper.TopicMapper;
import com.cristao.inteligente.infrastructure.repositories.jpa.SpringDataTopicRepository;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.TopicEntityJPA;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class TopicJPARepository implements TopicRepository {

    private final SpringDataTopicRepository jpa;
    private final TopicMapper topicMapper;


    public TopicJPARepository(SpringDataTopicRepository jpa, TopicMapper topicMapper) {
        this.jpa = jpa;
        this.topicMapper = topicMapper;
    }

    @Override
    public Topic save(Topic topic) {
        TopicEntityJPA entity = topicMapper.toEntity(topic);

        if (entity.getTopicoPai() != null && entity.getTopicoPai().getId() == null) {
            TopicEntityJPA savedPai = jpa.save(entity.getTopicoPai());
            entity.setTopicoPai(savedPai);
        }

        entity.getConteudos().forEach(c -> c.setTopico(entity));

        TopicEntityJPA created = jpa.save(entity);
        return topicMapper.toDomain(created);

    }

    @Override
    public Optional<Topic> findById(Long id) {

        return jpa.findById(id).map(topicMapper::toDomain);
    }

    @Override
    public List<Topic> findAll() {
        return jpa.findAll().stream()
                .map(topicMapper::toDomain).collect(Collectors.toList());
    }
}
