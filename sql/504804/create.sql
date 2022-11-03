create or replace procedure p_delete_data(p_id integer)
language 'plpgsql'
as $$
    BEGIN
        delete from products where id = p_id;
    END
$$;

create or replace function f_delete_data(p_id integer)
returns boolean
language 'plpgsql'
as
$$
    DECLARE
        amount integer;
    BEGIN
        select into amount count(*) from products where id = p_id;
        if amount = 0 then
            return false;
        end if;
        delete from products where id = p_id;
        return true;
    END;
$$;

--call p_delete_data(8);
--select f_delete_data(6);
