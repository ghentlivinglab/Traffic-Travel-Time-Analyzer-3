#Projectgroep 3 VOP : verkeer project repository
###Productieomgeving
http://verkeer-3.vop.tiwi.be/verkeer3gui/

username: root

wachtwoord: dKkvn0DuP5

###Installatie project op geconfigureerde omgeving
Het project vereis een linux distributie met daarop glassfish en mariadb of MySQL geconfigureerd. Voor detail omtrend het opzetten hiervan wordt verwezen naar de uitgebreidde installatiehandleiding in het projectdossier. Hier wordt enkel de installatie en configuratie van het verkeersplatform zelf behandeld.

Alle bestanden die nodig zijn voor configuratie zitten in de map deployment. Het script dat in deze map aanwezig is kan ook gebruikt worden om deze bestanden op de server binnen te halen. Alternatief kan het commando *"svn export https://github.ugent.be/iii-vop2016/verkeer-3/trunk/deployment --force --username [GIT\_USERNAME] --password [GIT\_WACHTWOORD]"* gebruikt worden om op de huidige locatie de folder deployment met de nodige bestanden te downloaden. Hiervoor is subversion nodig. 

1. Configuratie van de databank
    1. Het bestand *"Volledige database.sql"* bevat de querry om de databank op te bouwen en aan te vullen. Er is een versie die compatibel is met MySQL en een versie voor MariaDB. Deze querry kan op de geprefereerde manier uitgevoerd worden (terminal of gui zoals  MySQL workbench.
2. Installatie van de webapplicatie
    1. De webapp kan geïnstalleerd worden door middel van het *"deployment.sh"* script. Op de server kan het commando *"bash deploymentsh [GIT\_USERNAME] [GIT\_WACHTWOORD]"* uitgevoerd worden op de laatste versie op te halen en te deployen op glassfish.
    2. Het script zal vervolgens om de gebruikersnaam en wachtwoord van de glassfish admin in te geven.
    2. Bij de eerste installatie kan het script *"eerste deployment.sh"* analoog gebruikt worden.
    3. Handmatig kan het bestand *"verkeer3gui.war"* ook op een persoonlijk geprefereerde manier geïnstalleerd worden.


###Groepsleden

- De Bock Jelle
- Floré Brent
- Stofferis Jeroen
- Vandemoortele Simon 
- Vervenne Jan

### Wegwijs in deze repository
Deze repository bestaat uit 3 grote delen:
* **Analyse** 
In dit gedeelte vindt u alle analyse gerelateerde informatie. De map bevat steeds de laatste versie van het projectdossier. Een ander belangrijk onderdeel van dit gedeelte vindt u onder de submap databank. Hierin worden immers de laatste versies van de databankstructuur en de initiële populatiescripts bewaard. In deze map vindt u ook een Mysql Workbench File (.mwb). In dit programma werd namelijk het ontwerp van de databank uitgetekend. 

Tot slot bevat de databank submap ook een tool map waarin een C++ programmaatje zit dat ons bijstond bij het opstellen van de seed file. Dit tooltje was vooral van nut tijdens het populeren van de trajecten tabel, omdat deze trajecten in een pdf file stonden. Het volstond zo om de tabel uit de pdf te kopiëren in een kladbestand waaruit het C++ programmaatje de insert records genereerde.

* **Code** 
In dit gedeelte vindt u de source code van wat men nog het best als de "backend" kan omschrijven. In dit project worden namelijk alle databank lees-, schrijf- en wijzigfunctionaliteit geïmplementeerd. Verder wordt in dit project ook het scrapen van de verschillende providers geïmplementeerd. Een deel van de data kan door middel van een API opgehaald worden, bij een ander deel van de providers werden minder voor de hand liggende scrapers geïmplementeerd. Er was immers noodzaak aan zulke scrapers, die webpagina's afscannen op zoek naar bruikbare data omdat sommige providers nu eenmaal geen API voorzien. Bij scraping werd in het merendeel van de situaties gekozen voor Perl, in een enkele uitzondering werd Python (her)gebruikt. Alle data (API- en scraperdata) wordt uiteindelijk samengebracht in de Java code die uiteindelijk de databank vult met deze meetgegevens van de verschillende providers.

Ook nog interessant: het project is een Maven Java project. Dit wil vooral zeggen dat dependencies (externe klasses waarop onze code beroep doet) automatisch opgehaald en toegevoegd aan het project worden. Tot slot worden applicatie instellingen bewaard in een apart .config bestand, zodat ook de backend zeer draagbaar wordt.

* **De webapplicatie** (TODO)
Je bent helemaal nergens met een backend als je geen frontend hebt. De webapplicatie is waarschijnlijk een opstap naar meer geavanceerde toepassingen. De webapplicatie dient vooral om te tonen wat er mogelijk is met de huidige backend. Het bied (voorlopig) de basisfunctionaliteit. Het geeft een visueel inzicht in de data die op dat moment in onze databank bevindt. Het project zelf is een Spring project waarin dependencies ook door Maven beheerd worden. Doordat voor Spring gekozen werd is de webapplicatie dus ook nog vlot uitbreidbaar of wijzigbaar.


###Opdrachtomschrijving
Het Mobiliteitsbedrijf van de stad gent is sinds 2014 bezig met het opzetten van een regionaal verkeerscentrum. Het is de bedoeling dat op termijn het verkeer in de regio constant gemonitord wordt, op semi-automatische basis op normale werkdagen en bemand tijdens piekmomenten en evenementen. Tijdens de week is het de bedoeling dat onverwachte incidenten, calamiteiten of significante verhogingen van de reistijden automatisch gesignaleerd worden aan de verantwoordelijke, die dan de nodige acties kan ondernemen. De gegevens zouden ook constant beschikbaar zijn voor het publiek via een website, sociale media en open data. Op die manier kunnen mensen de beste route en het beste moment kiezen om hun verplaatsingen te maken in de regio.
Momenteel loopt al een proefproject via het platform van de Gentse Start-up Waylay. Gegevens worden automatisch verwerkt, tweets en sms’en worden uitgestuurd als er relevante informatie beschikbaar is voor de weggebruiker in en rond Gent. Het proefproject kan geraadpleegd worden op https://twitter.com/VerkeerGentB en www.verkeer.gent 
