<jsp:include page="/WEB-INF/views/partial/header.jsp"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="now" class="java.util.Date" />

<h1>Verkeersproblemen</h1>
    <div class="form-filter" method="post">

        <form class="form-filter" method="post">
            <label for="providers">Provider:</label>
            <select id="providers" name="providers">
                <option value=""></option>
                <c:forEach var="trafficIncidentProv" items="${trafficIncidentsProv}">
                    <option value="<c:out value="${trafficIncidentProv.id}"/>"><c:out
                            value="${trafficIncidentProv.naam}"/></option>
                </c:forEach>
            </select>


            <label for="trajecten">Traject:</label>
            <select id="trajecten" name="trajecten">
                <option value=""></option>
                <c:forEach var="trafficIncidentTraj" items="${trafficIncidentsTraj}">
                    <option value="<c:out value="${trafficIncidentTraj.id}"/>"><c:out
                            value="${trafficIncidentTraj.naam}"/></option>
                </c:forEach>
            </select>

            <label for="startTime">Starttijd:</label>
            <input type="text" id="startTime" name="startTime" value="" placeholder="dd/mm/yyyy hh:mm"/>


            <label for="endTime">Eindtijd:</label>
            <input type="text" id="endTime" name="endTime" value="" placeholder="dd/mm/yyyy hh:mm"/>


            <button type="submit">Filter</button>
        </form>


        <table id="incidents-table" class="table table-hover" data-toggle="table">
            <thead>
            <tr>
                <th class="th-head" data-sortable="true">Id</th>
                <th class="th-head" data-sortable="true">Provider</th>
                <th class="th-head" data-sortable="true">Traject</th>
                <th class="th-head" data-sortable="true">Start</th>
                <th class="th-head" data-sortable="true">End</th>
                <th class="th-head" data-sortable="true">Problem</th>
            </tr>
            </thead>
            <tbody>


            <c:forEach var="trafficIncident" items="${trafficIncidents}">
                <tr>
                    <td><c:out value="${trafficIncident.id}"/></td>
                    <td><c:out value="${trafficIncident.provider.naam}"/></td>
                    <td>
                        <a class="view-traject-onmap" data-toggle="modal" data-target="#mapModal"><c:out
                                value="${trafficIncident.traject.naam}"/>
                        </a>
                    </td>
                    <td>
                        <fmt:parseDate value="${trafficIncident.startTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
                        <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm"/>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${trafficIncident.endTime lt LocalDateTime.now().minusMinutes(10)}">
                                <fmt:parseDate value="${trafficIncident.endTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
                                <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:formatDate value="${now}" pattern="dd/MM/yyyy HH:mm"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${trafficIncident.problem}"/></td>
                </tr>

            </c:forEach>

            </tbody>
        </table>
    </div>
    <div class="content">
        <div id="mapModal" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Kaart</h4>
                    </div>
                    <div class="modal-body">
                        <div id="map"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Sluiten</button>
                    </div>
                </div>

            </div>
        </div>


        <link rel="stylesheet" type="text/css"
              href="<c:url value="/resources/datetimepicker-master/jquery.datetimepicker.css"/>"/>
        <script src="<c:url value="/resources/datetimepicker-master/build/jquery.datetimepicker.full.min.js"/>"></script>

        <link href="<c:url value="/resources/leaflet/leaflet.css"/>" rel="stylesheet" type="text/css"/>
        <script src="<c:url value="/resources/leaflet/leaflet.js"/>"></script>
        <script src="<c:url value="/resources/bootstrap-table/bootstrap-table.js"/>"></script>
        <link href="<c:url value="/resources/bootstrap-table/bootstrap-table.css"/>" rel="stylesheet" type="text/css"/>
        <script src="<c:url value="/resources/js/jquery.floatThead.js"/>"></script>
        <script src="<c:url value="/resources/js/incidents.js"/>"></script>
        <jsp:include page="/WEB-INF/views/partial/footer.jsp"/>
