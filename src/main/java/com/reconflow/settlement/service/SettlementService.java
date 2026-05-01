package com.reconflow.settlement.service;

import com.reconflow.payment.model.Payment;
import com.reconflow.settlement.model.SettlementRecord;
import org.springframework.stereotype.Service;

@Service
public interface SettlementService {
        public SettlementRecord simulateSettlement(Payment payment);
}
