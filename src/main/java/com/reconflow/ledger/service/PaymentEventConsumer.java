package com.reconflow.ledger.service;

import com.reconflow.common.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final LedgerService ledgerService;

    @KafkaListener(topics = "payments.created", groupId = "ledger-group")
    public void consume(PaymentCreatedEvent event) {
        ledgerService.createEntryFromEvent(event);
    }
}
