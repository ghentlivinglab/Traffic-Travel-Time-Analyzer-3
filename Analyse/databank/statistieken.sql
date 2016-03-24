use vop;

/*Gemiddelde vertraging voor een traject binnen een bepaald interval */
select avg(reistijd-traj.optimale_reistijd) from metingen
join trajecten traj on traj.id = traject_id
where timestamp between DATE_SUB(NOW(), INTERVAL 7 day) and NOW() and reistijd is not null;

/*Gemiddelde vertraging per traject binnen een bepaald interval */
select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging
from metingen m1
join metingen m2 on m1.traject_id = m2.traject_id
join trajecten traj on traj.id = m2.traject_id
where m1.timestamp between DATE_SUB(NOW(), INTERVAL 4 day) and NOW() and m1.reistijd is not null
group by m1.traject_id;


/*Gemiddelde vertraging per traject voor een bepaalde provider binnen een bepaald interval */
select m1.traject_id, traj.naam, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging
from metingen m1
join metingen m2 on m1.traject_id = m2.traject_id
join trajecten traj on traj.id = m2.traject_id
where m1.timestamp between DATE_SUB(NOW(), INTERVAL 72 hour) and NOW() and m1.reistijd is not null
and m1.provider_id = 9 group by m1.traject_id;

/*Gemiddelde vertraging voor alle trajecten binnen een bepaald interval */
select avg(y.gemiddelde_vertraging) avg_vertraging from (
	select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) gemiddelde_vertraging
	from metingen m1
	join metingen m2 on m1.traject_id = m2.traject_id
	join trajecten traj on traj.id = m2.traject_id
	where m1.timestamp between DATE_SUB(NOW(), INTERVAL 1 hour) and NOW() and m1.reistijd is not null
	group by m1.traject_id
) y;