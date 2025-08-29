package com.cristao.inteligente.infrastructure.repositories;

import com.cristao.inteligente.domain.entity.Conteudo;
import com.cristao.inteligente.domain.repository.ConteudoRepository;
import com.cristao.inteligente.infrastructure.mapper.ConteudoMapper;
import com.cristao.inteligente.infrastructure.repositories.jpa.SpringDataConteudoRepository;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.ConteudoEntityJPA;
import org.springframework.stereotype.Repository;


@Repository
public class ConteudoJPARepository implements ConteudoRepository {

    private final SpringDataConteudoRepository springDataConteudoRepository;
    private final ConteudoMapper conteudoMapper;

    public ConteudoJPARepository(ConteudoMapper conteudoMapper, SpringDataConteudoRepository springDataConteudoRepository) {
        this.conteudoMapper = conteudoMapper;
        this.springDataConteudoRepository = springDataConteudoRepository;
    }


    @Override
    public Conteudo create(Conteudo conteudo) {
        ConteudoEntityJPA entity = conteudoMapper.toEntity(conteudo);

        ConteudoEntityJPA created = springDataConteudoRepository.save(entity);

        return conteudoMapper.toDomain(created);
    }
}
