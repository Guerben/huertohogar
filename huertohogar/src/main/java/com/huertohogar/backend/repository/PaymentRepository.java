package com.huertohogar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huertohogar.backend.model.PaymentModel;

public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {

}
