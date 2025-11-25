package com.huertohogar.backend.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "miClaveSecretaSuperSeguraParaJWT123456789");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86400000L);
    }

    @Test
    public void testGenerarToken() {
        String token = jwtUtil.generarToken("test@example.com", 1L, "CLIENTE");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testValidarToken() {
        String token = jwtUtil.generarToken("test@example.com", 1L, "CLIENTE");
        assertTrue(jwtUtil.validarToken(token));
    }

    @Test
    public void testValidarTokenInvalido() {
        assertFalse(jwtUtil.validarToken("token.invalido.abc"));
    }

    @Test
    public void testObtenerEmailDelToken() {
        String email = "test@example.com";
        String token = jwtUtil.generarToken(email, 1L, "CLIENTE");
        assertEquals(email, jwtUtil.obtenerEmailDelToken(token));
    }

    @Test
    public void testObtenerUserIdDelToken() {
        String token = jwtUtil.generarToken("test@example.com", 123L, "CLIENTE");
        Long userId = jwtUtil.obtenerUserIdDelToken(token);
        assertEquals(123L, userId);
    }

    @Test
    public void testObtenerRolDelToken() {
        String token = jwtUtil.generarToken("test@example.com", 1L, "ADMIN");
        assertEquals("ADMIN", jwtUtil.obtenerRolDelToken(token));
    }
}
