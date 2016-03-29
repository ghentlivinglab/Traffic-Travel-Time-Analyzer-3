    <jsp:include page="/WEB-INF/views/partial/header.jsp" />
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <link href="<c:url value="/resources/css/font-awesome.min.css"/>" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/resources/cp/css/sb-admin-2.css"/>" rel="stylesheet"  type="text/css" />
    <link href="<c:url value="/resources/cp/css/timeline.css"/>" rel="stylesheet"  type="text/css" />
    <script src="<c:url value="/resources/cp/js/sb-admin-2.js"/>"></script>

    <h1>Welkom op de Mobiliteitspagina van de stad Gent</h1>
    <h2>Actueel overzicht (de afgelopen dag)</h2>
    <div class="col-lg-3 col-md-6">
        <div class="panel panel-<c:out value="${vertraging?'red':'green'}"/>">
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
    <jsp:include page="/WEB-INF/views/partial/footer.jsp" />
