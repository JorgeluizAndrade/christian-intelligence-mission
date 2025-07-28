package com.cristao.inteligente.repositories;

import com.cristao.inteligente.model.Conteudo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConteudoRepository extends JpaRepository<Conteudo, Long> {
}
