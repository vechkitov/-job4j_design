create table authors (
    id serial primary key,
    name varchar(50)
);

insert into authors (name) values ('Александр Пушкин');
insert into authors (name) values ('Гоголь Н.В.');
select * from authors;

begin transaction;
insert into authors(name) values('Достоевский Ф.М.');
savepoint sp_1;
insert into authors(name) values('Есенин С.А.');
savepoint sp_2;
insert into authors(name) values('Некрасов Н.А.');
select * from authors;
rollback to sp_2;
select * from authors;
rollback to sp_1;
select * from authors;
commit;
select * from authors;
