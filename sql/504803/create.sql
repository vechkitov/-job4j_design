create table products (
    id serial primary key,
    name varchar(50),
    producer varchar(50),
    count integer default 0,
    price integer
)
;
--1)  Триггер должен срабатывать после вставки данных, для любого товара и просто насчитывать налог на товар
--(нужно прибавить налог к цене товара). Действовать он должен не на каждый ряд, а на запрос (statement уровень)
create or replace function add_tax_all()
    returns trigger as
$$
    BEGIN
        update products
        set price = price * 1.2
        where id = (select id from inserted);
        return new;
    END;
$$
LANGUAGE 'plpgsql'
;
create trigger add_tax_all_after
    after insert on products
    referencing new table as inserted
    for each statement
    execute procedure add_tax_all()
;

--2) Триггер должен срабатывать до вставки данных и насчитывать налог на товар (нужно прибавить налог
--к цене товара). Здесь используем row уровень.
create or replace function add_tax_row()
    returns trigger as
$$
    BEGIN
        new.price = new.price * 1.2;
        return new;
    END;
$$
LANGUAGE 'plpgsql'
;
create trigger add_tax_row_before
    before insert
    on products
    for each row
    execute procedure add_tax_row();
;

-- 3) Создайте таблицу history_of_price.
-- Нужно написать триггер на row уровне, который при вставке продукта в таблицу products, будет
-- заносить имя, цену и текущую дату в таблицу history_of_price.
create table history_of_price (
    id serial primary key,
    name varchar(50),
    price integer,
    date timestamp
)
;
create or replace function add_history_of_price()
    returns trigger as
$$
    BEGIN
        insert into history_of_price (name, price, date)
        values (new.name, new.price, CURRENT_TIMESTAMP);
        return new;
    END;
$$
LANGUAGE 'plpgsql'
;
create trigger add_history_of_price
    after insert
    on products
    for each row
    execute procedure add_history_of_price();
;
