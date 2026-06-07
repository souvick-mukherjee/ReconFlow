package com.reconflow.saga.service;

import java.util.UUID;

public interface SagaService {

    public void startSaga(UUID paymentId);

    public void markLedgerCompleted(UUID paymentId);

    public void complete(UUID paymentId);

    public void compensate(UUID paymentId);
}
