package com.reconflow.reconciliation.controller;

import com.reconflow.reconciliation.model.ReconciliationRecord;
import com.reconflow.reconciliation.model.ReconciliationStatus;
import com.reconflow.reconciliation.repository.ReconciliationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/reconciliation")
@RequiredArgsConstructor
public class ReconciliationController {

    private final ReconciliationRepository reconciliationRepository;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ReconciliationRecord> getByPaymentId(@PathVariable UUID paymentId) {
        return reconciliationRepository.findByPaymentId(paymentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exceptions")
    public List<ReconciliationRecord> getExceptions() {
        return reconciliationRepository.findByStatusNot(ReconciliationStatus.MATCHED);
    }
}
