package com.reconflow.payment.service;

import com.reconflow.payment.dto.CreatePaymentRequest;
import com.reconflow.payment.model.Payment;

public interface PaymentService {

    public Payment createPayment(CreatePaymentRequest request);
}
