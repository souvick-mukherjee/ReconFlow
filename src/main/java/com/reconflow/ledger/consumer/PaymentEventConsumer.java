package com.reconflow.ledger.consumer;

import com.reconflow.Idempotency.service.IdempotencyService;
import com.reconflow.common.constants.KafkaTopics;
import com.reconflow.event.EventEnvelope;
import com.reconflow.event.PaymentCreatedEvent;
import com.reconflow.ledger.service.LedgerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final LedgerService ledgerService;
    private final IdempotencyService idempotencyService;

    @KafkaListener(topics = KafkaTopics.PAYMENTS_CREATED, groupId = "ledger-group")
    @Transactional
    public void consume(EventEnvelope<PaymentCreatedEvent> envelope) {

        UUID eventId = envelope.eventId();

        // duplicate protection
        if(idempotencyService.isProcessed(eventId)) {
            log.info(
                    "Skipping duplicate event {}",
                    eventId
            );
            return;
        }

        ledgerService.createEntryFromEvent(envelope.payload());

        idempotencyService.markProcessed(eventId, "ledger-group");
    }
}
