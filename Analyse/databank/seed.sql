#Maak tabel 'Provider' leeg en vul ze op met de providers uit de databronnen file
delete from vop.Provider;
insert into vop.Provider(naam) values(
	'Waze',
    'TomTom',
    'Here',
    'Coyote',
    'Google Maps',
    'Here'
);

#Maak de tabel 'Traject' leeg en vul ze op met de trajecten uit de file Analyse_van_reistijdinfo_in_en_rond_Gent_PM_v2.pdf
delete from vop.Traject;
insert into vop.Traject(letter,naam,lengte,is_active) values
(
);