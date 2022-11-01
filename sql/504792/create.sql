create table students (
    id serial primary key,
    name varchar(50)
);

create table authors (
    id serial primary key,
    name varchar(50)
);

create table books (
    id serial primary key,
    name varchar(200),
    author_id integer references authors(id)
);

create table orders (
    id serial primary key,
    active boolean default true,
    book_id integer references books(id),
    student_id integer references students(id)
);

-- Выбрать заказы с автором, у которого больше всего книг.
-- Вывести данные заказа: имя студента, наименование книги, имя автора.
create or replace view orders_with_author_who_has_most_books as (
    with max_amount_author as (
        select a.id, a.name
        from books as b
        join authors as a on a.id = b.author_id
        group by a.id, a.name
        having count(*) = (
            select max(amount) from (
                select count(*) as amount
                from books
                group by author_id
            ) as amount
        )
    )
    select s.name as student, b.name as book, maa.name as author
    from orders as o
    join books as b on b.id = o.book_id
    join max_amount_author as maa on maa.id = b.author_id
    join students as s on s.id = o.student_id
);
