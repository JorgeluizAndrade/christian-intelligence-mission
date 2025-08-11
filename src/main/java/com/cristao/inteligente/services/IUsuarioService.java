package com.cristao.inteligente.services;

import com.cristao.inteligente.dto.input.UsuarioRequest;
import com.cristao.inteligente.dto.input.UsuarioResponse;
import com.cristao.inteligente.model.Usuario;

public interface IUsuarioService {

    public UsuarioResponse createUsuarioAdmin(UsuarioRequest dto);

    public UsuarioResponse createUsuarioColaborador(UsuarioRequest dto);

    public Usuario findUsuarioById(Long id);
}
