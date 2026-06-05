create table dlq_events (
 id uuid primary key,
 topic varchar(200),
 payload text,
 error_message text,
 created_at timestamp
);