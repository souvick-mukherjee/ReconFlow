package com.reconflow.outbox.publisher;

import com.reconflow.common.event.PaymentCreatedEvent;
import com.reconflow.outbox.model.OutboxEvent;
import com.reconflow.outbox.model.OutboxStatus;
import com.reconflow.outbox.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000) // Run every 5 seconds
    @Transactional
    public void publishEvents() {
        List<OutboxEvent> events =
                outboxRepository.findByStatus(
                        OutboxStatus.PENDING
                );
        for (OutboxEvent event : events) {
            try {
                PaymentCreatedEvent payload = objectMapper.readValue(event.getPayload(), PaymentCreatedEvent.class);
                kafkaTemplate.send("payments.created", payload);
                event.setStatus(OutboxStatus.PUBLISHED);
                event.setPublishedAt(LocalDateTime.now());
                outboxRepository.save(event);
            } catch (Exception ex) {
                log.error("Failed to publish outbox event {}", event.getId(), ex);
                event.setStatus(OutboxStatus.FAILED);
                outboxRepository.save(event);
            }
        }

    }
}
