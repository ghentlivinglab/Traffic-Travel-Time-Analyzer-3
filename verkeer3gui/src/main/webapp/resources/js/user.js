/**
 * Created by Jeroen on 22/04/2016.
 */
$( document ).ready(function() {
    var cookieName = "verkeerCookie";
    var userName = "";

    //Indien geklikt wordt op de knop om uit te loggen
    $("#logout").on("click", deleteCookie);

    //Cookie ophalen
    if (document.cookie.indexOf(cookieName) >= 0) {
        //Cookie bestaat

        //Cookie uitlezen
        var cookieValue = getCookie(cookieName);
        if (cookieValue.indexOf('&')!=-1 && cookieValue.indexOf('=')!=-1) {
            //Cookie bevat & en = teken
            userName = cookieValue.split("&")[0].split("=")[1];

            //Knoppen + welkomsttekst zichtbaar maken
            document.getElementById("logout").setAttribute("style", "display: inline-block;");
            document.getElementById("welcome").setAttribute("style", "display: inline-block;font-size: 20px;color:black;");
            document.getElementById("welcome").innerHTML = "  " + userName;
        }
    }


    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    function deleteCookie() {
        //document.cookie = cookieName + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        $.removeCookie(cookieName, { path: '/' });
        document.getElementById("logout").setAttribute("style", "display: none;");
        document.getElementById("welcome").setAttribute("style", "display: none;font-size: 20px;color:black;");
        //Redirect naar de home pagina
        window.location.href = '/verkeer3gui';
    }
});