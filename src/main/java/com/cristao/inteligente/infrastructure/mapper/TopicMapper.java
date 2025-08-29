package com.cristao.inteligente.infrastructure.mapper;

import com.cristao.inteligente.domain.entity.Conteudo;
import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.ConteudoEntityJPA;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.TopicEntityJPA;
import org.mapstruct.Mapper;
import org.mapstruct.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    default TopicEntityJPA toEntity(Topic topic) {
        return toEntity(topic, new HashMap<>());
    }

    default Topic toDomain(TopicEntityJPA entity) {
        return toDomain(entity, new HashMap<>());
    }

    default TopicEntityJPA toEntity(Topic topic, @Context Map<Long, TopicEntityJPA> context) {
        if (topic == null) return null;
        if (topic.getId() != null && context.containsKey(topic.getId())) {
            return context.get(topic.getId());
        }

        TopicEntityJPA entity = new TopicEntityJPA();
        entity.setId(topic.getId());
        entity.setNome(topic.getNome());
        entity.setAutor(topic.getAutor());
        entity.setDescTopic(topic.getDescTopic());
        entity.setTipo(topic.getTipo());

        // Salva no contexto para evitar loop
        if (topic.getId() != null) {
            context.put(topic.getId(), entity);
        }

        // Mapear pai com contexto
        if (topic.getTopicoPai() != null) {
            entity.setTopicoPai(toEntity(topic.getTopicoPai(), context));
        }

        // Mapear filhos com contexto
        if (topic.getFilhos() != null) {
            entity.setFilhos(
                    topic.getFilhos().stream()
                            .map(f -> {
                                TopicEntityJPA fEntity = toEntity(f, context);
                                fEntity.setTopicoPai(entity); // garante referência ao pai
                                return fEntity;
                            }).collect(Collectors.toList())
            );
        }

        // Mapear conteúdos sem contexto
        if (topic.getConteudos() != null) {
            topic.getConteudos().forEach(c -> c.setTopico(toDomain(entity)));
            entity.setConteudos(topic.getConteudos().stream().map(c -> {
                ConteudoEntityJPA ce = new ConteudoEntityJPA();
                ce.setId(c.getId());
                ce.setAutor(c.getAutor());
                ce.setDescricao(c.getDescricao());
                ce.setLink(c.getLink());
                ce.setTitulo(c.getTitulo());
                ce.setTipo(c.getTipo());
                ce.setTopico(entity);
                return ce;
            }).collect(Collectors.toList()));
        }

        return entity;
    }

    default Topic toDomain(TopicEntityJPA entity, @Context Map<Long, Topic> context) {
        if (entity == null) return null;
        if (entity.getId() != null && context.containsKey(entity.getId())) {
            return context.get(entity.getId());
        }

        Topic topic = new Topic();
        topic.setId(entity.getId());
        topic.setNome(entity.getNome());
        topic.setAutor(entity.getAutor());
        topic.setDescTopic(entity.getDescTopic());
        topic.setTipo(entity.getTipo());

        if (entity.getId() != null) {
            context.put(entity.getId(), topic);
        }

        if (entity.getTopicoPai() != null) {
            topic.setTopicoPai(toDomain(entity.getTopicoPai(), context));
        }

        if (entity.getFilhos() != null) {
            topic.setFilhos(
                    entity.getFilhos().stream()
                            .map(f -> {
                                Topic fTopic = toDomain(f, context);
                                fTopic.setTopicoPai(topic);
                                return fTopic;
                            }).collect(Collectors.toList())
            );
        }

        if (entity.getConteudos() != null) {
            topic.setConteudos(
                    entity.getConteudos().stream().map(c -> {
                        Conteudo con = new Conteudo();
                        con.setId(c.getId());
                        con.setAutor(c.getAutor());
                        con.setDescricao(c.getDescricao());
                        con.setLink(c.getLink());
                        con.setTitulo(c.getTitulo());
                        con.setTipo(c.getTipo());
                        con.setTopico(topic);
                        return con;
                    }).collect(Collectors.toList())
            );
        }

        return topic;
    }
}
