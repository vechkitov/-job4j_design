select p.name person, c.name company
from person p
join company c on c.id = p.company_id and c.id <> 5
;
