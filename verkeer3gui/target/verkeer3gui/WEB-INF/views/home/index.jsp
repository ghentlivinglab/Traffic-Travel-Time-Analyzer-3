    <jsp:include page="/WEB-INF/views/partial/header.jsp" />
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <h1>Welkom op de Mobiliteitspagina van de stad Gent</h1>
    <h2>Reistijden</h2>
    <table class="table table-hover">
        <thead>
            <tr>
                <th>Provider</th>
                <th>Traject</th>
                <th>Reistijd</th>
                <th>Normale tijd</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="meting" items="${metingen}" >
                <tr>
                    <td><c:out value="${meting.provider.naam}"/></td>
                    <td><c:out value="${meting.traject.naam}"/></td>
                    <td><c:out value="${meting.reistijd}"/></td>
                    <td><c:out value="${meting.optimale_reistijd}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <jsp:include page="/WEB-INF/views/partial/footer.jsp" />
