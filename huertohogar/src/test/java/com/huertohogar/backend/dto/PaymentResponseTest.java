package com.huertohogar.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PaymentResponseTest {

    @Test
    public void testPaymentResponseCreation() {
        LocalDateTime now = LocalDateTime.now();
        PaymentResponse response = new PaymentResponse(
                1L, 1L, new BigDecimal("100.00"), "EUR", "SUCCESS", "MOCK-12345", now);

        assertEquals(1L, response.getId());
        assertEquals(1L, response.getUsuarioId());
        assertEquals(new BigDecimal("100.00"), response.getAmount());
        assertEquals("EUR", response.getCurrency());
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("MOCK-12345", response.getProviderReference());
    }

    @Test
    public void testPaymentResponseSetters() {
        PaymentResponse response = new PaymentResponse();
        response.setId(2L);
        response.setStatus("FAILED");
        response.setAmount(new BigDecimal("50.00"));

        assertEquals(2L, response.getId());
        assertEquals("FAILED", response.getStatus());
        assertEquals(new BigDecimal("50.00"), response.getAmount());
    }
}
