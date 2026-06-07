package com.reconflow.saga.service.impl;

import com.reconflow.saga.model.SagaInstance;
import com.reconflow.saga.model.SagaStatus;
import com.reconflow.saga.repository.SagaRepository;
import com.reconflow.saga.service.SagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SagaServiceImpl implements SagaService {
    public final SagaRepository sagaRepository;

    @Override
    public void startSaga(UUID paymentId) {
        SagaInstance saga = SagaInstance.builder()
                .id(UUID.randomUUID())
                .paymentId(paymentId)
                .sagaType("PAYMENT_RECON")
                .status(SagaStatus.STARTED)
                .currentStep("PAYMENT_CREATED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        sagaRepository.save(saga);
    }

    @Override
     public void markLedgerCompleted(UUID paymentId) {
         SagaInstance saga =
                 sagaRepository.findByPaymentId(paymentId)
                         .orElseThrow();

         saga.setCurrentStep("LEDGER_CREATED");
         saga.setStatus(SagaStatus.IN_PROGRESS);
         saga.setUpdatedAt(LocalDateTime.now());

         sagaRepository.save(saga);
     }

     @Override
     public void complete(UUID paymentId) {
         SagaInstance saga =
                 sagaRepository.findByPaymentId(paymentId)
                         .orElseThrow();

         saga.setStatus(SagaStatus.COMPLETED);
         saga.setCurrentStep("RECONCILIATION_COMPLETED");

         sagaRepository.save(saga);
     }

     @Override
     public void compensate(UUID paymentId) {
            SagaInstance saga =
                    sagaRepository.findByPaymentId(paymentId)
                            .orElseThrow();

            saga.setStatus(SagaStatus.COMPENSATED);
            saga.setCurrentStep("PAYMENT_COMPENSATED");
            saga.setUpdatedAt(LocalDateTime.now());

            sagaRepository.save(saga);
     }

}
