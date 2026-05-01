package com.reconflow.reconciliation.model;

public enum ReconciliationStatus {
    MISSING_LEDGER,
    MISSING_SETTLEMENT,
    MATCHED,
    AMOUNT_MISMATCH
}
