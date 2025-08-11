package com.cristao.inteligente.controller;

import com.cristao.inteligente.dto.input.UsuarioRequest;
import com.cristao.inteligente.dto.input.UsuarioResponse;
import com.cristao.inteligente.services.IUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    private final IUsuarioService service;

    public UsuarioController(IUsuarioService service) {
        this.service = service;
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
