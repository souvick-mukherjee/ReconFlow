package com.reconflow.reconciliation.consumer;

import com.reconflow.common.constants.KafkaTopics;
import com.reconflow.event.LedgerCreatedEvent;
import com.reconflow.payment.model.Payment;
import com.reconflow.payment.repository.PaymentRepository;
import com.reconflow.reconciliation.service.ReconciliationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LedgerEventConsumer {

    private final ReconciliationService reconciliationService;
    private final PaymentRepository paymentRepository;

    @KafkaListener(topics = KafkaTopics.LEDGER_CREATED, groupId = "recon-group")
    public void consume(LedgerCreatedEvent event) {
        Payment payment = paymentRepository.findById(event.paymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found for ID: " + event.paymentId()));

        reconciliationService.reconcile(payment);
    }
}
