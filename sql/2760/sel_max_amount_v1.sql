select c.name, count(p.id) amount
from person p
join company c on c.id = p.company_id
group by c.id
having count(p.id) = (
        select max(amount)
        from (
            select count(id) amount
            from person
            group by company_id
        ) as max_amount
    )
;
