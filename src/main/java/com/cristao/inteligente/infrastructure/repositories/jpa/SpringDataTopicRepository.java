package com.cristao.inteligente.infrastructure.repositories.jpa;

import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.TopicEntityJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SpringDataTopicRepository extends JpaRepository<TopicEntityJPA, Long> {
}
