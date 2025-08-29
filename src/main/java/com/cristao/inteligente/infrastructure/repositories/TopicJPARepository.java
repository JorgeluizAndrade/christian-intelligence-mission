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

        // Corrigir relações (evitar null em cascade)
        if (entity.getConteudos() != null) {
            entity.getConteudos().forEach(c -> c.setTopico(entity));
        }
        if (entity.getFilhos() != null) {
            entity.getFilhos().forEach(f -> f.setTopicoPai(entity));
        }

        if (topic.getTopicoPai() != null) {
            TopicEntityJPA paiEntity = jpa.findById(topic.getTopicoPai().getId())
                    .orElseThrow(() -> new RuntimeException("Pai não encontrado"));
            entity.setTopicoPai(paiEntity);
        }

        TopicEntityJPA saved = jpa.save(entity);
        return topicMapper.toDomain(saved);
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
