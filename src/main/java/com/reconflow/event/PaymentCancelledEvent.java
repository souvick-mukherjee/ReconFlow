package com.reconflow.event;

import java.util.UUID;

public record PaymentCancelledEvent(
        UUID paymentId,
        String reason
) {
}
