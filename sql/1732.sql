create table type(
    id serial primary key,
    name text
);
create table product(
    id serial primary key,
    name text,
    type_id int references type(id),
    expired_date date,
    price numeric(10, 2)
);

insert into type(name) values('сыр'), ('десерт'), ('молоко');
insert into product(name, type_id, expired_date, price) values
('Сыр плавленый', 1, '2022.07.03', 105),
('Сыр моцарела', 1, '2022.07.09', 450),
('Сыр сливочный', 1, '2022.07.03', 440),
('Сыр сливочный', 1, '2022.07.03', 440),
('Сыр творожный', 1, '2022.07.04', 130),
('Шоколадное мороженое', 2, '2022.07.09', 40),
('Сливочное мороженое', 2, '2022.06.09', 35.5),
('Молоко топленое', 3, '2022.07.06', 88),
('Мороженое большое', 2, '2022.08.19', 450)
;

/*получение всех продуктов с типом "СЫР"*/
select p.name as product, t.name as type
from product as p
join type as t on t.id = p.type_id
where t.name = 'сыр';

/*получения всех продуктов, у кого в имени есть слово "мороженое"*/
select * from product where name like '%мороженое%';

/*все продукты, срок годности которых уже истек*/
select * from product where expired_date < current_date;

/*самый дорогой продукт*/
/*вариант 1*/
select * from product where price = (select max(price) from product);
/*вариант 2*/
select p.* 
from product as p
join (select max(price) as price from product) as pr on pr.price = p.price;

/* для каждого типа количество продуктов к нему принадлежащих. В виде имя_типа, количество*/
select t.name, count(p.id)
from type as t
join product as p on p.type_id = t.id
group by t.name;

/*получение всех продуктов с типом "СЫР" и "МОЛОКО"*/
select p.name as product, t.name as type
from product as p
join type as t on t.id = p.type_id
where t.name = 'сыр' or t.name = 'молоко';

/*тип продуктов, которых осталось меньше 5 штук*/
select t.name
from type as t
join product as p on p.type_id = t.id
group by t.name
having count(p.id) < 5;

/*Вывести все продукты и их тип.*/
select p.id, p.name as product, t.name as type, p.expired_date, p.price
from product as p
join type as t on t.id = p.type_id;
