package com.reconflow.payment.service;

import com.reconflow.payment.dto.CreatePaymentRequest;
import com.reconflow.payment.model.Payment;
import com.reconflow.payment.model.PaymentStatus;
import com.reconflow.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment createPayment(CreatePaymentRequest request) {
        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .merchantId(request.merchantId())
                .amount(request.amount())
                .currency(request.currency())
                .status(PaymentStatus.CAPTURED)
                .paymentReference(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }
}
