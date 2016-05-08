package be.ugent.tiwi;

import be.ugent.tiwi.dal.IncidentRepository;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.TrafficIncident;
import be.ugent.tiwi.domein.Traject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Jeroen on 02.05.2016
 */
@Controller
@RequestMapping("/incidents")
public class IncidentController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletResponse response) {

        IncidentRepository ir = new IncidentRepository();
        List<TrafficIncident> trafficIncidents = ir.getTrafficIncidents();

        model.addAttribute("trafficIncidents", trafficIncidents);

        //Lijsten met opties toevoegen om te filteren
        List<Provider> providers = ir.getTrafficIncidentsProviders();
        List<Traject> trajecten = ir.getTrafficIncidentsTrajecten();

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


        boolean provFilled = request.getParameter("providers") != null && !request.getParameter("providers").equals("");
        boolean trajFilled = request.getParameter("trajecten") != null && !request.getParameter("trajecten").equals("");
        boolean timestampFilled = request.getParameter("timestamp") != null && !request.getParameter("timestamp").equals("");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        int provider_id = provFilled ? Integer.parseInt(request.getParameter("providers")) : 0;
        int traject_id = trajFilled ? Integer.parseInt(request.getParameter("trajecten")) : 0;
        LocalDateTime timestamp = LocalDateTime.now();


        //LocalDateTime start = startFilled ? LocalDateTime.parse(request.getParameter("startTime"), formatter) : LocalDateTime.of(2001, 1, 1, 1, 1);
        //LocalDateTime end = endFilled ? LocalDateTime.parse(request.getParameter("endTime"), formatter) : LocalDateTime.now();

        if (timestampFilled) {
            try {
                timestamp = LocalDateTime.parse(request.getParameter("timestamp"), formatter);
            } catch (Exception e) {
                timestamp = LocalDateTime.of(2001, 1, 1, 1, 1);
            }
            //Indien provider of traject ingevuld zijn
            if (provFilled || trajFilled) {
                // Zowel op provider als traject
                if (provFilled && trajFilled) {
                    //prov & traj & Datum zijn ingevuld
                    trafficIncidents = ir.getTrafficIncidents(new Provider(provider_id, null, true),
                            new Traject(traject_id, null, 0, 0, null, true, null, null, null, null), timestamp);
                } else {
                    //Enkel op provider
                    if (provFilled) {
                        //prov & dat
                        trafficIncidents = ir.getTrafficIncidents(new Provider(provider_id, null, true), timestamp);
                    } else {
                        //traject & dat
                        trafficIncidents = ir.getTrafficIncidents(new Traject(traject_id, null, 0, 0, null, true, null, null, null, null), timestamp);
                    }
                }
            } else {
                //Datums zijn sowieso ingevuld
                trafficIncidents = ir.getTrafficIncidents(timestamp);
            }
        }else {
            //Indien provider of traject ingevuld zijn
            if (provFilled || trajFilled) {
                // Zowel op provider als traject
                if (provFilled && trajFilled) {
                    //prov & traj & Datum zijn ingevuld
                    trafficIncidents = ir.getTrafficIncidents(new Provider(provider_id, null, true),
                            new Traject(traject_id, null, 0, 0, null, true, null, null, null, null));
                } else {
                    //Enkel op provider
                    if (provFilled) {
                        //prov & dat
                        trafficIncidents = ir.getTrafficIncidents(new Provider(provider_id, null, true));
                    } else {
                        //traject & dat
                        trafficIncidents = ir.getTrafficIncidents(new Traject(traject_id, null, 0, 0, null, true, null, null, null, null));
                    }
                }
            }else{
                trafficIncidents = ir.getTrafficIncidents();
            }
        }

        model.addAttribute("trafficIncidents", trafficIncidents);

        //Lijsten met opties toevoegen om te filteren
        List<Provider> providers = ir.getTrafficIncidentsProviders();
        List<Traject> trajecten = ir.getTrafficIncidentsTrajecten();

        model.addAttribute("trafficIncidentsProv", providers);
        model.addAttribute("trafficIncidentsTraj", trajecten);

        return "home/incidents";
    }

}