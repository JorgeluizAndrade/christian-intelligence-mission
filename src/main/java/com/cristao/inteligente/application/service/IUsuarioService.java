package com.cristao.inteligente.application.service;

import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.shared.dto.UsuarioRequest;
import com.cristao.inteligente.shared.dto.UsuarioResponse;

public interface IUsuarioService {

    public UsuarioResponse createUsuarioAdmin(UsuarioRequest dto);

    public UsuarioResponse createUsuarioColaborador(UsuarioRequest dto);

    public Usuario findUsuarioById(Long id);
}
