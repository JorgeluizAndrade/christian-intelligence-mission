package com.cristao.inteligente.infrastructure.repositories.jpa;

import com.cristao.inteligente.infrastructure.repositories.jpa.entity.ConteudoEntityJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SpringDataConteudoRepository extends JpaRepository<ConteudoEntityJPA, Long> {
}
