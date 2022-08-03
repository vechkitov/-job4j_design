with 
    person_amount as (
        select company_id, count(id) amount 
        from person 
        group by company_id
    ),
    max_amount as (
        select max(amount) 
        from person_amount
    )
select c.name, pa.amount
from person_amount pa
join company c on c.id = pa.company_id
where pa.amount = (select * from max_amount)
;
