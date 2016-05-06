<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Vergelijken van trajecten</h1>
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
<div id="selecteer-dropdown-traject-1">
    <select id="traject-dropdown1" class="form-control">
        <option selected disabled>Kies een traject</option>
        <c:forEach var="traject" items="${trajecten}" >
            <option value="<c:out value="${traject.id}"/>"><c:out value="${traject.naam}"/></option>
        </c:forEach>
    </select>

    <!--<div id="dropdown-select-traject-1" class="dropdown">
        <button class="btn btn-default dropdown-toggle traject-dropdown" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            <p id="geselecteerd-traject-1">Kies traject ...</p>
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" aria-labelledby="traject-dropdown">
            <c:forEach var="traject" items="${trajecten}" >
                <li class="traject-dropdown-item" data-trajectid="<c:out value="${traject.id}"/>"><c:out value="${traject.naam}"/></li>
            </c:forEach>
        </ul>
    </div>-->
</div>
<div id="selecteer-dropdown-traject-2">
    <select id="traject-dropdown2" class="form-control">
        <option selected disabled>(Kies een tweede traject)</option>
        <c:forEach var="traject" items="${trajecten}" >
            <option value="<c:out value="${traject.id}"/>"><c:out value="${traject.naam}"/></option>
        </c:forEach>
    </select>
    <!--<div id="dropdown-select-traject-2" class="dropdown">
        <button class="btn btn-default dropdown-toggle traject-dropdown" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            <p id="geselecteerd-traject-2">Kies traject ...</p>
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" aria-labelledby="traject-dropdown">
            <c:forEach var="traject" items="${trajecten}" >
                <li class="traject-dropdown-item" data-trajectid="<c:out value="${traject.id}"/>"><c:out value="${traject.naam}"/></li>
            </c:forEach>
        </ul>
    </div>-->
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