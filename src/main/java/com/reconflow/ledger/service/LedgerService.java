package com.reconflow.ledger.service;

import com.reconflow.common.event.PaymentCreatedEvent;
import com.reconflow.ledger.model.LedgerEntry;
import com.reconflow.payment.model.Payment;

public interface LedgerService {
    public LedgerEntry createEntryFromEvent(PaymentCreatedEvent event);
}
