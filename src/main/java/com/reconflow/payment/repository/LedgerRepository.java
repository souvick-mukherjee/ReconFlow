package com.reconflow.payment.repository;

import com.reconflow.payment.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LedgerRepository extends JpaRepository<LedgerEntry, UUID> {

    Optional<LedgerEntry> findByPaymentId(UUID paymentId);
}
