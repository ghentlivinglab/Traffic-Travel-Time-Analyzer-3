<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Trajecten</h1>
<div class="col-md-6" id="trajecten">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Traject naam</th>
            <th>Lengte in km</th>
            <th>Optimale Reistijd</th>
            <th>Toon</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="traject" items="${trajecten}" >
                <tr>
                    <td><c:out value="${traject.naam}"/></td>
                    <td><c:out value="${traject.lengte/1000}"/></td>
                    <td><c:out value="${traject.optimale_reistijd}"/></td>
                    <td><a class="btn btn-default view-traject-onmap" data-id="<c:out value="${traject.id}"/>">Show</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div class="col-md-6" id="map"></div>
<link href="<c:url value="/resources/leaflet/leaflet.css"/>" rel="stylesheet"  type="text/css" />
<link href="<c:url value="/resources/leaflet/leaflet-routing-machine.css"/>" rel="stylesheet"  type="text/css" />
<script src="<c:url value="/resources/leaflet/leaflet.js"/>"></script>
<script src="<c:url value="/resources/leaflet/leaflet-routing-machine.min.js"/>"></script>
<script src="<c:url value="/resources/js/trajecten.js"/>"></script>
<jsp:include page="/WEB-INF/views/partial/footer.jsp" />
