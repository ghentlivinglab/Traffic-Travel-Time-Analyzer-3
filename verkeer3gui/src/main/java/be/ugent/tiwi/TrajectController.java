package be.ugent.tiwi;

import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.dal.ITrajectRepository;
import be.ugent.tiwi.dal.LoginRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.User;
import be.ugent.tiwi.domein.Waypoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import settings.DependencyModules.RepositoryModule;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jelle on 10.03.16.
 */
@Controller
@RequestMapping("/traject")
public class TrajectController {
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String wijzigTraject(@PathVariable("id") int id, ModelMap model,
                                @CookieValue(value = "verkeerCookie", defaultValue = "verkeerCookie") String cookieContent,
                                HttpServletResponse response) {
        //Contole indien het cookie correct is
        if (!validCookie(model, cookieContent, response))
            return "redirect:/login";
        else {
            ITrajectRepository repo = new TrajectRepository();
            Traject traject = repo.getTraject(id);
            model.addAttribute("traject", traject);
            return "traject/edit";
        }
    }

    @RequestMapping(value = "/{id}/waypoints", method = RequestMethod.GET)
    @ResponseBody
    public String getWayPoints(@PathVariable("id") int id, ModelMap model,
                               @CookieValue(value = "verkeerCookie", defaultValue = "verkeerCookie") String cookieContent,
                               HttpServletResponse response) {
        //Contole indien het cookie correct is
        if (!validCookie(model, cookieContent, response))
            return "redirect:/login";
        else {

            TrajectRepository trajectRepository = new TrajectRepository();
            List<Waypoint> waypoints = trajectRepository.getWaypoints(id);
            Gson gson = new Gson();
            String boop = gson.toJson(waypoints);
            return boop;
        }
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public ModelAndView slaTrajectWijzigingenOp(@PathVariable("id") int id, @ModelAttribute Traject traject, @RequestParam(value = "wayPoints") String wayPoints, Model model) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Waypoint>>() {
        }.getType();
        traject.setWaypoints(gson.fromJson(wayPoints, collectionType));

        Injector injector = Guice.createInjector(new RepositoryModule());
        DatabaseController dbController = injector.getInstance(DatabaseController.class);
        dbController.wijzigTraject(traject);
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        modelAndView.addObject("message", "Wijzigen van " + traject.getNaam() + " geslaagd!");
        return modelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String nieuwTraject(ModelMap model,
                               @CookieValue(value = "verkeerCookie", defaultValue = "verkeerCookie") String cookieContent,
                               HttpServletResponse response) {
        //Contole indien het cookie correct is
        if (!validCookie(model, cookieContent, response)) {
            return "redirect:/login";
        } else {
            return "traject/new";
        }
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView slaTrajectOp(@ModelAttribute Traject traject, @RequestParam(value = "wayPoints") String
            wayPoints, Model model) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Waypoint>>() {
        }.getType();
        traject.setWaypoints(gson.fromJson(wayPoints, collectionType));

        Injector injector = Guice.createInjector(new RepositoryModule());
        DatabaseController dbController = injector.getInstance(DatabaseController.class);
        dbController.voegTrajectToe(traject);
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        modelAndView.addObject("message", "Toevoegen van " + traject.getNaam() + " geslaagd!");
        return modelAndView;
    }


    /**
     * Controle indien de cookie die aanwezig is op het host-device overeen komt met wat in de database aanwezig is
     *
     * @param model
     * @param cookieContent
     * @param response
     * @return
     */

    public boolean validCookie(ModelMap model,
                               @CookieValue(value = "verkeerCookie", defaultValue = "verkeerCookie") String cookieContent,
                               HttpServletResponse response) {

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
                    response.addCookie(deleteCookie("verkeerCookie"));
                    loginSucces = false;
                }
            } else {
                //Cookie is foutief aangemaakt / gewijzigd
                response.addCookie(deleteCookie("verkeerCookie"));
                loginSucces = false;
            }
        }

        return loginSucces;
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