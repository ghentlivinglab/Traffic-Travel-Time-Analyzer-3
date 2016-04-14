package be.ugent.tiwi;

import be.ugent.tiwi.dal.MetingRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Vertraging;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class IndexController {
    private static int counter = 0;
    private static final String VIEW_INDEX = "home/index";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model, @RequestParam(value="pid", required=false) Integer pid) {
        //Config.properties file in jetty home zetten voor testing...
        MetingRepository mcrud = new MetingRepository();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1L);
        double vertraging = mcrud.gemiddeldeVertraging(oneDayAgo, now);
        Vertraging v = mcrud.getDrukstePunt(oneDayAgo,now);
        Traject drukste_traject = null;
        if(v != null) {
            drukste_traject = v.getTraject();
            model.addAttribute("drukste_punt_id", drukste_traject.getId());
            model.addAttribute("drukste_punt", drukste_traject.getNaam());
        }

        int minuten = (int)vertraging/60;
        model.addAttribute("vertraging",vertraging>0?true:false);
        model.addAttribute("vertraging_min", minuten);
        model.addAttribute("vertraging_sec", (int)(vertraging-(minuten*60)));


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

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String status(ModelMap model) {
        TrajectRepository tr = new TrajectRepository();
        List<Traject> trajecten = tr.getTrajecten();

        MetingRepository mcrud = new MetingRepository();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1L);
        double vertraging = mcrud.gemiddeldeVertraging(oneDayAgo, now);
        String drukste_punt = mcrud.getDrukstePunt(oneDayAgo,now).getTraject().getNaam();
        int minuten = (int)vertraging/60;

        model.addAttribute("vertraging",vertraging>0?true:false);
        model.addAttribute("trajecten",trajecten);
        model.addAttribute("totale_vertraging_min",minuten);
        model.addAttribute("totale_vertraging_sec",(int)(vertraging-(minuten*60)));
        model.addAttribute("drukste_plaats",drukste_punt);

        return "home/status";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String reset(ModelMap model)
    {
        return "home/about";
    }

}