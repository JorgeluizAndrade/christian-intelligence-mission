package com.cristao.inteligente.infrastructure.mapper;

import com.cristao.inteligente.domain.entity.Conteudo;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.ConteudoEntityJPA;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConteudoMapper {

    @Mapping(target = "topico", ignore = true)  // não mapear de volta
    ConteudoEntityJPA toEntity(Conteudo conteudo);

    @Mapping(target = "topico", ignore = true)
    Conteudo toDomain(ConteudoEntityJPA entity);

}
