create table processed_events (
    id uuid primary key,
    consumer_group varchar(100),
    processed_at timestamp
);