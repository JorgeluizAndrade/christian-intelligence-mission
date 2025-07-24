package com.cristao.inteligente.repositories;

import com.cristao.inteligente.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
