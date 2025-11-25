package com.huertohogar.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.huertohogar.backend.dto.PaymentRequest;
import com.huertohogar.backend.dto.PaymentResponse;
import com.huertohogar.backend.model.PaymentModel;
import com.huertohogar.backend.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private PaymentModel payment;

    @BeforeEach
    public void setup() {
        payment = new PaymentModel();
        payment.setId(1L);
        payment.setUsuarioId(1L);
        payment.setAmount(new BigDecimal("100.00"));
        payment.setCurrency("EUR");
        payment.setMethod("CARD");
        payment.setStatus("SUCCESS");
        payment.setProviderReference("MOCK-12345");
        payment.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testProcessPayment() {
        PaymentRequest request = new PaymentRequest();
        request.setUsuarioId(1L);
        request.setAmount(new BigDecimal("100.00"));
        request.setCurrency("EUR");
        request.setMethod("CARD");
        request.setDescription("Test payment");

        when(paymentRepository.save(any(PaymentModel.class))).thenReturn(payment);

        PaymentResponse response = paymentService.processPayment(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getUsuarioId());
        assertTrue(response.getStatus().equals("SUCCESS") || response.getStatus().equals("FAILED"));
    }

    @Test
    public void testProcessPaymentDefaultCurrency() {
        PaymentRequest request = new PaymentRequest();
        request.setUsuarioId(1L);
        request.setAmount(new BigDecimal("50.00"));

        when(paymentRepository.save(any(PaymentModel.class))).thenReturn(payment);

        PaymentResponse response = paymentService.processPayment(request);

        assertNotNull(response);
        assertNotNull(response.getCurrency());
    }

    @Test
    public void testGetPayment() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.getPayment(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("SUCCESS", response.getStatus());
    }

    @Test
    public void testGetPaymentNotFound() {
        when(paymentRepository.findById(999L)).thenReturn(Optional.empty());

        PaymentResponse response = paymentService.getPayment(999L);

        assertNull(response);
    }

    @Test
    public void testProcessPaymentAmounts() {
        PaymentRequest request = new PaymentRequest();
        request.setUsuarioId(1L);
        request.setAmount(new BigDecimal("99.99"));

        when(paymentRepository.save(any(PaymentModel.class))).thenReturn(payment);

        PaymentResponse response = paymentService.processPayment(request);

        assertNotNull(response);
        assertNotNull(response.getAmount());
    }
}
