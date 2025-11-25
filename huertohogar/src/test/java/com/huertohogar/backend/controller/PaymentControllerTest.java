package com.huertohogar.backend.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.huertohogar.backend.dto.PaymentRequest;
import com.huertohogar.backend.dto.PaymentResponse;
import com.huertohogar.backend.service.PaymentService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentResponse paymentResponse;

    @BeforeEach
    public void setup() {
        paymentResponse = new PaymentResponse(
                1L, 1L, new BigDecimal("100.00"), "EUR", "SUCCESS", "MOCK-12345", LocalDateTime.now());
    }

    @Test
    public void testProcessPayment() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setUsuarioId(1L);
        request.setAmount(new BigDecimal("100.00"));
        request.setCurrency("EUR");
        request.setMethod("CARD");
        request.setDescription("Test payment");

        when(paymentService.processPayment(any(PaymentRequest.class))).thenReturn(paymentResponse);

        mockMvc.perform(post("/api/payments/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    public void testGetPayment() throws Exception {
        when(paymentService.getPayment(anyLong())).thenReturn(paymentResponse);

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void testGetPaymentNotFound() throws Exception {
        when(paymentService.getPayment(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/payments/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testProcessPaymentInvalidRequest() throws Exception {
        when(paymentService.processPayment(any(PaymentRequest.class)))
                .thenThrow(new RuntimeException("Invalid payment"));

        PaymentRequest request = new PaymentRequest();
        request.setUsuarioId(1L);

        mockMvc.perform(post("/api/payments/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
