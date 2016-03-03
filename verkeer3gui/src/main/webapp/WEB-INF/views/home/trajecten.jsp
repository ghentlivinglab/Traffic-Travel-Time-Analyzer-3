<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Trajecten</h1>
<div class="col-md-4" id="map-comments">Hier komen commentaren</div>
<div class="col-md-8" id="map"></div>
<link href="<c:url value="/resources/leaflet/leaflet.css"/>" rel="stylesheet"  type="text/css" />
<link href="<c:url value="/resources/leaflet/leaflet-routing-machine.css"/>" rel="stylesheet"  type="text/css" />
<script src="<c:url value="/resources/leaflet/leaflet.js"/>"></script>
<script src="<c:url value="/resources/leaflet/leaflet-routing-machine.min.js"/>"></script>
<script src="<c:url value="/resources/js/trajecten.js"/>"></script>
<jsp:include page="/WEB-INF/views/partial/footer.jsp" />
