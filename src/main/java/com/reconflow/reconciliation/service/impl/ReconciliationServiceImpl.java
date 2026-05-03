package com.reconflow.reconciliation.service.impl;

import com.reconflow.ledger.model.LedgerEntry;
import com.reconflow.ledger.repository.LedgerRepository;
import com.reconflow.ledger.service.LedgerService;
import com.reconflow.payment.model.Payment;
import com.reconflow.reconciliation.model.ReconciliationRecord;
import com.reconflow.reconciliation.model.ReconciliationStatus;
import com.reconflow.reconciliation.repository.ReconciliationRepository;
import com.reconflow.reconciliation.service.ReconciliationService;
import com.reconflow.settlement.model.SettlementRecord;
import com.reconflow.settlement.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReconciliationServiceImpl implements ReconciliationService {

    private final LedgerRepository ledgerRepository;
    private final SettlementRepository settlementRepository;
    private final ReconciliationRepository reconciliationRepository;

    @Override
    public ReconciliationRecord reconcile(Payment payment) {

        LedgerEntry ledger = ledgerRepository.findByPaymentId(payment.getId()).orElse(null);

        SettlementRecord settlement = settlementRepository.findByPaymentId(payment.getId()).orElse(null);

        BigDecimal paymentAmount = payment.getAmount();
        BigDecimal ledgerAmount = ledger != null ? ledger.getAmount() : BigDecimal.ZERO;
        BigDecimal settledAmount = settlement != null ? settlement.getSettledAmount() : BigDecimal.ZERO;

        BigDecimal variance = paymentAmount.subtract(settledAmount);

        ReconciliationStatus status;
        if (ledger == null) {
            status = ReconciliationStatus.MISSING_LEDGER;
        } else if (settlement == null) {
            status = ReconciliationStatus.MISSING_SETTLEMENT;
        } else if (paymentAmount.compareTo(ledgerAmount) == 0 && ledgerAmount.compareTo(settledAmount) == 0) {
            status = ReconciliationStatus.MATCHED;
        } else {
            status = ReconciliationStatus.AMOUNT_MISMATCH;
        }

        ReconciliationRecord record = ReconciliationRecord.builder()
                .id(UUID.randomUUID())
                .paymentId(payment.getId())
                .paymentAmount(paymentAmount)
                .ledgerAmount(ledgerAmount)
                .settledAmount(settledAmount)
                .variance(variance)
                .status(status)
                .matchedAt(LocalDateTime.now())
                .build();

        return reconciliationRepository.save(record);
    }
}
