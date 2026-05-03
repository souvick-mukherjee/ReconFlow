package com.reconflow.reconciliation.model;

public enum ReconciliationStatus {
    MATCHED,
    MATCHED_WITH_TOLERANCE,
    AMOUNT_MISMATCH,
    MISSING_LEDGER,
    MISSING_SETTLEMENT,
    UNDER_REVIEW
}
