package com.reconflow.saga.repository;

import com.reconflow.saga.model.SagaInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SagaRepository extends JpaRepository<SagaInstance, UUID> {
    Optional<SagaInstance> findByPaymentId(UUID paymentId);
}
