package com.reconflow.payment.service;

import com.reconflow.payment.model.Payment;
import com.reconflow.payment.model.SettlementRecord;
import org.springframework.stereotype.Service;

@Service
public interface SettlementService {
        public SettlementRecord simulateSettlement(Payment payment);
}
