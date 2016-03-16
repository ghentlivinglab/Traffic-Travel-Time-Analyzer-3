<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Actueel overzicht</h1>
<h2>Status</h2>
<ul>
    <li>Er is totaal <c:out value="${totale_vertraging_min}"/> minuten vertraging in Gent.</li>
    <li>Het is momenteel het meest druk in <c:out value="${drukste_plaats}"/>.</li>
</ul>
<h2>Reistijdinformatie</h2>
<div id="selecteer-dropdown-traject">
    <div id="dropdown-select-traject" class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="traject-dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            <p id="geselecteerd-traject">Kies traject ...</p>
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" aria-labelledby="traject-dropdown">
            <c:forEach var="traject" items="${trajecten}" >
                <li class="traject-dropdown-item" data-trajectid="<c:out value="${traject.id}"/>"><c:out value="${traject.naam}"/></li>
            </c:forEach>
        </ul>
    </div>
</div>
<div id="chart">

</div>
<link href="<c:url value="/resources/c3/c3.min.css"/>" rel="stylesheet"  type="text/css" />
<script src="<c:url value="/resources/c3/c3.min.js"/>"></script>
<script src="<c:url value="/resources/js/status.js"/>"></script>
<jsp:include page="/WEB-INF/views/partial/footer.jsp" />
