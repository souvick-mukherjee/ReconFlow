create table settlement_records (
    id uuid primary key,
    payment_id uuid,
    settled_amount numeric(18,2),
    settlement_date timestamp,
    source varchar(30),
    status varchar(30)
);
