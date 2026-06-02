package com.reconflow.ledger.service.impl;

import com.reconflow.common.constants.KafkaTopics;
import com.reconflow.event.LedgerCreatedEvent;
import com.reconflow.event.PaymentCreatedEvent;
import com.reconflow.ledger.model.LedgerStatus;
import com.reconflow.ledger.model.LedgerEntry;
import com.reconflow.ledger.repository.LedgerRepository;
import com.reconflow.ledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public LedgerEntry createEntryFromEvent(PaymentCreatedEvent event) {
        LedgerEntry entry = LedgerEntry.builder()
                .id(UUID.randomUUID())
                .paymentId(event.paymentId())
                .debitAccount("CUSTOMER_WALLET")
                .creditAccount("MERCHANT_PAYABLE")
                .amount(event.amount())
                .entryStatus(LedgerStatus.POSTED)
                .createdAt(LocalDateTime.now())
                .build();

        LedgerEntry saved = ledgerRepository.save(entry);

        // publish next event
        kafkaTemplate.send(KafkaTopics.LEDGER_CREATED,
                new LedgerCreatedEvent(event.paymentId(), event.amount()));

        return saved;
    }
}
