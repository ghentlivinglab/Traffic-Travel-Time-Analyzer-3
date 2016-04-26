package be.ugent.tiwi;

import be.ugent.tiwi.dal.LoginRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(ModelMap model,
                        @CookieValue(value = "verkeerCookie", defaultValue = "verkeerCookie") String cookieContent,
                        HttpServletResponse response, HttpServletRequest request) {

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
                    TrajectRepository trajectRepository = new TrajectRepository();
                    model.addAttribute("trajecten", trajectRepository.getTrajectenMetWayPoints());
                    loginSucces = true;

                } else {
                    //Cookie foutief
                    response.addCookie(deleteCookie("verkeerCookie"));
                    loginSucces = false;
                }
            } else {
                //Cookie is foutief aangemaakt / gewijzigd
                response.addCookie(deleteCookie("verkeerCookie"));
                loginSucces = false;
            }
        }

        if(loginSucces){
            return "admin/index";
        }
        else{
            //String contextPath = request.getContextPath();
            //response.sendRedirect(response.encodeRedirectURL(contextPath + "/profile.jsp"));
            return "redirect:/login";
        }
    }

    /**
     * Verwijderen van een aangemaakte cookie
     * Deze maakt een cookie aan met de opgegeven naam die meteen zal verdwijnen bij het inladen door een browser
     *
     * @param cookieName
     * @return
     */
    private Cookie deleteCookie(String cookieName) {
        Cookie myCookie = new Cookie(cookieName, null); // Not necessary, but saves bandwidth.
        myCookie.setPath("/");
        myCookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
        return myCookie;
    }
}
