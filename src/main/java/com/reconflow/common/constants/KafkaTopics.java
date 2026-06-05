package com.reconflow.common.constants;

public class KafkaTopics {

    public static final String PAYMENTS_CREATED =
            "payments.created";

    public static final String PAYMENTS_CREATED_DLQ =
            "payments.created.dlq";

    public static final String LEDGER_CREATED =
            "ledger.created";

    public static final String LEDGER_CREATED_DLQ =
            "ledger.created.dlq";

    private KafkaTopics() {}
}
