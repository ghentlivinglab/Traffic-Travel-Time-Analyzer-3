    <jsp:include page="/WEB-INF/views/partial/header.jsp" />
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <script src="resources/js/index.js" ></script>
    <h1>Welkom op de Mobiliteitspagina van de stad Gent</h1>
    <ul class="nav nav-tabs">
        <c:forEach var="provider" items="${gebruikteProviders}">
            <li ${provider.id eq currentProviderId ? 'class="active"' : ''}><a href="?pid=${provider.id}">${provider.naam}</a></li>
        </c:forEach>
    </ul>
    <table class="table table-hover" id="vertragingen">
        <thead>
            <tr>
                <th>Traject</th>
                <th>Huidige vertraging</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="meting" items="${metingen}" >
                <tr>
                    <td><c:out value="${meting.traject.naam}"/></td>
                    <td><span d-optimale-reistijd="${meting.traject.optimale_reistijd}" d-reistijd="${meting.reistijd}"></span></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <jsp:include page="/WEB-INF/views/partial/footer.jsp" />
