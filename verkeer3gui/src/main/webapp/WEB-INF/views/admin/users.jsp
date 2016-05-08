<jsp:include page="/WEB-INF/views/partial/header.jsp"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1 class="inline-h">Admin panel</h1>

<ul class="nav nav-tabs admin-nav-tabs">
    <li role="presentation"><a href="<c:url value="/admin"/>">Server</a></li>
    <li role="presentation"><a href="<c:url value="/admin/trajects"/>">Trajecten</a></li>
    <li role="presentation" class="active"><a href="<c:url value="/admin/users"/>">Gebruikers</a></li>
</ul>

<div class="panel panel-primary" id="admin-traject-table">
    <div class="panel-heading">
        <a data-toggle="collapse" data-target="#admin-user-details"
           href="#admin-user-details">
            Beheer gebruikers
        </a>
    </div>
    <ul class="nav navbar">
        <li>
            <a href="<c:url value="/user"/>"><span class="glyphicon glyphicon-plus"></span> Voeg een nieuwe gebruiker toe...</a>
        </li>
    </ul>
    <div id="admin-user-details" class="panel-collapse collapse in">
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Verwijder</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">

                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.username}"/></td>
                        <td><a href=<c:url value="/user/remove/${user.username}"/>>
                            <span class="glyphicon glyphicon-remove"></span></a></td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>



</div>


<jsp:include page="/WEB-INF/views/partial/footer.jsp"/>