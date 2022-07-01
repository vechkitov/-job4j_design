create table body (
    id serial primary key,
    name text
);
create table engine (
    id serial primary key,
    name text
);
create table transmission (
    id serial primary key,
    name text
);
create table auto (
    id serial primary key,
    name text,
    body_id int references body(id),
    engine_id int references engine(id),
    transmission_id int references transmission(id)
);

insert into body(name) values('универсал'), ('седан'), ('хетчбэк');
insert into engine(name) values('1.6, бензин'), ('1.8, бензин'), ('2.0, дизель');
insert into transmission(name) values('автомат'), ('механика'), ('вариатор');
insert into auto(name, body_id, engine_id, transmission_id) values
('Лада', 2, 1, 2), ('Москвич', 2, 1, 2), ('Форд', 3, 3, 3), ('Пежо', 3, 1, 3), ('DIY', 3, null, 3);

/*1) Вывести список всех машин и все привязанные к ним детали.
Нужно учесть, что каких-то деталей машина может и не содержать.*/
select a.name авто, b.name кузов, e.name двигатель, t.name "коробка передач"
from auto a
left join body b on b.id = a.body_id
left join engine e on e.id = a.engine_id
left join transmission t on t.id = a.transmission_id
;

/*2) Вывести отдельно детали (1 деталь - 1 запрос), которые
не используются НИ в одной машине, кузова, двигатели, коробки передач.*/
select b.*
from body b
left join auto a on a.body_id = b.id
where a.id is null
;
select e.*
from engine e
left join auto a on a.engine_id = e.id
where a.id is null
;
select t.*
from transmission t
left join auto a on a.transmission_id = t.id
where a.id is null
;
