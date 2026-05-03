package com.reconflow.ledger.service.impl;

import com.reconflow.ledger.model.LedgerStatus;
import com.reconflow.ledger.model.LedgerEntry;
import com.reconflow.payment.model.Payment;
import com.reconflow.ledger.repository.LedgerRepository;
import com.reconflow.ledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;

    @Override
    public LedgerEntry createEntry(Payment payment) {
        LedgerEntry entry = LedgerEntry.builder()
                .id(UUID.randomUUID())
                .paymentId(payment.getId())
                .debitAccount("CUSTOMER_WALLET")
                .creditAccount("MERCHANT_PAYABLE")
                .amount(payment.getAmount())
                .entryStatus(LedgerStatus.POSTED)
                .createdAt(LocalDateTime.now())
                .build();

        return ledgerRepository.save(entry);

    }
}
