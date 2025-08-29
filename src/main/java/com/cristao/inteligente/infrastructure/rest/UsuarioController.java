package com.cristao.inteligente.infrastructure.rest;

import com.cristao.inteligente.shared.dto.LoginRequest;
import com.cristao.inteligente.shared.dto.LoginResponse;
import com.cristao.inteligente.shared.dto.UsuarioRequest;
import com.cristao.inteligente.shared.dto.UsuarioResponse;
import com.cristao.inteligente.application.service.IAuthService;
import com.cristao.inteligente.application.service.IUsuarioService;
import org.springframework.http.ResponseEntity;
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
