package com.reconflow.payment.service.impl;

import com.reconflow.event.EventEnvelope;
import com.reconflow.event.PaymentCreatedEvent;
import com.reconflow.ledger.service.LedgerService;
import com.reconflow.outbox.model.OutboxEvent;
import com.reconflow.outbox.model.OutboxStatus;
import com.reconflow.outbox.repository.OutboxRepository;
import com.reconflow.payment.dto.CreatePaymentRequest;
import com.reconflow.payment.model.Payment;
import com.reconflow.payment.model.PaymentStatus;
import com.reconflow.payment.repository.PaymentRepository;
import com.reconflow.payment.service.PaymentService;
import com.reconflow.reconciliation.service.ReconciliationService;
import com.reconflow.saga.service.SagaService;
import com.reconflow.settlement.service.SettlementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;
    private final OutboxRepository outboxRepository;
    private final LedgerService ledgerService;
    private final SettlementService settlementService;
    private final ReconciliationService reconciliationService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SagaService sagaService;

    @Override
    @Transactional
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
        sagaService.startSaga(payment.getId());

        // Step 1: Ledger
//        ledgerService.createEntry(payment);

        // Step 2: Settlement (simulate)
//        settlementService.simulateSettlement(payment);

        // Step 3: Reconcile
//        reconciliationService.reconcile(payment);

        PaymentCreatedEvent payload = new PaymentCreatedEvent(
                payment.getId(),
                payment.getAmount(),
                payment.getCurrency()
        );
//        kafkaTemplate.send("payments.created", event);
        EventEnvelope<PaymentCreatedEvent> eventEnvelope = new EventEnvelope<>(
                UUID.randomUUID(),
                LocalDateTime.now(),
                "PaymentCreatedEvent",
                payload
        );

        OutboxEvent outboxEvent = OutboxEvent.builder()
                .id(UUID.randomUUID())
                .aggregateType("PAYMENT")
                .aggregateId(payment.getId().toString())
                .eventType("PaymentCreatedEvent")
                .payload(objectMapper.writeValueAsString(eventEnvelope))
                .status(OutboxStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        outboxRepository.save(outboxEvent);
        return payment;
    }
}
