<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Trajecten</h1>
</div>
<div class="content-container">
    <table id="trajecten-table" class="table table-hover" data-toggle="table">
        <thead>
        <tr>
            <th class="th-head" data-sortable="true">Id</th>
            <th class="th-head" data-sortable="true">Naam</th>
            <th class="th-head" data-sortable="true">Lengte</th>
            <th class="rotate optimaleReistijd thh"><div><span>Optimale reistijd</span></div></th>
            <th class="rotate"><div><span>Globaal</span></div></th>
                <c:forEach var="provider" items="${providers}" >
                    <th class="rotate" data-sortable="true"><div><span>${provider.naam}</span></div></th>
                </c:forEach>

            <th class="rotate vertraging thh"><div><span>Vertraging</span></div></th>
            <th class="rotate"><div><span>Globaal</span></div></th>
            <c:forEach var="provider" items="${providers}" >
                <th class="rotate" data-sortable="true"><div><span>${provider.naam}</span></div></th>
            </c:forEach>

            <th class="rotate reistijd thh"><div><span>Huidige reistijd</span></div></th>
            <th class="rotate"><div><span>Globaal</span></div></th>
            <c:forEach var="provider" items="${providers}" >
                <th class="rotate" data-sortable="true"><div><span>${provider.naam}</span></div></th>
            </c:forEach>

        </tr>
        </thead>
        <tbody>
        <c:set var="optTotal" value="${0}" />
        <c:set var="trajectCount" value="${0}" />

        <c:forEach var="traject" items="${trajecten}" >
            <c:set var="trajectCount" value="${trajectCount + 1}"/>
                <tr id="traject-<c:out value="${traject.id}"/>" class="map-not-showing<c:if test="${traject.id eq currentTrajectId}"><c:out value=" current-traject"/></c:if>">
                    <td><c:out value="${traject.id}"/></td>
                    <td><a class="view-traject-onmap" data-id="<c:out value="${traject.id}"/>" data-toggle="modal" data-target="#mapModal"><c:out value="${traject.naam}"/></a></td>
                    <td><c:out value="${traject.lengte/1000}"/></td>

                    <td class="thtd"></td>
                    <c:set var="optTotal" value="${optTotal + traject.optimale_reistijd}"/>
                    <td class="rotatedtd"><c:out value="${traject.optimale_reistijd}"/></td>
                    <c:forEach var="provider" items="${providers}" >
                        <td class="rotatedtd">${traject.optimaleReistijden[provider.id]}</td>
                    </c:forEach>

                    <td class="thtd"></td>
                    <td class="rotatedtd"><c:out value="${globaleVertragingen[traject.id]}"/></td>
                    <c:forEach var="provider" items="${providers}" >
                        <c:choose>
                            <c:when test="${vertragingen[provider.id][traject.id] != null}">
                                <td class="rotatedtd">${vertragingen[provider.id][traject.id]}</td>
                            </c:when>
                            <c:otherwise>
                                <td class="rotatedtd empty"></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <td class="thtd"></td>
                    <td class="rotatedtd"><c:out value="${globaleVertragingen[traject.id] + traject.optimale_reistijd}"/></td>
                    <c:forEach var="provider" items="${providers}" >
                        <c:choose>
                            <c:when test="${vertragingen[provider.id][traject.id] != null}">
                                <td class="rotatedtd">${vertragingen[provider.id][traject.id] + traject.optimaleReistijden[provider.id]}</td>
                            </c:when>
                            <c:otherwise>
                                <td class="rotatedtd empty"></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </c:forEach>

        </tbody>
    </table>
</div>
<div class="content">
    <!-- Modal -->
    <div id="mapModal" class="modal fade" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
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
    <link href="<c:url value="/resources/leaflet/leaflet.css"/>" rel="stylesheet"  type="text/css" />
    <script src="<c:url value="/resources/leaflet/leaflet.js"/>"></script>
    <script src="<c:url value="/resources/bootstrap-table/bootstrap-table.js"/>"></script>
    <link href="<c:url value="/resources/bootstrap-table/bootstrap-table.css"/>" rel="stylesheet"  type="text/css" />
    <script src="<c:url value="/resources/js/trajecten.js"/>"></script>
    <jsp:include page="/WEB-INF/views/partial/footer.jsp" />
