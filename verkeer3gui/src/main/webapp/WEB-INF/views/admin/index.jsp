<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Admin panel</h1>
<div class="panel panel-primary" id="admin-traject-table">
    <div class="panel-heading">
        <a data-toggle="collapse" data-target="#admin-traject-details"
           href="#admin-traject-details">
            Beheer trajecten
        </a>
    </div>
    <div id="admin-traject-details" class="panel-collapse collapse in">
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>id</th>
                    <th>Naam traject</th>
                    <th>is actief</th>
                    <th>ideale reistijd</th>
                    <th>afstand (km)</th>
                    <th>Start Latitude</th>
                    <th>Start Longitude</th>
                    <th>End Latitude</th>
                    <th>End Longitude</th>
                    <th>Acties</th>
                </tr>
                </thead>
                <tbody>
                <tbody>
                <c:forEach var="traject" items="${trajecten}" >
                    <tr>
                        <td><c:out value="${traject.id}"/></td>
                        <td><c:out value="${traject.naam}"/></td>
                        <td><span class="glyphicon <c:out value="${traject.is_active()?'glyphicon-ok':'glyphicon-remove'}"/>" aria-hidden="true"></span></td>
                        <td><c:out value="${traject.optimale_reistijd}"/></td>
                        <td><c:out value="${traject.lengte/1000}"/></td>
                        <td><c:out value="${traject.start_latitude}"/></td>
                        <td><c:out value="${traject.start_longitude}"/></td>
                        <td><c:out value="${traject.end_latitude}"/></td>
                        <td><c:out value="${traject.end_longitude}"/></td>
                        <td><a href=<c:url value="/traject/${traject.id}/edit"/>><span class="glyphicon glyphicon-edit"></span></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/partial/footer.jsp" />