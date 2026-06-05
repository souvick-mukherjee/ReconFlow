package com.reconflow.ledger.consumer;

import com.reconflow.common.constants.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentCreatedDlqConsumer {
    @KafkaListener(
            topics =
                    KafkaTopics.PAYMENTS_CREATED_DLQ,
            groupId = "dlq-group"
    )
    public void consume(String message) {

        log.error(
                "DLQ EVENT RECEIVED: {}",
                message
        );

        // future:
        // create incident
        // send alert
        // store for manual replay
    }
}
