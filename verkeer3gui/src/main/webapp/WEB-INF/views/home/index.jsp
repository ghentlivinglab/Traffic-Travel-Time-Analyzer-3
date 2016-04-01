    <jsp:include page="/WEB-INF/views/partial/header.jsp" />
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <script src="resources/js/index.js" ></script>
    <h1>Welkom op de Mobiliteitspagina van de stad Gent</h1>
    <ul class="nav nav-tabs">
        <c:forEach var="provider" items="${gebruikteProviders}">
            <li ${provider.id eq currentProviderId ? 'class="active"' : ''}><a href="?pid=${provider.id}">${provider.naam}</a></li>
        </c:forEach>
    </ul>
    <c:if test="${not correcteOptimaleReistijden}">
        <br>
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <span class="sr-only">Waarschuwing:</span>
            Niet alle trajecten zijn in de laatste 7 dagen gemeten geweest.
        </div>
    </c:if>
    <table class="table table-hover" id="vertragingen">
        <thead>
            <tr>
                <th></th>
                <th>Traject</th>
                <th>Vertraging</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="meting" items="${metingen}" >
                <tr class="clickablerow" data-href="">
                    <td><c:out value="${meting.traject.id}"/></td>
                    <td><c:out value="${meting.traject.naam}"/></td>
                    <td><span data-optimale-reistijd="${meting.traject.optimale_reistijd}" data-reistijd="${meting.reistijd}"></span></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <jsp:include page="/WEB-INF/views/partial/footer.jsp" />
