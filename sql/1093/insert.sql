insert into role(name) values('user');
insert into role(name) values('advanced user');
insert into role(name) values('admin');

insert into users(name, role_id) values('ivanov', 1);
insert into users(name, role_id) values('petrov', 2);
insert into users(name, role_id) values('sverdlov', 3);

insert into rules(name) values('создание');
insert into rules(name) values('чтение');
insert into rules(name) values('изменение');
insert into rules(name) values('удаление');
insert into rules(name) values('администрирование');

insert into role_rules(role_id, rules_id) values(1, 2);
insert into role_rules(role_id, rules_id) values(2, 1);
insert into role_rules(role_id, rules_id) values(2, 2);
insert into role_rules(role_id, rules_id) values(2, 3);
insert into role_rules(role_id, rules_id) values(2, 4);
insert into role_rules(role_id, rules_id) values(3, 5);

insert into category(name) values('обычная');
insert into category(name) values('срочная');

insert into state(name) values('новая');
insert into state(name) values('одобрена');
insert into state(name) values('выполнена');
insert into state(name) values('отменена');

insert into item(item_date, item_number, user_id, category_id, state_id) 
values(CURRENT_TIMESTAMP, 'AA-0001', 2, 1, 1);

insert into comments(comment, item_id) values('доставить после обеда', 1);
