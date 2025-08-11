package com.cristao.inteligente.services;


import com.cristao.inteligente.dto.input.UsuarioRequest;
import com.cristao.inteligente.dto.input.UsuarioResponse;
import com.cristao.inteligente.model.Role;
import com.cristao.inteligente.model.Usuario;
import com.cristao.inteligente.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService{


    @Autowired
    private  UsuarioRepository usuarioRepository;


    @Override
    public UsuarioResponse createUsuarioAdmin(UsuarioRequest dto) {
        Usuario user = new Usuario();

        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        user.setRole(Role.ROLE_ADMIN);



        usuarioRepository.save(user);

       return new UsuarioResponse(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

    }

    @Override
    public UsuarioResponse createUsuarioColaborador(UsuarioRequest dto) {
        Usuario user = new Usuario();

        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        user.setRole(Role.ROLE_COLABORADOR);

        usuarioRepository.save(user);

        return new UsuarioResponse(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

    }

    @Override
    public Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
    }

}
