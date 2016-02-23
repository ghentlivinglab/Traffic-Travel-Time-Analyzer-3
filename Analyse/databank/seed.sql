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
insert into vop.trajecten(letter,naam,lengte,is_active,start_latitude,start_longitude, end_latitude, end_longitude) values
	("A",'E40 (Oostende) > centrum',12176,1,"51.040800","3.614126","51.038736","3.736503"),
	("B",'E40 (Oostende) > centrum via Drongen',6677,1,"51.040800","3.614126","51.056190","3.694913"),
	("C",'E40 (Brussel) > centrum',8027,1,"50.990332","3.798873","51.038736","3.736503"),
	("D",'E17 (Kortrijk) > centrum',10479,1,"50.979661","3.647235","51.038736","3.736503"),
	("E",'E17 (Antwerpen) > centrum',8689,1,"51.053116","3.835141","51.038736","3.736503"),
	("F",'R4 Noordwest tegenwijzerzin',14966,1,"51.1923622","3.776722","51.0845337","3.6645412"),
	("F'",'R4 Noordwest wijzerzin',15310,1,"51.0845337","3.6645412","51.1923622","3.776722"),
	("G",'R4 West tegenwijzerzin',10508,1,"51.0845337","3.6645412","51.0134172","3.7237644"),
	("G'",'R4 West wijzerzin',10131,1,"51.0134172","3.7237644","51.0845337","3.6645412"),
	("H",'R4 Noordoost wijzerzin',12597,1,"51.1863372","3.8328552","51.0870812","3.7567234"),
	("H'",'R4 Noordoost tegenwijzerzin',12682,1,"51.0870812","3.7567234","51.1863372","3.8328552"),
	("I",'R4 Oost wijzerzin',17530,1,"51.0870812","3.7567234","51.0141461","3.7235498"),
	("I'",'R4 Oost tegenwijzerzin',19149,1,"51.0141461","3.7235498","51.0870812","3.7567234"),
	("J",'R40 Zuid-West wijzerzin',3719,1,"51.0386584","3.736564","51.0562324","3.6948395"),
	("J'",'R40 Zuid-West tegenwijzerzin',3824,1,"51.0562324","3.6948395","51.0386584","3.736564"),
	("K",'R40 Noord-West wijzerzin',1080,1,"51.0666435","3.6997318","51.0562324","3.6948395"),
	("K'",'R40 Noord-West tegenwijzerzin',1104,1,"51.0562324","3.6948395","51.0666435","3.6997318"),
	("L",'R40 Noord-Oost wijzerzin',1424,1,"51.0678503","3.7294185","51.0576452","3.7379372"),
	("L'",'R40 Noord-Oost tegenwijzerzin',1447,1,"51.0576452","3.7379372","51.0678503","3.7294185"),
	("M",'R40 Zuid-Oost wijzerzin',2112,1,"51.0559626","3.7390423","51.0386584","3.736564"),
	("M'",'R40 Zuid-Oost tegenwijzerzin',2246,1,"51.0386584","3.736564","51.0559626","3.7390423"),
	("N",'E40 (Oostende) > B402 via E40',6997,1,"51.0407547","3.6143491","51.0263077","3.6886382"),
	("N'",'E40 (Oostende) > B402 via Drongen',12264,1,"51.0407547", "3.6143491", "51.0270905", "3.6892605"),
	("O",'E40 (Brussel) > B402',11493,1,"50.9904212","3.7988663","51.0263077", "3.6886382"),
	("P",'E17 (Antwerpen) > B402 via E40',12994,1,"51.0530358","3.8353443","51.0263077","3.6886382"),
	("Q",'E17 (Antwerpen) > B402 via E17/R4',13675,1,"51.0530358","3.8353443","51.0270905","3.6892605"),
	("R",'E17 (Antwerpen) > B402 via R4',16948,1,"51.0530358","3.8353443","51.027090","3.6892605"),
	("S",'E17 (Kortrijk) > B402',11294,1,"50.9798849","3.6472034","51.0270905","3.6892605"),
	("T",'E17 (Antwerpen) > P+R Gentbrugge',6096,1,"51.0530358","3.8353443","51.035889","3.7573242"),
	("U",'E17 (Kortrijk) > P+R Gentbrugge',10473,1,"50.9798849","3.6472034","51.0345936","3.7554359")
;