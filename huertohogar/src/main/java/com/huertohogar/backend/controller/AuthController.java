package com.huertohogar.backend.controller;

import com.huertohogar.backend.dto.AuthResponse;
import com.huertohogar.backend.dto.LoginRequest;
import com.huertohogar.backend.dto.RegisterRequest;
import com.huertohogar.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest request){
        try{
            AuthResponse reponse = authService.registrar(request);
            return new ResponseEntity<>(reponse, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validarToken(@RequestParam String token) {
        try {
            boolean esValido = authService.validarToken(token);
            if (esValido) {
                // Obtener info del usuario del token
                com.huertohogar.backend.dto.UsuarioDTO usuario =
                        authService.obtenerUsuarioDelToken(token);
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Token inválido o expirado");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> obtenerUsuarioActual(
            @RequestHeader("Authorization") String authHeader) {
        try {

            String token = authHeader.replace("Bearer ", "");

            com.huertohogar.backend.dto.UsuarioDTO usuario =
                    authService.obtenerUsuarioDelToken(token);

            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido");
        }
    }
}
