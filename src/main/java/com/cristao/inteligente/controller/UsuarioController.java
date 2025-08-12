package com.cristao.inteligente.controller;

import com.cristao.inteligente.dto.input.LoginRequest;
import com.cristao.inteligente.dto.input.LoginResponse;
import com.cristao.inteligente.dto.input.UsuarioRequest;
import com.cristao.inteligente.dto.input.UsuarioResponse;
import com.cristao.inteligente.model.Usuario;
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
    private final ITokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UsuarioController(IUsuarioService service, ITokenService token, AuthenticationManager authenticationManager) {
        this.service = service;
        this.tokenService = token;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));


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
