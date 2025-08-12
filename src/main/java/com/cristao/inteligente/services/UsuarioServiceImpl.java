package com.cristao.inteligente.services;


import com.cristao.inteligente.dto.input.UsuarioRequest;
import com.cristao.inteligente.dto.input.UsuarioResponse;
import com.cristao.inteligente.model.Role;
import com.cristao.inteligente.model.Usuario;
import com.cristao.inteligente.repositories.UsuarioRepository;
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

        usuarioRepository.save(user);

        return new UsuarioResponse(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getRole()
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
