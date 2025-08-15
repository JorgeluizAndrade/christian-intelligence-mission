package com.cristao.inteligente.domain.entity;

import com.cristao.inteligente.domain.valueobject.Role;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String password;
    private Role role;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isAdmin() {
        return this.role == Role.ROLE_ADMIN;
    }
}
