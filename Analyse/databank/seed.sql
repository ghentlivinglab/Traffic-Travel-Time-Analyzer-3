#Maak tabel 'Provider' leeg en vul ze op met de providers uit de databronnen file
use vop;
delete from vop.providers;
insert into vop.providers(naam,is_active) values
	('Waze',1),
    ('TomTom',1),
    ('Here',1),
    ('Coyote',1),
    ('Google Maps',1),
    ('Here',1)
;

#Maak de tabel 'Traject' leeg en vul ze op met de trajecten uit de file Analyse_van_reistijdinfo_in_en_rond_Gent_PM_v2.pdf
delete from vop.trajecten;
insert into vop.trajecten(letter,naam,lengte,is_active) values
	("A",'E40 (Oostende) > centrum',12176,1),
	("B",'E40 (Oostende) > centrum via Drongen',6677,1),
	("C",'E40 (Brussel) > centrum',8027,1),
	("D",'E17 (Kortrijk) > centrum',10479,1),
	("E",'E17 (Antwerpen) > centrum',8689,1),
	("F",'R4 Noordwest tegenwijzerzin',14966,1),
	("F'",'R4 Noordwest wijzerzin',15310,1),
	("G",'R4 West tegenwijzerzin',10508,1),
	("G'",'R4 West wijzerzin',10131,1),
	("H",'R4 Noordoost wijzerzin',12597,1),
	("H'",'R4 Noordoost tegenwijzerzin',12682,1),
	("I",'R4 Oost wijzerzin',17530,1),
	("I'",'R4 Oost tegenwijzerzin',19149,1),
	("J",'R40 Zuid-West wijzerzin',3719,1),
	("J'",'R40 Zuid-West tegenwijzerzin',3824,1),
	("K",'R40 Noord-West wijzerzin',1080,1),
	("K'",'R40 Noord-West tegenwijzerzin',1104,1),
	("L",'R40 Noord-Oost wijzerzin',1424,1),
	("L'",'R40 Noord-Oost tegenwijzerzin',1447,1),
	("M",'R40 Zuid-Oost wijzerzin',2112,1),
	("M'",'R40 Zuid-Oost tegenwijzerzin',2246,1),
	("N",'E40 (Oostende) > B402 via E40',6997,1),
	("N'",'E40 (Oostende) > B402 via Drongen',12264,1),
	("O",'E40 (Brussel) > B402',11493,1),
	("P",'E17 (Antwerpen) > B402 via E40',12994,1),
	("Q",'E17 (Antwerpen) > B402 via E17/R4',13675,1),
	("R",'E17 (Antwerpen) > B402 via R4',16948,1),
	("S",'E17 (Kortrijk) > B402',11294,1),
	("T",'E17 (Antwerpen) > P+R Gentbrugge',6096,1),
	("U",'E17 (Kortrijk) > P+R Gentbrugge',10473,1)
;