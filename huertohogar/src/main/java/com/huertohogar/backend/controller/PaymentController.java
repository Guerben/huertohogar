package com.huertohogar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huertohogar.backend.dto.PaymentRequest;
import com.huertohogar.backend.dto.PaymentResponse;
import com.huertohogar.backend.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request) {
        try {
            PaymentResponse resp = paymentService.processPayment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable Long id) {
        PaymentResponse resp = paymentService.getPayment(id);
        if (resp == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado");
        return ResponseEntity.ok(resp);
    }
}
