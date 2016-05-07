<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Actueel overzicht</h1>
<h2>Vergelijk reistijdinformatie</h2>
<div id="timepickers" class="left">
  <div class="row">
    <div class='col-sm-8'>
      <div class="form-group">
        <label>Start datum</label>
        <div class='input-group date' id='startdate-1'>
          <input type='text' class="form-control" />
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-calendar"></span>
            </span>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class='col-sm-8'>
      <div class="form-group">
        <label>Eind datum</label>
        <div class='input-group date' id='enddate-1'>
          <input type='text' class="form-control" />
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-calendar"></span>
            </span>
        </div>
      </div>
    </div>
  </div>
</div>
<div id="timepickers" class="right">
  <div class="row">
    <div class='col-sm-8'>
      <div class="form-group">
        <label>Start datum</label>
        <div class='input-group date' id='startdate-2'>
          <input type='text' class="form-control" />
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-calendar"></span>
            </span>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class='col-sm-8'>
      <div class="form-group">
        <label>Eind datum</label>
        <div class='input-group date' id='enddate-2'>
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
      <label> &bull;</label>
      <label> &bull;</label>
      <label> &bull;</label>
      <label> &bull;</label>
      <label> &bull;</label>
      <label> &bull;</label>
    </div>
    <p>Even geduld ... De data wordt geladen</p>
  </div>
  <div id="container1"></div>
  <div id="container2"></div>
</div>
<script src="<c:url value="/resources/highcharts/js/highcharts.src.js"/>"></script>
<script src="<c:url value="/resources/momentjs/min/moment.min.js"/>"></script>
<script src="<c:url value="/resources/datetimepicker/js/bootstrap-datetimepicker.min.js"/>"></script>
<script src="<c:url value="/resources/js/compare.js"/>"></script>

<jsp:include page="/WEB-INF/views/partial/footer.jsp" />