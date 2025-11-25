package com.huertohogar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huertohogar.backend.dto.AuthResponse;
import com.huertohogar.backend.dto.LoginRequest;
import com.huertohogar.backend.dto.RegisterRequest;
import com.huertohogar.backend.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest request) {
        try {
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

    // Login basado en sesión: crea una sesión HTTP y guarda el SecurityContext
    @PostMapping("/login-session")
    public ResponseEntity<?> loginSession(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        try {
            // Authenticate credentials and obtain user DTO
            com.huertohogar.backend.dto.UsuarioDTO usuario = authService.authenticateUser(request);

            // Build authentication token with role
            String roleName = usuario.getRolNombre() != null ? usuario.getRolNombre() : "CLIENTE";
            String authority = "ROLE_" + roleName.toUpperCase();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    usuario.getEmail(), null,
                    java.util.Collections.singletonList(new SimpleGrantedAuthority(authority)));

            SecurityContextHolder.getContext().setAuthentication(authToken);
            // Persist in HTTP session so subsequent requests are authenticated
            httpRequest.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout-session")
    public ResponseEntity<?> logoutSession(HttpServletRequest request) {
        try {
            if (request.getSession(false) != null) {
                request.getSession(false).invalidate();
            }
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Sesión cerrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cerrar sesión");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validarToken(@RequestParam String token) {
        try {
            boolean esValido = authService.validarToken(token);
            if (esValido) {
                // Obtener info del usuario del token
                com.huertohogar.backend.dto.UsuarioDTO usuario = authService.obtenerUsuarioDelToken(token);
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

            com.huertohogar.backend.dto.UsuarioDTO usuario = authService.obtenerUsuarioDelToken(token);

            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido");
        }
    }

    // Endpoint invoked after successful OAuth2 login (provider redirects here)
    @GetMapping("/oauth2/success")
    public ResponseEntity<?> oauth2Success(OAuth2AuthenticationToken oauthToken, HttpServletRequest request) {
        try {
            java.util.Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();

            // Let the service find or create a user based on provider attributes
            com.huertohogar.backend.dto.UsuarioDTO usuario = authService.processOAuthPostLogin(attributes,
                    oauthToken.getAuthorizedClientRegistrationId());

            // Create session for the user
            String roleName = usuario.getRolNombre() != null ? usuario.getRolNombre() : "CLIENTE";
            String authority = "ROLE_" + roleName.toUpperCase();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    usuario.getEmail(), null,
                    java.util.Collections.singletonList(new SimpleGrantedAuthority(authority)));

            SecurityContextHolder.getContext().setAuthentication(authToken);
            request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en OAuth2 login");
        }
    }
}
