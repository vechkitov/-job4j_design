create table devices(
    id serial primary key,
    name varchar(255),
    price float
);
create table people(
    id serial primary key,
    name varchar(255)
);
create table devices_people(
    id serial primary key,
    device_id int references devices(id),
    people_id int references people(id)
);

insert into devices(name, price) values ('телефон', 10000), ('фитнес-браслет', 4000), ('планшет', 18000);
insert into people(name) values ('Паша'), ('Маша'), ('Глаша');
insert into devices_people(device_id, people_id) values (1, 1), (2, 1), (3, 1);
insert into devices_people(device_id, people_id) values (1, 2), (2, 2);
insert into devices_people(device_id, people_id) values (2, 3);

/*вывести среднюю цену устройств*/
select avg(d.price) 
from devices_people dp
join devices d on d.id = dp.device_id
;
/*вывести для каждого человека среднюю цену его устройств*/
select p.name, avg(d.price) 
from devices_people dp
join devices d on d.id = dp.device_id
join people p on p.id = dp.people_id
group by p.name
;
/*средняя стоимость устройств должна быть больше 5000*/
select p.name, avg(d.price) 
from devices_people dp
join devices d on d.id = dp.device_id
join people p on p.id = dp.people_id
group by p.name
having avg(d.price) > 5000
;
