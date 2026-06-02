package com.reconflow.Idempotency.service.impl;

import com.reconflow.Idempotency.service.IdempotencyService;
import com.reconflow.Idempotency.model.ProcessedEvent;
import com.reconflow.Idempotency.repository.ProcessedEventsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdempotencyServiceImpl implements IdempotencyService {

    private final ProcessedEventsRepository repository;

    @Override
    public boolean isProcessed(UUID eventId) {
        return repository.existsById(eventId);
    }

    @Override
    public void markProcessed(UUID eventId, String consumerGroup) {
        ProcessedEvent event = ProcessedEvent.builder()
                .eventId(eventId)
                .consumerGroup(consumerGroup)
                .processedAt(java.time.LocalDateTime.now())
                .build();
        repository.save(event);
    }
}