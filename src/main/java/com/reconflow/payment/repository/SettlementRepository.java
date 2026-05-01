package com.reconflow.payment.repository;

import com.reconflow.payment.model.SettlementRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SettlementRepository extends JpaRepository<SettlementRecord, UUID> {
    Optional<SettlementRecord> findByPaymentId(UUID paymentId);
}
