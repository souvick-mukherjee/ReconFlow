package com.reconflow.payment.service;

import com.reconflow.payment.model.LedgerEntry;
import com.reconflow.payment.model.Payment;
import org.springframework.stereotype.Service;

@Service
public interface LedgerService {
    public LedgerEntry createEntry(Payment payment);
}
