package com.reconflow.ledger.model;

import jakarta.persistence.Entity;
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
@Table(name = "ledger_entries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LedgerEntry {

    @Id
    private UUID id;

    private UUID paymentId;

    private String debitAccount;

    private String creditAccount;

    private BigDecimal amount;

    private  EntryStatus entryStatus;

    private LocalDateTime createdAt;
}
