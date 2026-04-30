create table payments (
id uuid primary key,
merchant_id varchar(50),
amount numeric(18,2),
currency varchar(10),
status varchar(30),
payment_reference varchar(100),
created_at timestamp
);