package com.reconflow.saga.controller;

import com.reconflow.saga.model.SagaInstance;
import com.reconflow.saga.repository.SagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/api/sagas")
@RequiredArgsConstructor
public class SagaController {
    private final SagaRepository sagaRepository;
    @GetMapping("/{paymentId}")
    public SagaInstance getSaga(@PathVariable UUID paymentId) {
        return sagaRepository
                .findByPaymentId(paymentId)
                .orElseThrow();
    }
}
