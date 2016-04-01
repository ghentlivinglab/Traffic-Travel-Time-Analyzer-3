package be.ugent.tiwi;

import be.ugent.tiwi.dal.MetingRepository;
import be.ugent.tiwi.dal.ProviderRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import settings.Settings;

import java.util.List;

@Controller
public class IndexController {
    private static int counter = 0;
    private static final String VIEW_INDEX = "home/index";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model, @RequestParam(value="pid", required=false) Integer pid) {
        //Config.properties file in jetty home zetten voor testing...
        MetingRepository mcrud = new MetingRepository();
        List<Provider> gebruikteProviders = mcrud.getGebruikteProviders();


        boolean pidExists = false;
        if(pid != null)
            for(Provider p : gebruikteProviders)
                if(p.getId() == pid){
                    pidExists = true;
                    break;
                }

        if(!pidExists && gebruikteProviders.size() > 0)
            pid = gebruikteProviders.get(0).getId();
        Provider p = new ProviderRepository().getProvider(pid);
        List<Meting> metingen = mcrud.getLaatsteMetingenByProvider(pid);
        boolean metingenHebbenCorrecteOptimaleReistijd = true;
        if(!p.getNaam().equalsIgnoreCase("coyote"))
            for(Meting m : metingen) {
                int reistijd = mcrud.getOptimaleReistijdLaatste7Dagen(m.getTraject().getId(), pid);
                if(reistijd != -1)
                    m.getTraject().setOptimale_reistijd(reistijd);
                else {
                    //Negatieve waarde instellen zodat index.js weet dat het gaat over de optimale reistijd van het traject (en niet de dynamish gegenereerde)
                    m.getTraject().setOptimale_reistijd(-m.getTraject().getOptimale_reistijd());
                    metingenHebbenCorrecteOptimaleReistijd = false;
                }
            }
        model.addAttribute("currentProviderId", pid);
        model.addAttribute("metingen", metingen);
        model.addAttribute("gebruikteProviders", gebruikteProviders);
        model.addAttribute("correcteOptimaleReistijden", metingenHebbenCorrecteOptimaleReistijd);

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

        model.addAttribute("trajecten",trajecten);
        model.addAttribute("totale_vertraging_min",10);
        model.addAttribute("drukste_plaats","centrum");
        return "home/status";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String reset(ModelMap model)
    {
        return "home/about";
    }

}