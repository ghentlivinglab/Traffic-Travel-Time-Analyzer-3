package be.ugent.tiwi;

import be.ugent.tiwi.dal.MetingRepository;
import be.ugent.tiwi.dal.ProviderRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Vertraging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import settings.Settings;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    private static int counter = 0;
    private static final String VIEW_INDEX = "home/index";
    private static final Logger logger = LogManager.getLogger(IndexController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)

    public String index(ModelMap model,@RequestParam(value = "provider", defaultValue = "-1") int provider) {
        //Config.properties file in jetty home zetten voor testing...
        List<Provider> providers = new ProviderRepository().getActieveProviders();
        try {
            MetingRepository mcrud = new MetingRepository();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startTime = LocalDateTime.now().minusMinutes(Long.parseLong(Settings.getSetting("stat_minutes")));
            Provider providerobj = new ProviderRepository().getProvider(provider);
            double vertraging;
            Traject drukste_traject;
            if(provider!=-1) {
                vertraging = mcrud.gemiddeldeVertraging(providerobj, startTime, now);
                drukste_traject = mcrud.getDrukstePunt(providerobj, startTime, now).getTraject();
            } else {
                vertraging = mcrud.gemiddeldeVertraging(startTime, now);
                drukste_traject = mcrud.getDrukstePunt(startTime, now).getTraject();
            }
            String drukste_punt = drukste_traject.getNaam();
            int minuten = (int) vertraging / 60;
            model.addAttribute("vertraging", vertraging > 0 ? true : false);
            model.addAttribute("provider",provider);
            model.addAttribute("vertraging_min", minuten);
            model.addAttribute("vertraging_sec", (int) (vertraging - (minuten * 60)));
            model.addAttribute("drukste_punt", drukste_punt);
            model.addAttribute("drukste_punt_id", drukste_traject.getId());
        }catch(Exception e){
          model.addAttribute("exceptie","Fout bij het ophalen van de gemiddeldes. <ul><li>Staat de scraper server wel aan?</li><li>Zijn de API-keys nog allemaal geldig?</li></ul>");
        }
        model.addAttribute("providers",providers);
        model.addAttribute("stat_min", Settings.getSetting("stat_minutes"));


        // Spring uses InternalResourceViewResolver and return back index.jsp
        return VIEW_INDEX;

    }

    @RequestMapping(value = "/trajecten", method = RequestMethod.GET)
    public String trajecten(ModelMap model) {
        // Spring uses InternalResourceViewResolver and return back index.jsp
        TrajectRepository tr = new TrajectRepository();
        List<Traject> trajecten = tr.getTrajecten();
        ProviderRepository pr = new ProviderRepository();
        List<Provider> providers = pr.getActieveProviders();
        MetingRepository mr = new MetingRepository();

        //traject.id -> vertraging
        Map<Integer, Integer> globaleVertragingen = new HashMap<>();
        List<Vertraging> vList = mr.getVertragingen(LocalDateTime.now().minusMinutes(Long.parseLong(Settings.getSetting("stat_minutes"))), LocalDateTime.now());
        if(vList != null)
            for(Vertraging v : vList)
                globaleVertragingen.put(v.getTraject().getId(), (int) Math.round(v.getAverageVertraging()));

        Map<Integer, Map<Integer, Integer>> vertragingen = new HashMap<>();
        for(Provider p : providers) {
            Map<Integer, Integer> temp = new HashMap<>();
            vList = mr.getVertragingen(p, LocalDateTime.now().minusMinutes(Long.parseLong(Settings.getSetting("stat_minutes"))), LocalDateTime.now());
            if(vList != null)
                for(Vertraging v : vList)
                    temp.put(v.getTraject().getId(), (int) Math.round(v.getAverageVertraging()));
            vertragingen.put(p.getId(), temp);
        }
        model.addAttribute("vertragingen", vertragingen);
        model.addAttribute("globaleVertragingen", globaleVertragingen);
        model.addAttribute("trajecten",trajecten);
        model.addAttribute("providers",providers);


        return "home/trajecten";
    }
    @RequestMapping(value = "/trajecten/{id}", method = RequestMethod.GET)
    public String traject(@PathVariable("id") int id, ModelMap model) {
        // Spring uses InternalResourceViewResolver and return back index.jsp
        TrajectRepository tr = new TrajectRepository();
        List<Traject> trajecten = tr.getTrajecten();
        ProviderRepository pr = new ProviderRepository();
        List<Provider> providers = pr.getActieveProviders();
        MetingRepository mr = new MetingRepository();

        //traject.id -> vertraging
        Map<Integer, Integer> globaleVertragingen = new HashMap<>();
        List<Vertraging> vList = mr.getVertragingen(LocalDateTime.now().minusMinutes(Long.parseLong(Settings.getSetting("stat_minutes"))), LocalDateTime.now());
        if(vList != null)
            for(Vertraging v : vList)
                globaleVertragingen.put(v.getTraject().getId(), (int) Math.round(v.getAverageVertraging()));

        Map<Integer, Map<Integer, Integer>> vertragingen = new HashMap<>();
        for(Provider p : providers) {
            Map<Integer, Integer> temp = new HashMap<>();
            vList = mr.getVertragingen(p, LocalDateTime.now().minusMinutes(Long.parseLong(Settings.getSetting("stat_minutes"))), LocalDateTime.now());
            if(vList != null)
                for(Vertraging v : vList)
                    temp.put(v.getTraject().getId(), (int) Math.round(v.getAverageVertraging()));
            vertragingen.put(p.getId(), temp);
        }
        model.addAttribute("currentTrajectId", id);
        model.addAttribute("vertragingen", vertragingen);
        model.addAttribute("globaleVertragingen", globaleVertragingen);
        model.addAttribute("trajecten",trajecten);
        model.addAttribute("providers",providers);


        return "home/trajecten";
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String status(ModelMap model) {
        TrajectRepository tr = new TrajectRepository();
        List<Traject> trajecten = tr.getTrajecten();

        model.addAttribute("trajecten",trajecten);

        return "home/status";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String reset(ModelMap model)
    {
        return "home/about";
    }

    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public String compare(ModelMap model) {
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

        return "home/compare";
    }
}