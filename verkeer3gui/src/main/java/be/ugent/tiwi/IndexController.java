package be.ugent.tiwi;

import be.ugent.tiwi.dal.MetingRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Traject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import settings.Settings;

import java.util.List;

@Controller
public class IndexController {
    private static int counter = 0;
    private static final String VIEW_INDEX = "home/index";
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        //Config.properties file in jetty home zetten voor testing...
        MetingRepository mcrud = new MetingRepository();
        List<Meting> metingen = mcrud.getMetingen();
        model.addAttribute("metingen", metingen);

        // Spring uses InternalResourceViewResolver and return back index.jsp
        return VIEW_INDEX;

    }

    @RequestMapping(value = "/trajecten", method = RequestMethod.GET)
    public String trajecten(ModelMap model) {
        // Spring uses InternalResourceViewResolver and return back index.jsp
        TrajectRepository tr = new TrajectRepository();
        List<Traject> trajecten = tr.getTrajecten();

        model.addAttribute("trajecten",trajecten);
        return "home/trajecten";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String reset(ModelMap model)
    {
        return "home/about";
    }

}