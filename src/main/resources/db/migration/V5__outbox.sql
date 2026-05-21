create table outbox_events (
 id uuid primary key,
 aggregate_type varchar(100),
 aggregate_id varchar(100),
 event_type varchar(100),
 payload jsonb,
 status varchar(30),
 created_at timestamp,
 published_at timestamp
);