create table message (
    id serial primary key,
    send_date timestamp,
    is_sent boolean,
    payload json
);
