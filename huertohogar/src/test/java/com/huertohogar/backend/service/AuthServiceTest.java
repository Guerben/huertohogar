package com.huertohogar.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.huertohogar.backend.dto.AuthResponse;
import com.huertohogar.backend.dto.LoginRequest;
import com.huertohogar.backend.dto.RegisterRequest;
import com.huertohogar.backend.dto.UsuarioDTO;
import com.huertohogar.backend.model.RolModel;
import com.huertohogar.backend.model.UsuarioModel;
import com.huertohogar.backend.repository.RolRepository;
import com.huertohogar.backend.repository.UsuarioRepository;
import com.huertohogar.backend.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private UsuarioModel usuario;
    private RolModel rol;

    @BeforeEach
    public void setup() {
        rol = new RolModel();
        rol.setId(1L);
        rol.setNombre("CLIENTE");

        usuario = new UsuarioModel();
        usuario.setId_usuario(1L);
        usuario.setNombre("Test User");
        usuario.setEmail("test@example.com");
        usuario.setPassword("hashedPassword");
        usuario.setRolId(1L);
        usuario.setFechaRegistro(LocalDateTime.now());
    }

    @Test
    public void testRegistrar() {
        RegisterRequest request = new RegisterRequest();
        request.setNombre("New User");
        request.setEmail("newuser@example.com");
        request.setPassword("password123");

        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(rolRepository.findByNombre("CLIENTE")).thenReturn(Optional.of(rol));
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuario);
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(jwtUtil.generarToken(anyString(), any(Long.class), anyString())).thenReturn("token123");

        AuthResponse response = authService.registrar(request);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    public void testRegistrarEmailExistente() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@example.com");

        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.registrar(request));
    }

    @Test
    public void testLogin() {
        LoginRequest request = new LoginRequest("test@example.com", "password123");

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(jwtUtil.generarToken(anyString(), any(Long.class), anyString())).thenReturn("token123");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals(1L, response.getId());
    }

    @Test
    public void testLoginPasswordInvalida() {
        LoginRequest request = new LoginRequest("test@example.com", "wrongpassword");

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    public void testAuthenticateUser() {
        LoginRequest request = new LoginRequest("test@example.com", "password123");

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        UsuarioDTO result = authService.authenticateUser(request);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("CLIENTE", result.getRolNombre());
    }

    @Test
    public void testProcessOAuthPostLogin() {
        java.util.Map<String, Object> attributes = new java.util.HashMap<>();
        attributes.put("email", "oauth@example.com");
        attributes.put("name", "OAuth User");

        when(usuarioRepository.findByEmail("oauth@example.com")).thenReturn(Optional.of(usuario));
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        UsuarioDTO result = authService.processOAuthPostLogin(attributes, "google");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    public void testValidarToken() {
        when(jwtUtil.validarToken("validToken")).thenReturn(true);

        assertTrue(authService.validarToken("validToken"));
    }

    @Test
    public void testObtenerUsuarioDelToken() {
        when(jwtUtil.obtenerEmailDelToken(anyString())).thenReturn("test@example.com");
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        UsuarioDTO result = authService.obtenerUsuarioDelToken("token123");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }
}
