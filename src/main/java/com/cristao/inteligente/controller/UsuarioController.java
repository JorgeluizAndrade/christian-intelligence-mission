package com.cristao.inteligente.controller;

import com.cristao.inteligente.dto.input.LoginRequest;
import com.cristao.inteligente.dto.input.LoginResponse;
import com.cristao.inteligente.dto.input.UsuarioRequest;
import com.cristao.inteligente.dto.input.UsuarioResponse;
import com.cristao.inteligente.model.Usuario;
import com.cristao.inteligente.services.IAuthService;
import com.cristao.inteligente.services.ITokenService;
import com.cristao.inteligente.services.IUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    private final IUsuarioService service;
    private final IAuthService authService;

    public UsuarioController(IUsuarioService service, IAuthService authService) {
        this.service = service;
        this.authService = authService;

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }


    @PostMapping("/admin")
    public ResponseEntity<UsuarioResponse> createAdmin(@RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(service.createUsuarioAdmin(request));
    }

    @PostMapping("/colaborador")
    public ResponseEntity<UsuarioResponse> createColaborador(@RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(service.createUsuarioColaborador(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findUsuarioById(id));
    }
}
