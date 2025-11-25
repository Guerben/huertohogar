package com.huertohogar.backend.controller;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huertohogar.backend.dto.AuthResponse;
import com.huertohogar.backend.dto.LoginRequest;
import com.huertohogar.backend.dto.RegisterRequest;
import com.huertohogar.backend.dto.UsuarioDTO;
import com.huertohogar.backend.service.AuthService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testRegister() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        AuthResponse response = new AuthResponse("token123", 1L, "test@example.com", "Test User", "CLIENTE");

        when(authService.registrar(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token123"));
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest request = new LoginRequest("test@example.com", "password123");

        AuthResponse response = new AuthResponse("token123", 1L, "test@example.com", "Test User", "CLIENTE");

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"));
    }

    @Test
    public void testLoginUnauthorized() throws Exception {
        LoginRequest request = new LoginRequest("test@example.com", "wrongpassword");

        when(authService.login(any(LoginRequest.class))).thenThrow(new RuntimeException("Credenciales inválidas"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testValidarToken() throws Exception {
        UsuarioDTO usuario = new UsuarioDTO(1L, "Test User", "test@example.com", null, "123456", "Address", 1L,
                "CLIENTE", LocalDateTime.now());

        when(authService.validarToken("validToken")).thenReturn(true);
        when(authService.obtenerUsuarioDelToken("validToken")).thenReturn(usuario);

        mockMvc.perform(get("/api/auth/validate")
                .param("token", "validToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testValidarTokenInvalido() throws Exception {
        when(authService.validarToken("invalidToken")).thenReturn(false);

        mockMvc.perform(get("/api/auth/validate")
                .param("token", "invalidToken"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginSession() throws Exception {
        LoginRequest request = new LoginRequest("test@example.com", "password123");
        UsuarioDTO usuario = new UsuarioDTO(1L, "Test User", "test@example.com", null, "123456", "Address", 1L,
                "CLIENTE", LocalDateTime.now());

        when(authService.authenticateUser(any(LoginRequest.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/auth/login-session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testLogoutSession() throws Exception {
        mockMvc.perform(post("/api/auth/logout-session"))
                .andExpect(status().isOk());
    }
}
