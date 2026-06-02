package com.reconflow.event;

import java.math.BigDecimal;
import java.util.UUID;

public record LedgerCreatedEvent(
        UUID paymentId,
        BigDecimal amount
) {}
