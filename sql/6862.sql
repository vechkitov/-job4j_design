create table departments(
    id serial primary key,
    name text
);
create table employees (
    id serial primary key,
    name text,
    departments_id int references departments(id)
);

insert into departments(name) values('администрация'), ('бухгалтерия'), ('продажи'), ('производство'), ('IT');
insert into employees(name, departments_id)
values ('Абрикосов', 4), ('Бубнов', 2), ('Васин', 1), ('Гусев', 4), ('Дворжецкий', 3), ('Ежов', null), ('Жулин', 4);

select * from employees e
left join departments d on d.id = e.departments_id
;
select * from employees e
right join departments d on d.id = e.departments_id
;
select * from employees e
full join departments d on d.id = e.departments_id
;
select * from employees
cross join departments
;

/*3. Используя left join найти департаменты, у которых нет работников*/
select d.*
from departments d
left join employees e on e.departments_id = d.id
where e.id is null
;

/*4. Используя left и right join написать запросы, которые давали бы одинаковый 
результат (порядок вывода колонок в эти запросах также должен быть идентичный).*/
select e.*, d.* from employees e
left join departments d on d.id = e.departments_id
;
select e.*, d.* from departments d
right join employees e on e.departments_id = d.id
;

/*Создать таблицу teens с атрибутами name, gender и заполнить ее. 
Используя cross join составить все возможные разнополые пары*/
create table teens(
    id serial primary key,
    name text,
    gender text
);
insert into teens(name, gender) values
('Алекс', 'муж'), ('Борис', 'муж'), ('Влад', 'муж'), ('Галя', 'жен'), ('Даша', 'жен');

select t1.name, t2.name from teens t1
cross join teens t2
where t1.gender <> t2.gender
and t1.gender = 'муж'
;
