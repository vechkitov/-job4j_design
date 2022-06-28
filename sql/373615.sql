/* one to many */
create table street (
    id serial primary key,
    name varchar(255)
)
;
create table building (
    id serial primary key,
    building_number int,
    street_id int references street(id)
)
;

/* many to many */
create table invoice (
    id serial primary key,
    invoice_date timestamp,
    invoice_number varchar(255)
)
;
create table nomenclature (
    id serial primary key,
    name varchar(255)
)
;
create table invoice_nomenclature (
    id serial primary key,
    invoice_id int references invoice(id),
    nomenclature_id int references nomenclature(id)
)
;

/*one to one*/
create table engine (
    id serial primary key,
    serial_number int
)
;
create table auto (
    id serial primary key,
    name varchar(255),
    engine_id int references engine(id) unique
)
;
