create table engine (
    id serial primary key,
    serial_number int,
    power numeric(6, 1),
    number_of_cylinders int,
    type text
);
create table auto (
    id serial primary key,
    brand text,
    model text,
    engine_id int references engine(id) unique,
    color text,
    number_of_doors int,
    type text
);

insert into engine(serial_number, power, number_of_cylinders, type)
values(123456, 100.5, 4, 'бензин');
insert into engine(serial_number, power, number_of_cylinders, type)
values(2334567, 190, 8, 'дизель');

insert into auto (brand, model, engine_id, color, number_of_doors, type)
values('Ford', 'Fiesta', 1, 'white', 5, 'хэтчбек');
insert into auto (brand, model, engine_id, color, number_of_doors, type)
values('Ford', 'Explorer', 2, 'black', 5, 'внедорожник');

select a.*, e.*
from auto as a
join engine as e on e.id = a.engine_id
;
select a.brand as марка, a.model as модель, e.power as "мощность, л.с."
from auto as a
join engine as e on e.id = a.engine_id
;
select a.brand as марка, a.model as модель, a.type as "тип кузова", e.type as "тип двигателя"
from auto as a
join engine as e on e.id = a.engine_id
;
