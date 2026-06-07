package com.reconflow.saga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "saga_instances")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SagaInstance {
    @Id
    private UUID id;

    private UUID paymentId;

    private String sagaType;

    @Enumerated(EnumType.STRING)
    private SagaStatus status;

    private String currentStep;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
