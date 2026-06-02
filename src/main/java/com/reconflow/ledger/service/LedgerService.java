package com.reconflow.ledger.service;

import com.reconflow.event.PaymentCreatedEvent;
import com.reconflow.ledger.model.LedgerEntry;

public interface LedgerService {
    public LedgerEntry createEntryFromEvent(PaymentCreatedEvent event);
}
