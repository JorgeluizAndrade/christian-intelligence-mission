package com.cristao.inteligente.infrastructure.mapper;


import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.TopicEntityJPA;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ConteudoMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TopicMapper {

    TopicEntityJPA toEntity(Topic topic);

    Topic toDomain(TopicEntityJPA topicEntity);

}
