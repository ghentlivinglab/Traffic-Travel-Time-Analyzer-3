<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Actueel overzicht</h1>
<h2>Status</h2>
<ul>
<c:choose>
    <c:when test="${vertraging}">
        <li>Er was de afgelopen dag gemiddeld <c:out value="${totale_vertraging_min}"/>'<c:out value="${totale_vertraging_sec}"/> vertraging in Gent.</li>
    </c:when>
</c:choose>
    <li>Het is momenteel het meest druk in <c:out value="${drukste_plaats}"/>.</li>
</ul>
<h2>Reistijdinformatie</h2>
<div id="timepickers">
    <div class="row">
        <div class='col-sm-6'>
            <div class="form-group">
                <label>Start datum</label>
                <div class='input-group date' id='startdate'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class='col-sm-6'>
            <div class="form-group">
                <label>Eind datum</label>
                <div class='input-group date' id='enddate'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>
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
<div id="chart-area">
    <div class="cs-loader" style="display: none">
        <div class="cs-loader-inner">
            <label>	&bull;</label>
            <label>	&bull;</label>
            <label>	&bull;</label>
            <label>	&bull;</label>
            <label>	&bull;</label>
            <label>	&bull;</label>
        </div>
        <p>Even geduld ... De data wordt geladen</p>
    </div>
    <div id="container">
    </div>
</div>
<script src="<c:url value="/resources/highcharts/js/highcharts.src.js"/>"></script>
<script src="<c:url value="/resources/momentjs/min/moment.min.js"/>"></script>
<script src="<c:url value="/resources/datetimepicker/js/bootstrap-datetimepicker.min.js"/>"></script>
<script src="<c:url value="/resources/js/status.js"/>"></script>

<jsp:include page="/WEB-INF/views/partial/footer.jsp" />
