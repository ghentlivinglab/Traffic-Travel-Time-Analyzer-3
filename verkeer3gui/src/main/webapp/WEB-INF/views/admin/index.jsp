<jsp:include page="/WEB-INF/views/partial/header.jsp"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:out value="resources/css/bootstrap3/bootstrap-switch.css"/>" rel="stylesheet">
<script src="<c:out value="resources/js/admin.js"/>"></script>
<h1 class="inline-h">Admin panel</h1>

<div id="scraperAlert" class="alert alert-success" role="alert">
    <strong>Well done!</strong> You successfully read this important alert message.
</div>

<ul class="nav nav-tabs admin-nav-tabs">
    <li role="presentation" class="active"><a href="<c:url value="/admin"/>">Server</a></li>
    <li role="presentation"><a href="<c:url value="/admin/trajects"/>">Trajecten</a></li>
    <li role="presentation"><a href="<c:url value="/admin/users"/>">Gebruikers</a></li>
</ul>
<div class="row">
    <div class="col-md-6">
        <h2>Update optimale reistijden</h2>
        <p>Om correct vertragingen te kunnen bepalen, moet de databank juiste referentiewaarden bevatten. Deze waarden
            moeten voorstellen wat de reistijd is in een optimale situatie voor ieder traject is:
        <ul>
            <li>Er mogen geen verkeersincidenten zijn</li>
            <li>De bestuurder moet de maximum toegelaten snelheid zoveel mogelijk kunnen benaderen</li>
            <li>Er moeten genoeg bestuurders ieder traject gebruiken om correcte reistijden te bepalen</li>
        </ul>
        Na het verwerken van enkele gegevens stellen we vast dat tussen <i>06u00 en 07u00</i> 's morgens het systeem
        het best aan de bovenstaande voorwaarden kan voldoen.
        </p>
        <p>
            Als u de onderstaande checkbox aanvinkt, zullen alle bestaande optimale reistijden overschreven worden (
            uiteraard enkel als de meting gelukt is). Zoniet, dan worden de bestaande optimale reistijden die <i>kleiner</i>
            zijn dan de nieuwe reistijden <i>niet</i> overschreven.
        </p>
        <div class="server-input-group">
            <label for="chkDisable">Alle oude optimale reistijden overschrijven: </label>
            <input type="checkbox" name="chkDisable" id="chkDisable"/>
        </div>
        <div class="server-input-group">
            <input class="btn btn-primary right" type="button" name="btnOptimalScrape" id="btnOptimalScrape" value="Optimale reistijden updaten"/>
        </div>
    </div>

    <div class="col-md-6">
        <h2>De scrapers</h2>
        <p>Vooraleer er statistieken kunnen worden opgemaakt, moet de scraper-server actief zijn. Deze zal periodiek (iedere 5
            minuten) verschillende gegevens ophalen van de providers in de databank (Google, Bing Maps, ...). Ter ondersteuning
            van het overgrote deel van deze website moet deze server actief zijn.</p>
        <div class="server-input-group">
            <label class="switch-label" for="chkServerToggle">Scrapers server: </label>
            <input type="checkbox" name="chkServerToggle" id="chkServerToggle" disabled="true" />
        </div>

        <h2>De trajecten van Coyote</h2>
        <p>Om zeker te zijn dat we telkens de correcte metingen ophalen, gebruiken we de services van Coyote om de
            databank op te vullen met de benodigde trajecten. Er wordt een bestand aangemaakt waarin alle trajecten
            (en bijbehorende waypoints) in geplaatst worden. Dit bestand heet <b>trajects.sql</b> is te vinden in dezelfde map van je <b>config.properties</b>-file.
            Een database-developer kan dit bestand importeren in de huidige <i>MariaDB</i>- of <i>MySQL</i> installatie.</p>
        <div class="server-input-group">
            <input class="btn btn-primary right" type="button" name="btnGetTrajects" id="btnGetTrajects" value="Trajecten-bestand aanmaken"/>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/views/partial/footer.jsp"/>