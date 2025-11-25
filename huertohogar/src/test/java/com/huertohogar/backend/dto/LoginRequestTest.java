package com.huertohogar.backend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class LoginRequestTest {

    @Test
    public void testLoginRequestCreation() {
        LoginRequest request = new LoginRequest("test@example.com", "password123");

        assertEquals("test@example.com", request.getEmail());
        assertEquals("password123", request.getPassword());
    }

    @Test
    public void testLoginRequestSetters() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("securepass");

        assertEquals("user@example.com", request.getEmail());
        assertEquals("securepass", request.getPassword());
    }

    @Test
    public void testLoginRequestEmptyConstructor() {
        LoginRequest request = new LoginRequest();
        assertNull(request.getEmail());
        assertNull(request.getPassword());
    }
}
