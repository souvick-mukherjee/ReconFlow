create table reconciliation_records (
 id uuid primary key,
 payment_id uuid,
 payment_amount numeric(18,2),
 ledger_amount numeric(18,2),
 settled_amount numeric(18,2),
 variance numeric(18,2),
 status varchar(50),
 matched_at timestamp
);