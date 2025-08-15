package com.cristao.inteligente.infrastructure.mapper;

import com.cristao.inteligente.domain.entity.Conteudo;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.ConteudoEntityJPA;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConteudoMapper {

    // Domain -> Entity (só id do topico)
    @Mapping(target = "topico.id", source = "topico.id")
    @Mapping(target = "topico.nome", ignore = true)       // Evita mapear tudo
    @Mapping(target = "topico.descTopic", ignore = true)
    @Mapping(target = "topico.tipo", ignore = true)
    @Mapping(target = "topico.topicoPai", ignore = true)
    @Mapping(target = "topico.filhos", ignore = true)
    @Mapping(target = "topico.conteudos", ignore = true)
    ConteudoEntityJPA toEntity(Conteudo conteudo);

    // Entity -> Domain (só id do topico)
    @Mapping(target = "topico.id", source = "topico.id")
    @Mapping(target = "topico.nome", ignore = true)
    @Mapping(target = "topico.descTopic", ignore = true)
    @Mapping(target = "topico.tipo", ignore = true)
    @Mapping(target = "topico.topicoPai", ignore = true)
    @Mapping(target = "topico.filhos", ignore = true)
    @Mapping(target = "topico.conteudos", ignore = true)
    Conteudo toDomain(ConteudoEntityJPA conteudoEntityJPA);
}
