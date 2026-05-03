package com.reconflow.reconciliation.service;

import com.reconflow.payment.model.Payment;
import com.reconflow.reconciliation.model.ReconciliationRecord;

public interface ReconciliationService {
        public ReconciliationRecord reconcile(Payment payment);
}
