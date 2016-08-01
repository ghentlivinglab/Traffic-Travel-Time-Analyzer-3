package be.ugent.tiwi;

import be.ugent.tiwi.dal.LoginRepository;
import be.ugent.tiwi.domein.User;

import javax.servlet.http.Cookie;


/**
 * Created by Jeroen on 28/04/2016.
 */
public class cookieController {

    /**
     * Controle indien de cookie die aanwezig is op het host-device overeen komt met wat in de database aanwezig is
     *
     * @param cookieContent
     * @return
     */
    public boolean validCookie(String cookieContent) {

        boolean loginSucces = false;
        //Cookiecontrole
        //Cookie moet bestaan
        if (cookieContent != null) {
            //Bevat deze substrings
            if (cookieContent.matches("username=\\w+&sessionID=\\d+")) {
                LoginRepository lr = new LoginRepository();
                String[] parts = cookieContent.split("&");
                String username = parts[0].split("=")[1];
                User user = new User(username);
                String cookieSessionID = parts[1].split("=")[1];
                String databaseSessionID = lr.getUserSessionID(user);


                //Controle indien gebruiker bestaat en de sessieID van cookie overeenkomt met deze in de database
                if (lr.userExists(user) && cookieSessionID.equals(databaseSessionID)) {

                    //De persoon is correct ingelogd
                    loginSucces = true;

                } else {
                    //Cookie foutief
                    //response.addCookie(deleteCookie("verkeerCookie"));
                    loginSucces = false;
                }
            } else {
                //Cookie is foutief aangemaakt / gewijzigd
                //response.addCookie(deleteCookie("verkeerCookie"));
                loginSucces = false;
            }
        }

        return loginSucces;
    }

    /**
     * Aanmaken van de cookie voor logincontrole
     *
     * @param cookieName
     * @param age
     * @param path
     * @param user
     * @param sessionID
     * @return
     */
    public Cookie createCookie(String cookieName, int age, String path, User user, String sessionID) {
        Cookie myCookie = new Cookie("verkeerCookie", "username=" + user.getUsername() + "&" + "sessionID=" + sessionID);
        myCookie.setPath(path);
        myCookie.setMaxAge(age);
        return myCookie;
    }

    /**
     * Verwijderen van een aangemaakte cookie
     * Deze maakt een cookie aan met de opgegeven naam die meteen zal verdwijnen bij het inladen door een browser
     *
     * @param cookieName
     * @return
     */

    public Cookie deleteCookie(String cookieName) {
        Cookie myCookie = new Cookie(cookieName, null); // Not necessary, but saves bandwidth.
        myCookie.setPath("/");
        myCookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
        return myCookie;
    }
}
