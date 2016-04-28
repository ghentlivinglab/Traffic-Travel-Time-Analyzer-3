package be.ugent.tiwi;

import be.ugent.tiwi.controller.exceptions.UserException;
import be.ugent.tiwi.dal.ITrajectRepository;
import be.ugent.tiwi.dal.LoginRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * Wordt opgeroepen bij het verwijderen van een gebruiker uit het systeem
     * Eerst wordt gecontroleerd indien de gebruiker ingelogd is.
     *
     * @param username
     * @param cookieContent
     * @param response
     * @return een redirect naar de login of admin pagina
     */
    @RequestMapping(value = "/remove/{username}", method = RequestMethod.GET)
    public String index(@PathVariable("username") String username,
                        @CookieValue(value = "verkeerCookie", defaultValue = "verkeerCookie") String cookieContent,
                        HttpServletResponse response) {
        //Contole indien het cookie correct is
        cookieController cc = new cookieController();

        if (!cc.validCookie(cookieContent)) {
            response.addCookie(cc.deleteCookie("verkeerCookie"));
            return "redirect:/login";
        } else {
            LoginRepository lr = new LoginRepository();
            User user = new User(username);
            lr.removeUser(user);
            return "redirect:/admin";
        }
    }

    /**
     * Controleren indien de persoon ingelogd is om de pagina te mogen bezoeken
     *
     * @param cookieContent
     * @param response
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@CookieValue(value = "verkeerCookie", defaultValue = "verkeerCookie") String cookieContent,
                        HttpServletResponse response) {
        //Contole indien het cookie correct is
        cookieController cc = new cookieController();

        if (!cc.validCookie(cookieContent)) {
            response.addCookie(cc.deleteCookie("verkeerCookie"));
            return "redirect:/login";
        } else {
            return "user/index";
        }
    }


    /**
     * Wordt aangeroepen bij het toevoegen van een nieuwe gebruiker
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String post(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        LoginRepository lr = new LoginRepository();
        model.addAttribute("type", "hidden");
        model.addAttribute("color", "red");

        try {
            User user = new User(request.getParameter("inputUsername"), request.getParameter("inputPassword"));
            model.addAttribute("type", "text");

            if (!lr.userExists(user)) {
                //Add user
                lr.addUser(user);

                //check if user is added
                if (lr.userExists(user)) {
                    model.addAttribute("error", user.getUsername() + " toegevoegd aan het systeem!");
                    model.addAttribute("color", "green");
                } else
                    model.addAttribute("error", "Er is intern iets misgelopen bij het toevoegen van de gebruiker.");
            } else {
                //username already used
                model.addAttribute("error", "De username " + user.getUsername() + " is reeds in gebruik.");
            }
        } catch (UserException ue) {
            model.addAttribute("error", ue.toString());
        }

        return "user/index";
    }

}