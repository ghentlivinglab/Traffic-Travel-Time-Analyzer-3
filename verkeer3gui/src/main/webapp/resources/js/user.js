/**
 * Created by Jeroen on 22/04/2016.
 */
$( document ).ready(function() {
    var cookieName = "verkeerCookie";
    var userName = "";


    //Cookie ophalen
    if (document.cookie.indexOf(cookieName) >= 0) {
        //Cookie bestaat

        //Cookie uitlezen
        var cookieValue = getCookie(cookieName);
        if (cookieValue.indexOf('&')!=-1 && cookieValue.indexOf('=')!=-1) {
            //Cookie bevat & en = teken
            userName = cookieValue.split("&")[0].split("=")[1];

            //Knoppen + welkomsttekst zichtbaar maken
            $("#welcome").addClass("visible");
            $("#welcome .user").append(userName);
            $("#login").remove();

            //Indien geklikt wordt op de knop om uit te loggen
            $("#welcome").on("click", deleteCookie);
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
        //Redirect naar de home pagina
        window.location = '/verkeer3gui#';
    }
});