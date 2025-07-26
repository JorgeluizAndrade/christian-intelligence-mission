package com.cristao.inteligente.repositories;

import com.cristao.inteligente.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
