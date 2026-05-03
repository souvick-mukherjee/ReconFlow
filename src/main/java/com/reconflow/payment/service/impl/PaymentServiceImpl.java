package com.reconflow.payment.service.impl;

import com.reconflow.ledger.service.LedgerService;
import com.reconflow.payment.dto.CreatePaymentRequest;
import com.reconflow.payment.model.Payment;
import com.reconflow.payment.model.PaymentStatus;
import com.reconflow.payment.repository.PaymentRepository;
import com.reconflow.payment.service.PaymentService;
import com.reconflow.reconciliation.service.ReconciliationService;
import com.reconflow.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final LedgerService ledgerService;
    private final SettlementService settlementService;
    private final ReconciliationService reconciliationService;

    @Override
    public Payment createPayment(CreatePaymentRequest request) {
        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .merchantId(request.merchantId())
                .amount(request.amount())
                .currency(request.currency())
                .status(PaymentStatus.CAPTURED)
                .paymentReference(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .build();

        payment = paymentRepository.save(payment);

        // Step 1: Ledger
        ledgerService.createEntry(payment);

        // Step 2: Settlement (simulate)
        settlementService.simulateSettlement(payment);

        // Step 3: Reconcile
        reconciliationService.reconcile(payment);

        return payment;
    }
}
