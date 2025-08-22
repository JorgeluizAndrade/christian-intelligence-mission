package com.cristao.inteligente.application.usecase;

import com.cristao.inteligente.domain.entity.Conteudo;
import com.cristao.inteligente.domain.entity.Topic;
import com.cristao.inteligente.shared.dto.ConteudoDTO;

public interface ICreateConteudoUseCase {
    public Conteudo createConteudo(Topic topic, ConteudoDTO dto);

}
