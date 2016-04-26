<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<h1>Voeg nieuw traject toe</h1>
<div class="panel panel-primary col col-md-4" id="nieuw-traject-form">
    <div class="panel-heading">Trajectgegevens</div>
    <div class="panel-body">
        <form class="form-horizontal" id="nieuw-traject" role="form" method="post">
            <input type="hidden" id="wayPoints" name="wayPoints" />
            <div class="form-group">
                <label for="naam">Naam</label>
                <input class="form-control" id="naam" type="text" name="naam">
            </div>
            <div class="form-group">
                <label for="lengte">Afstand</label>
                <input class="form-control" id="lengte" type="number" name="lengte">
            </div>
            <div class="form-group">
                <label for="optimale_reistijd">Optimale reistijd</label>
                <input class="form-control" id="optimale_reistijd" type="number" name="optimale_reistijd">
            </div>
            <div class="form-group">
                <label for="start_latitude">Start-Latitude</label>
                <input class="form-control" id="start_latitude" type="text" name="start_latitude">
            </div>
            <div class="form-group">
                <label for="start_longitude">Start-Longitude</label>
                <input class="form-control" id="start_longitude" type="text" name="start_longitude">
            </div>
            <div class="form-group">
                <label for="start_latitude">End-Latitude</label>
                <input class="form-control" id="end_latitude" type="text" name="end_latitude">
            </div>
            <div class="form-group">
                <label for="start_latitude">End-Longitude</label>
                <input class="form-control" id="end_longitude" type="text" name="end_longitude">
            </div>
            <div class="form-group">
                <label for="is_active">Traject is actief:</label>
                <input type="hidden" name="_is_active"/>
                <input type="checkbox" id="is_active" name="is_active" value="true">
            </div>
            <button onclick="formSubmit()" class="btn btn-success">Voeg traject toe</button>
        </form>
    </div>
</div>
<div class="panel panel-info col col-md-7" id="nieuw-traject-map">
    <div class="panel-heading">Bekijk en sleep op kaart</div>
    <div class="panel-body">
        <div id="map"></div>
    </div>
</div>

<link href="<c:url value="/resources/leaflet/leaflet.css"/>" rel="stylesheet"  type="text/css" />
<script src="<c:url value="/resources/leaflet/leaflet.js"/>"></script>
<link href="<c:url value="/resources/leaflet/leaflet.label.css"/>" rel="stylesheet"  type="text/css" />
<script src="<c:url value="/resources/leaflet/leaflet.label.js"/>"></script>
<link href="<c:url value="/resources/leaflet/leaflet-routing-machine.css"/>" rel="stylesheet"  type="text/css" />
<script src="<c:url value="/resources/leaflet/leaflet-routing-machine.js"/>"></script>
<script src="<c:url value="/resources/js/traject_new.js"/>"></script>
<jsp:include page="/WEB-INF/views/partial/footer.jsp" />