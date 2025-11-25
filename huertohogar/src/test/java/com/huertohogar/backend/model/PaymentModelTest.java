package com.huertohogar.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PaymentModelTest {

    @Test
    public void testPaymentModelCreation() {
        PaymentModel payment = new PaymentModel();
        payment.setId(1L);
        payment.setUsuarioId(1L);
        payment.setAmount(new BigDecimal("100.00"));
        payment.setCurrency("EUR");
        payment.setMethod("CARD");
        payment.setStatus("SUCCESS");
        payment.setProviderReference("MOCK-12345");

        assertEquals(1L, payment.getId());
        assertEquals(1L, payment.getUsuarioId());
        assertEquals(new BigDecimal("100.00"), payment.getAmount());
        assertEquals("EUR", payment.getCurrency());
        assertEquals("CARD", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("MOCK-12345", payment.getProviderReference());
    }

    @Test
    public void testPaymentModelSetters() {
        PaymentModel payment = new PaymentModel();
        LocalDateTime now = LocalDateTime.now();

        payment.setId(2L);
        payment.setStatus("PENDING");
        payment.setCreatedAt(now);

        assertEquals(2L, payment.getId());
        assertEquals("PENDING", payment.getStatus());
        assertEquals(now, payment.getCreatedAt());
    }

    @Test
    public void testPaymentModelAmountPrecision() {
        PaymentModel payment = new PaymentModel();
        payment.setAmount(new BigDecimal("99.99"));

        assertEquals(new BigDecimal("99.99"), payment.getAmount());
    }
}
