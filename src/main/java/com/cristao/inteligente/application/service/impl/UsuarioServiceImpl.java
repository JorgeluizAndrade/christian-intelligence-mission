package com.cristao.inteligente.application.service.impl;


import com.cristao.inteligente.application.service.IUsuarioService;
import com.cristao.inteligente.domain.entity.Usuario;
import com.cristao.inteligente.domain.repository.UsuarioRepository;
import com.cristao.inteligente.domain.valueobject.Role;
import com.cristao.inteligente.infrastructure.mapper.UsuarioMapper;
import com.cristao.inteligente.infrastructure.repositories.jpa.entity.UsuarioEntityJPA;
import com.cristao.inteligente.shared.dto.UsuarioRequest;
import com.cristao.inteligente.shared.dto.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {


    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponse createUsuarioAdmin(UsuarioRequest dto) {
        return saveUser(dto, Role.ROLE_ADMIN);
    }

    @Override
    public UsuarioResponse createUsuarioColaborador(UsuarioRequest dto) {
        return saveUser(dto, Role.ROLE_COLABORADOR);
    }


    private UsuarioResponse saveUser(UsuarioRequest dto, Role role) {
        Usuario user = new Usuario();

        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());

        String encryptedPassword = passwordEncoder.encode(dto.getPassword());

        user.setPassword(encryptedPassword);

        user.setRole(role);

        Usuario saved = usuarioRepository.save(user);


        return new UsuarioResponse(
                saved.getId(),
                saved.getNome(),
                saved.getEmail(),
                saved.getRole()
        );

    }

    @Override
    public Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }
}
