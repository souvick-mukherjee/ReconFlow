package com.reconflow.payment.service;

import com.reconflow.payment.dto.CreatePaymentRequest;
import com.reconflow.payment.model.Payment;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    public Payment createPayment(CreatePaymentRequest request);
}
