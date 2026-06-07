create table saga_instances (
 id uuid primary key,
 payment_id uuid,
 saga_type varchar(100),
 status varchar(50),
 current_step varchar(100),
 created_at timestamp,
 updated_at timestamp
);