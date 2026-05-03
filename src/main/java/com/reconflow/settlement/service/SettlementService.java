package com.reconflow.settlement.service;

import com.reconflow.payment.model.Payment;
import com.reconflow.settlement.model.SettlementRecord;

public interface SettlementService {
        public SettlementRecord simulateSettlement(Payment payment);
}
