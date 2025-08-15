package com.cristao.inteligente.infrastructure.repositories.jpa.entity;

import com.cristao.inteligente.domain.valueobject.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "usuario")
@Data
public class UsuarioEntityJPA implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Email
    @Column(name = "email", nullable = false)
    private String email;


    @Column(name = "password", nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ROLE_ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_COLABORADOR"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_COLABORADOR"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
