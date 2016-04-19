    <jsp:include page="/WEB-INF/views/partial/header.jsp" />
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <link href="<c:url value="/resources/css/font-awesome.min.css"/>" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/resources/cp/css/sb-admin-2.css"/>" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/resources/cp/css/timeline.css"/>" rel="stylesheet"  type="text/css" />

    <h1>Welkom op de Mobiliteitspagina van de stad Gent</h1>

    <c:choose>
        <c:when test="${exceptie == null || exceptie.length()==0}">
            <ul class="nav nav-tabs">
                <li role="presentation" <c:if test="${provider==-1}"><c:out value="class=active"/></c:if>><a href="<c:url value="/"/>">Alle providers</a></li>
                <c:forEach items="${providers}" var="providerobj">
                    <li role="presentation" <c:if test="${providerobj.id == provider}"><c:out value="class=active"/></c:if>><a href="?provider=<c:out value="${providerobj.id}"/>"><c:out value="${providerobj.naam}"/></a></li>
                </c:forEach>
            </ul>
            <h2>Actueel overzicht (het afgelopen uur)</h2>
            <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-<c:out value="${vertraging_min*60 + vertraging_sec < 30 ? 'green': vertraging_min*60 + vertraging_sec < 120 ? 'red' : 'red'}"/>">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-clock-o fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><c:out value="${vertraging_min}"/> min <c:out value="${vertraging_sec}"/></div>
                                    <div><c:out value="${vertraging?'trager':'sneller'}"/> dan ideale reistijd</div>
                                </div>
                            </div>
                        </div>
                        <a href="<c:url value="/status"/>">
                            <div class="panel-footer">
                                <span class="pull-left">Uitgebreide details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-road fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="small"><c:out value="${drukste_punt}"/></div>
                                    <div>drukste punt</div>
                                </div>
                            </div>
                        </div>
                        <a href="<c:url value="/trajecten/${drukste_punt_id}"/>">
                            <div class="panel-footer">
                                <span class="pull-left">Bekijk traject</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <h2>Vertragingen in kaart gebracht</h2>
            <div id="map"></div>
            <script src="<c:url value="/resources/momentjs/min/moment.min.js"/>"></script>
            <link href="<c:url value="/resources/leaflet/leaflet.css"/>" rel="stylesheet"  type="text/css" />
            <script src="<c:url value="/resources/leaflet/leaflet.js"/>"></script>
            <script src="<c:url value="/resources/js/index.js"/>"></script>
        </c:when>
        <c:otherwise>
            <div class="panel panel-danger">
                <div class="panel-heading">
                    <h3 class="panel-title">Oeps...</h3>
                </div>
                <div class="panel-body">
                    Er ging iets fout bij het ophalen van de data... Contacteer de beheerders van deze website voor meer info.
                    <div class="well"><c:out value="${exceptie}"/></div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <jsp:include page="/WEB-INF/views/partial/footer.jsp" />
