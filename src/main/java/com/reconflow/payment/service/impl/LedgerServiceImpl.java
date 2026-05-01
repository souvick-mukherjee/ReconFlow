package com.reconflow.payment.service.impl;

import com.reconflow.payment.model.EntryStatus;
import com.reconflow.payment.model.LedgerEntry;
import com.reconflow.payment.model.Payment;
import com.reconflow.payment.repository.LedgerRepository;
import com.reconflow.payment.service.LedgerService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

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
                .entryStatus(EntryStatus.POSTED)
                .createdAt(LocalDateTime.now())
                .build();

        return ledgerRepository.save(entry);

    }
}
