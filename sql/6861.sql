select * from fauna where name like '%fish%'; /*имя name содержит подстроку fish*/
select * from fauna where avg_age > 10000 and avg_age <= 21000; /*сред. продолжительность жизни находится в диапазоне 10 000 и 21 000*/
select * from fauna where discovery_date is null; /*дата открытия не известна (null)*/
select * from fauna where discovery_date < '01.01.1950'; /*дата открытия раньше 1950 года*/
