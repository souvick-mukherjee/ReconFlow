package com.reconflow.ledger.repository;

import com.reconflow.ledger.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LedgerRepository extends JpaRepository<LedgerEntry, UUID> {

    Optional<LedgerEntry> findByPaymentId(UUID paymentId);
}
