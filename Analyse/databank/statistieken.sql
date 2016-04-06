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

/*Ratio traject.reistijd/avg(meting.reistijd) per traject, provider */
select traject_id, provider_id, providers.naam, trajecten.optimale_reistijd, avg(reistijd), avg(reistijd)/trajecten.optimale_reistijd as ratio from metingen
inner join providers on providers.id = metingen.provider_id
inner join trajecten on trajecten.id = metingen.traject_id
where reistijd is not null and reistijd >= 0
group by provider_id, traject_id
ORDER BY `ratio`  DESC;

/*Verschil tussen min- en max-ratio per traject */
select r.traject_id, max(r.ratio) - min(r.ratio) ratiodiff
from (
	select traject_id, provider_id, providers.naam, trajecten.optimale_reistijd, avg(reistijd), avg(reistijd)/trajecten.optimale_reistijd as ratio from metingen
	inner join providers on providers.id = metingen.provider_id
	inner join trajecten on trajecten.id = metingen.traject_id
	where reistijd is not null and reistijd >= 0
	group by provider_id, traject_id
) r
group by r.traject_id;