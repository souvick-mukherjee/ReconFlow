package com.reconflow.Idempotency.service;

import java.util.UUID;

public interface IdempotencyService {

    public boolean isProcessed(UUID eventId);

    public void markProcessed(
            UUID eventId,
            String consumerGroup
    );
}
