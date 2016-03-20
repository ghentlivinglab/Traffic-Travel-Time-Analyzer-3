In deze map zitten alle bestanden om de database te importeren in MySQL met de correcte trajecten en waypoints.

De database kan op 2 manieren ingeladen worden:
 - Structuur en daarna trajecten en waypoints
	1. 	Importeer "DatabankCreate.sql"
	2. 	Importeer "Trajecten en Waypoints.sql"
 - Alles in één keer
		Importeer "Volledige database.sql"
		
Merk op: 
het bestand "Trajecten en Waypoints.sql" kan dynamisch opgemaakt worden door in de klasse CoyoteScraper de functie genereerSQL te runnen. Daarna komt een bestand "tempsql.sql" in de map "code" terecht. Dit bestand bevat alle trajecten en waypoints.