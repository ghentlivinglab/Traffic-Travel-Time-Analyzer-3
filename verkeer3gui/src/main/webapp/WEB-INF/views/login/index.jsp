<jsp:include page="/WEB-INF/views/partial/header.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">


    <form class="form-signin" method="post">
        <h2 class="form-signin-heading">Inloggen</h2>
        <h3 type="<c:out value="${type}"/>" name="error" style="color:red"><c:out value="${error}"/></h3>
        <label for="inputUsername" class="sr-only">Gebruikersnaam</label>
        <input id="inputUsername" name="inputUsername" class="form-control" placeholder="Gebruikersnaam" required="" autofocus="" type="text">
        <label for="inputPassword" class="sr-only">Wachtwoord</label>
        <input id="inputPassword" name="inputPassword" class="form-control" placeholder="Wachtwoord" required="" type="password">
        <div class="checkbox">
            <label>
                <input name="inputRemember" type="checkbox" value="checked"> Onthouden
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
    </form>


</div> <!-- /container -->
<jsp:include page="/WEB-INF/views/partial/footer.jsp" />