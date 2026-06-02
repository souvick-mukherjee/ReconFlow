package com.reconflow.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventEnvelope<T>(
        UUID eventId,
        LocalDateTime createdAt,
        String eventType,
        T payload
) { }
