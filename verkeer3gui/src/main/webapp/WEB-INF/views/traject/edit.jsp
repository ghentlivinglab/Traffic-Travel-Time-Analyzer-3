<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<h1>Wijzig traject <c:out value="${traject.naam}"/></h1>
<div class="panel panel-primary col col-md-4" id="wijzig-traject-form">
    <div class="panel-heading">Wijzig traject</div>
    <div class="panel-body">
        <form class="form-horizontal" id="edit-traject" role="form" method="post">
            <input type="hidden" id="id" name="id" value="<c:out value="${traject.id}"/>">
            <div class="form-group">
                <label for="naam">Traject naam</label>
                <input class="form-control" id="naam" type="text" name="naam" value="<c:out value="${traject.naam}"/>">
            </div>
            <div class="form-group">
                <label for="lengte">Lengte</label>
                <input class="form-control" id="lengte" type="number" name="lengte" value="<c:out value="${traject.lengte}"/>">
            </div>
            <div class="form-group">
                <label for="optimale_reistijd">Ideale reistijd</label>
                <input class="form-control" id="optimale_reistijd" type="number" name="optimale_reistijd" value="<c:out value="${traject.optimale_reistijd}"/>">
            </div>
            <div class="form-group">
                <label for="start_latitude">Start latitude</label>
                <input class="form-control" id="start_latitude" type="text" name="start_latitude" value="<c:out value="${traject.start_latitude}"/>">
            </div>
            <div class="form-group">
                <label for="start_longitude">Start longitude</label>
                <input class="form-control" id="start_longitude" type="text" name="start_longitude" value="<c:out value="${traject.start_longitude}"/>">
            </div>
            <div class="form-group">
                <label for="start_latitude">End latitude</label>
                <input class="form-control" id="end_latitude" type="text" name="end_latitude" value="<c:out value="${traject.end_latitude}"/>">
            </div>
            <div class="form-group">
                <label for="start_latitude">End longitude</label>
                <input class="form-control" id="end_longitude" type="text" name="end_longitude" value="<c:out value="${traject.end_longitude}"/>">
            </div>
            <div class="form-group-sm">
                <label for="is_active">Traject is actief:</label>
                <input type="hidden" name="_is_active"/>
                <input type="checkbox" id="is_active" name="is_active" value="true"  <c:if test="${traject.is_active()}">checked</c:if>>
            </div>
            <button type="submit" class="btn btn-success">Wijzig traject</button>
        </form>
    </div>
</div>
<div class="panel panel-info col col-md-7" id="wijzig-traject-map">
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
<script src="<c:url value="/resources/js/traject_edit.js"/>"></script>
<jsp:include page="/WEB-INF/views/partial/footer.jsp" />