create table message (
    id serial primary key,
    send_date timestamp,
    is_sent boolean,
    payload json
)
;
insert into message (send_date, is_sent, payload)
values ('2022-06-27 10:00:00', false, '{"greeting": "Hi!"}')
;
update message
set payload = '{"greeting": "Hi there!"}'
;
delete from message
;
