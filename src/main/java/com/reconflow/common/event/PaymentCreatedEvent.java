package com.reconflow.common.event;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreatedEvent(
        UUID paymentId,
        BigDecimal amount,
        String currency
) {}
