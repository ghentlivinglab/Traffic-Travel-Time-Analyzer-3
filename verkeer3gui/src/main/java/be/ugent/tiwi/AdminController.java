package be.ugent.tiwi;

import be.ugent.tiwi.dal.ITrajectRepository;
import be.ugent.tiwi.dal.LoginRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Traject;
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

        //Contole indien het cookie correct is
        cookieController cc = new cookieController();

        if (!cc.validCookie(cookieContent)) {
            response.addCookie(cc.deleteCookie("verkeerCookie"));
            return "redirect:/login";
        } else {
            TrajectRepository trajectRepository = new TrajectRepository();
            LoginRepository lr = new LoginRepository();
            model.addAttribute("trajecten", trajectRepository.getTrajectenMetWayPoints());
            model.addAttribute("users", lr.getUsers());
            return "admin/index";
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
