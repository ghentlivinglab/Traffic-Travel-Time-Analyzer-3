package be.ugent.tiwi;

import be.ugent.tiwi.controller.exceptions.UserException;
import be.ugent.tiwi.dal.*;
import be.ugent.tiwi.domein.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.internal.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import settings.DependencyModules.RepositoryModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Jeroen on 02.05.2016
 */
@Controller
@RequestMapping("/incidents")
public class IncidentController {
    final static String DATE_FORMAT = "yyyy-mm-dd hh:mm:ss";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletResponse response) {

        IncidentRepository ir = new IncidentRepository();
        List<TrafficIncident> trafficIncidents = ir.getTrafficIncidents();

        ProviderRepository pr = new ProviderRepository();
        List<Provider> providers = pr.getActieveProviders();

        TrajectRepository tr = new TrajectRepository();
        List<Traject> trajecten = tr.getTrajecten();

        for (TrafficIncident ti : trafficIncidents) {
            //Volledige Provider gegevens toevoegen aan het het TrafficIncident zodat de naam er later uitgehaald kan worden
            boolean found = false;
            int index = 0;
            while (!found && index < providers.size()) {
                if (ti.getProvider().getId() == providers.get(index).getId()) {
                    ti.setProvider(providers.get(index));
                    found = true;
                }
                index++;
            }

            //Volledig Traffic object toevoegen aan het Trafficincident
            found = false;
            index = 0;

            while (!found && index < trajecten.size()) {
                if (ti.getTraject().getId() == trajecten.get(index).getId()) {
                    ti.setTraject(trajecten.get(index));
                    found = true;
                }
                index++;
            }
        }

        model.addAttribute("trafficIncidents", trafficIncidents);

        //Lijsten met opties toevoegen om te filteren
        providers = ir.getTrafficIncidentsProviders();
        trajecten = ir.getTrafficIncidentsTrajecten();

        model.addAttribute("trafficIncidentsProv", providers);
        model.addAttribute("trafficIncidentsTraj", trajecten);

        return "home/incidents";
    }




    /**
     * Hier komt de 'filtering-query' toe
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String post(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        IncidentRepository ir = new IncidentRepository();
        List<TrafficIncident> trafficIncidents;

        ProviderRepository pr = new ProviderRepository();
        List<Provider> providers = pr.getActieveProviders();

        TrajectRepository tr = new TrajectRepository();
        List<Traject> trajecten = tr.getTrajecten();


        boolean provFilled = !request.getParameter("providers").equals("");
        boolean trajFilled = !request.getParameter("trajecten").equals("");
        boolean startFilled = !request.getParameter("startTime").equals("");
        boolean endFilled = !request.getParameter("endTime").equals("");

        if(startFilled)
            startFilled = isDateValid(request.getParameter("startTime"));
        if(endFilled)
            endFilled = isDateValid(request.getParameter("endTime"));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
        int provider_id = provFilled ? Integer.parseInt(request.getParameter("providers")) : 0;
        int traject_id = trajFilled ? Integer.parseInt(request.getParameter("trajecten")) : 0;
        LocalDateTime start = startFilled ? LocalDateTime.parse(request.getParameter("startTime"), formatter) : LocalDateTime.of(2001, 1, 1, 1, 1);
        LocalDateTime end = endFilled ? LocalDateTime.parse(request.getParameter("endTime"), formatter) : LocalDateTime.now();


        //Indien provider of traject ingevuld zijn
        if (provFilled || trajFilled) {
            // Zowel op provider als traject
            if (provFilled && trajFilled) {
                //prov & traj & Datum zijn ingevuld
                trafficIncidents = ir.getTrafficIncidents(new Provider(provider_id, null, true),
                        new Traject(traject_id, null, 0, 0, null, true, null, null, null, null), start, end);
            } else {
                //Enkel op provider
                if (provFilled) {
                    //prov & dat
                    trafficIncidents = ir.getTrafficIncidents(new Provider(provider_id, null, true), start, end);
                } else {
                    //traject & dat
                    trafficIncidents = ir.getTrafficIncidents(new Traject(traject_id, null, 0, 0, null, true, null, null, null, null), start, end);
                }
            }
        } else {
            //Datums zijn sowieso ingevuld
            trafficIncidents = ir.getTrafficIncidents(start, end);
        }


        for (TrafficIncident ti : trafficIncidents) {
            //Volledige Provider gegevens toevoegen aan het het TrafficIncident zodat de naam er later uitgehaald kan worden
            boolean found = false;
            int index = 0;
            while (!found && index < providers.size()) {
                if (ti.getProvider().getId() == providers.get(index).getId()) {
                    ti.setProvider(providers.get(index));
                    found = true;
                }
                index++;
            }

            //Volledig Traffic object toevoegen aan het Trafficincident
            found = false;
            index = 0;

            while (!found && index < trajecten.size()) {
                if (ti.getTraject().getId() == trajecten.get(index).getId()) {
                    ti.setTraject(trajecten.get(index));
                    found = true;
                }
                index++;
            }
        }

        model.addAttribute("trafficIncidents", trafficIncidents);

        //Lijsten met opties toevoegen om te filteren
        providers = ir.getTrafficIncidentsProviders();
        trajecten = ir.getTrafficIncidentsTrajecten();

        model.addAttribute("trafficIncidentsProv", providers);
        model.addAttribute("trafficIncidentsTraj", trajecten);

        return "home/incidents";
    }


    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}