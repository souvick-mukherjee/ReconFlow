create table ledger_entries (
    id uuid primary key,
    payment_id uuid,
    debit_account varchar(50),
    credit_account varchar(50),
    amount numeric(18,2),
    entry_status varchar(30),
    created_at timestamp
);