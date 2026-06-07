package com.reconflow.payment.consumer;

import com.reconflow.common.constants.KafkaTopics;
import com.reconflow.event.PaymentCancelledEvent;
import com.reconflow.payment.model.Payment;
import com.reconflow.payment.model.PaymentStatus;
import com.reconflow.payment.repository.PaymentRepository;
import com.reconflow.saga.service.SagaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCancelledConsumer {

    private final PaymentRepository paymentRepository;
    private final SagaService sagaService;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_CANCELLED
    )
    @Transactional
    public void consume(PaymentCancelledEvent event) {
        Payment payment = paymentRepository.findById(event.paymentId()).orElseThrow();
        payment.setStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);
        sagaService.compensate(event.paymentId());
    }
}
