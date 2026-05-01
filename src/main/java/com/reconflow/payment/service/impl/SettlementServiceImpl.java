package com.reconflow.payment.service.impl;

import com.reconflow.payment.model.Payment;
import com.reconflow.payment.model.SettlementRecord;
import com.reconflow.payment.model.SettlementStatus;
import com.reconflow.payment.repository.SettlementRepository;
import com.reconflow.payment.service.SettlementService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {
    private final SettlementRepository settlementRepository;

    public SettlementRecord simulateSettlement(Payment payment) {
        // introduce small mismatch randomly (realistic)
        BigDecimal settledAmount = payment.getAmount();

        if (Math.random() < 0.3) {
            settledAmount = settledAmount.subtract(BigDecimal.valueOf(2));
        }

        SettlementRecord record = SettlementRecord.builder()
                .id(UUID.randomUUID())
                .paymentId(payment.getId())
                .settlementAmount(settledAmount)
                .settlementDate(LocalDateTime.now())
                .source("BANK")
                .status(SettlementStatus.SETTLED)
                .build();
        return settlementRepository.save(record);
    }
}
