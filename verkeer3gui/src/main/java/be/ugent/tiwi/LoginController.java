package be.ugent.tiwi;

import be.ugent.tiwi.controller.exceptions.UserException;
import be.ugent.tiwi.dal.MetingRepository;
import be.ugent.tiwi.dal.LoginRepository;
import be.ugent.tiwi.dal.ProviderRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.User;

import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(ModelMap model,
                        @CookieValue(value = "verkeerCookie", defaultValue = "verkeerCookie") String cookieContent,
                        HttpServletResponse response) {
        model.addAttribute("type", "hidden");


        //Cookiecontrole
        //Cookie moet bestaan
        if (cookieContent != null) {
            //Bevat deze substrings
            if(cookieContent.contains("username=") && cookieContent.contains("&sessionID=")) {
                LoginRepository lr = new LoginRepository();
                String[] parts = cookieContent.split("&");
                String username = parts[0].split("=")[1];
                User user = new User(username);
                String cookieSessionID = parts[1].split("=")[1];
                String databaseSessionID = lr.getUserSessionID(user);


                //Controle indien gebruiker bestaat en de sessieID van cookie overeenkomt met deze in de database
                if (lr.userExists(user) && cookieSessionID.equals(databaseSessionID)) {
                    //$$verder gaan
                    model.addAttribute("error", "Cookie correct ingelezen! " + cookieContent);
                } else {
                    //$$mag weg
                    model.addAttribute("error", "Cookie verwijderd! ");
                    response.addCookie(deleteCookie("verkeerCookie"));
                }
            }
            else{
                //Cookie is foutief aangemaakt / gewijzigd
                response.addCookie(deleteCookie("verkeerCookie"));
            }
        }

        return "login/index";
    }

    /**
     * Wordt aangeroepen bij het inloggen
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String post(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        boolean loginSucces = false;
        LoginRepository lr = new LoginRepository();

        try {
            User user = new User(request.getParameter("inputUsername"), request.getParameter("inputPassword"));
            model.addAttribute("type", "text");

            if (lr.userExists(user)) {
                if (lr.credentialsCorrect(user)) {
                    //continue login

                    //SessieID genereren en opslaan in de database
                    lr.generateUserSessionID(user);

                    //Variabelen voor het aanmaken van een cookie
                    String cookieName = "verkeerCookie";
                    int age;
                    String path = "/";
                    String cookieSessionID = lr.getUserSessionID(user);

                    //Checkbox is aangevink indien de value gereturned wordt
                    //Niet aangevinkt returned null
                    if (request.getParameter("inputRemember") != null) {
                        //Cookie bewaren voor 7 dagen
                        age = 60 * 60 * 24 * 7;
                    } else {
                        //Cookie wissen bij het sluiten van de browser
                        age = -1;
                    }

                    response.addCookie(createCookie("verkeerCookie", age, "/", user, cookieSessionID));
                    loginSucces = true;
                } else {
                    //login incorrect
                    model.addAttribute("error", "Het wachtwoord is incorrect.");
                }
            } else {
                //username does not exist
                model.addAttribute("error", "De username " + user.getUsername() + " is niet gevonden.");
            }
        } catch (UserException ue) {
            model.addAttribute("error", ue.toString());
        }

        if (loginSucces) {
            return "home/index";
        } else {
            return "login/index";
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
    private Cookie createCookie(String cookieName, int age, String path, User user, String sessionID) {
        Cookie myCookie = new Cookie("verkeerCookie", "username=" + user.getUsername() + "&" + "sessionID=" + sessionID);
        myCookie.setPath(path);
        myCookie.setMaxAge(age);
        return myCookie;
    }
}