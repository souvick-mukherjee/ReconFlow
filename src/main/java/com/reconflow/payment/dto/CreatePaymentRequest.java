package com.reconflow.payment.dto;

import java.math.BigDecimal;

public record CreatePaymentRequest(String merchantId,
                                   BigDecimal amount,
                                   String currency) {
}
