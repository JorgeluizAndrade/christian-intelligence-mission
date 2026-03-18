package com.cristao.inteligente.infrastructure.rest;

import com.cristao.inteligente.application.service.IAuthService;
import com.cristao.inteligente.application.service.IUsuarioService;
import com.cristao.inteligente.shared.dto.LoginRequest;
import com.cristao.inteligente.shared.dto.LoginResponse;
import com.cristao.inteligente.shared.dto.UsuarioRequest;
import com.cristao.inteligente.shared.dto.UsuarioResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(req, response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            @CookieValue(name = "refresh_token") String refreshToken,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.refreshAccessToken(refreshToken, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.noContent().build();
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
