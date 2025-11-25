package com.huertohogar.backend.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UsuarioModelTest {

    @Test
    public void testUsuarioModelCreation() {
        UsuarioModel usuario = new UsuarioModel("Test User", "test@example.com", "password123", "Address", "123456",
                1L);

        assertEquals("Test User", usuario.getNombre());
        assertEquals("test@example.com", usuario.getEmail());
        assertEquals("password123", usuario.getPassword());
        assertEquals("Address", usuario.getDireccion());
        assertEquals("123456", usuario.getTelefono());
        assertEquals(1L, usuario.getRolId());
        assertNotNull(usuario.getFechaRegistro());
    }

    @Test
    public void testUsuarioModelEmptyConstructor() {
        UsuarioModel usuario = new UsuarioModel();
        assertNotNull(usuario.getFechaRegistro());
    }

    @Test
    public void testUsuarioModelSetters() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId_usuario(1L);
        usuario.setNombre("New Name");
        usuario.setEmail("new@example.com");
        usuario.setPassword("newpass");

        assertEquals(1L, usuario.getId_usuario());
        assertEquals("New Name", usuario.getNombre());
        assertEquals("new@example.com", usuario.getEmail());
        assertEquals("newpass", usuario.getPassword());
    }

    @Test
    public void testUsuarioModelValidation() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNombre("User");
        usuario.setEmail("user@example.com");

        assertEquals("User", usuario.getNombre());
        assertEquals("user@example.com", usuario.getEmail());
    }
}
