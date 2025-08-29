package com.cristao.inteligente.domain.repository;

import com.cristao.inteligente.domain.entity.Topic;

import java.util.List;
import java.util.Optional;



public interface TopicRepository {
    Topic save(Topic topic);
    Optional<Topic> findById(Long id);
    List<Topic> findAll();

}
