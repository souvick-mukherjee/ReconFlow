package com.reconflow.reconciliation.model;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="reconciliation_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReconciliationRecord {

    @Id
    private UUID id;

    private UUID paymentId;

    private BigDecimal paymentAmount;
    private BigDecimal ledgerAmount;
    private BigDecimal settledAmount;

    private BigDecimal variance;

    @Enumerated(EnumType.STRING)
    private ReconciliationStatus status;

    private LocalDateTime matchedAt;
}