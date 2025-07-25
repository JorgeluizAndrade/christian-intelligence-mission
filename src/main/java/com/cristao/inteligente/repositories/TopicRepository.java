package com.cristao.inteligente.repositories;

import com.cristao.inteligente.model.Topic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    @Override
    Optional<Topic> findById(Integer integer);


    @EntityGraph(attributePaths = {"topicoPai"})
    List<Topic> findAll();
}
