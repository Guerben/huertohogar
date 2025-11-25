package com.huertohogar.backend.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class UsuarioDTOTest {

    @Test
    public void testUsuarioDTOCreation() {
        LocalDateTime now = LocalDateTime.now();
        UsuarioDTO usuario = new UsuarioDTO(
                1L, "Test User", "test@example.com", null, "123456", "Address", 1L, "CLIENTE", now);

        assertEquals(1L, usuario.getId());
        assertEquals("Test User", usuario.getNombre());
        assertEquals("test@example.com", usuario.getEmail());
        assertEquals("123456", usuario.getTelefono());
        assertEquals("Address", usuario.getDireccion());
        assertEquals("CLIENTE", usuario.getRolNombre());
    }

    @Test
    public void testUsuarioDTOSetters() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(2L);
        usuario.setNombre("Another User");
        usuario.setEmail("another@example.com");

        assertEquals(2L, usuario.getId());
        assertEquals("Another User", usuario.getNombre());
        assertEquals("another@example.com", usuario.getEmail());
    }
}
