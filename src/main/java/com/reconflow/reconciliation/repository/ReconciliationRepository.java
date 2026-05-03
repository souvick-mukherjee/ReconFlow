package com.reconflow.reconciliation.repository;

import com.reconflow.reconciliation.model.ReconciliationRecord;
import com.reconflow.reconciliation.model.ReconciliationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReconciliationRepository extends JpaRepository<ReconciliationRecord, UUID> {

    Optional<ReconciliationRecord> findByPaymentId(UUID paymentId);

    List<ReconciliationRecord> findByStatusNot(ReconciliationStatus status);
}
