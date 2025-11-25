package com.huertohogar.backend.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huertohogar.backend.dto.PaymentRequest;
import com.huertohogar.backend.dto.PaymentResponse;
import com.huertohogar.backend.model.PaymentModel;
import com.huertohogar.backend.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Procesa un pago de forma mock (simula llamada a proveedor y guarda
     * transacción).
     * - Guarda registro con estado PENDING, simula respuesta y actualiza estado.
     */
    public PaymentResponse processPayment(PaymentRequest request) {
        PaymentModel p = new PaymentModel();
        p.setUsuarioId(request.getUsuarioId());
        p.setAmount(request.getAmount());
        p.setCurrency(request.getCurrency() != null ? request.getCurrency() : "EUR");
        p.setMethod(request.getMethod() != null ? request.getMethod() : "CARD");
        p.setStatus("PENDING");
        p.setCreatedAt(LocalDateTime.now());

        p = paymentRepository.save(p);

        // Simular llamada a proveedor: generar referencia y estado aleatorio (aprox 90%
        // éxito)
        String providerRef = "MOCK-" + UUID.randomUUID();
        boolean success = Math.random() < 0.9;

        p.setProviderReference(providerRef);
        p.setStatus(success ? "SUCCESS" : "FAILED");

        p = paymentRepository.save(p);

        return new PaymentResponse(
                p.getId(),
                p.getUsuarioId(),
                p.getAmount(),
                p.getCurrency(),
                p.getStatus(),
                p.getProviderReference(),
                p.getCreatedAt());
    }

    public PaymentResponse getPayment(Long id) {
        return paymentRepository.findById(id)
                .map(p -> new PaymentResponse(
                        p.getId(), p.getUsuarioId(), p.getAmount(), p.getCurrency(), p.getStatus(),
                        p.getProviderReference(), p.getCreatedAt()))
                .orElse(null);
    }
}
