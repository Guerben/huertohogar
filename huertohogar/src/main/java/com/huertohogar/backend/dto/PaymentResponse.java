package com.huertohogar.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {

    private Long id;
    private Long usuarioId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String providerReference;
    private LocalDateTime createdAt;

    public PaymentResponse() {
    }

    public PaymentResponse(Long id, Long usuarioId, BigDecimal amount, String currency, String status,
            String providerReference, LocalDateTime createdAt) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.providerReference = providerReference;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public void setProviderReference(String providerReference) {
        this.providerReference = providerReference;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
