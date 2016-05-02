package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.exceptions.InvalidMethodException;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.dal.JsonController;
import be.ugent.tiwi.domein.*;
import be.ugent.tiwi.domein.hereIncident.HereIncidents;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.DependencyModules.RepositoryModule;
import settings.Settings;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeroen on 30.04.16.
 */
public class HereIncidentScraper extends IncidentScraper {
    private static final Logger logger = LogManager.getLogger(HereIncidentScraper.class);
    private final String appId;
    private final String appCode;
    private JsonController<HereIncidents> jc;


    /**
     * Constructor van de klasse
     */
    public HereIncidentScraper() {
        this.appId = Settings.getSetting("here_appid");
        this.appCode = Settings.getSetting("here_appcode");
        this.jc = new JsonController<HereIncidents>();
    }

    /**
     * Via de Here API wordt een verkeersprobleem opgevraagd voor een specifiek traject
     *
     * @param trajects Een lijst van trajecten waarvan de meting opgehaald moet worden
     * @return Een lijst van problemen
     */
    @Override
    public List<TrafficIncident> scrape(List<Traject> trajects) {
        //TrafficIncident object aanmaken
        List<TrafficIncident> trafficIncidents = new ArrayList<>();

        TrafficIncident ti = new TrafficIncident();

        Injector injector = Guice.createInjector(new RepositoryModule());
        DatabaseController databaseController = injector.getInstance(DatabaseController.class);
        Provider here = databaseController.haalProviderOp("Here");

        for (Traject traject : trajects) {
            //Centrum van het traject berekenen en deze omzetten naar een quadkey
            double[] normalizedMercator = this.geo2NorMercator(this.getCentre(traject));

            String url = "https://traffic.cit.api.here.com/traffic/6.0/incidents/json/"
                    + (int)normalizedMercator[2] + "/" + (int)normalizedMercator[0] + "/" + (int)normalizedMercator[1]
                    + "?app_id=" + this.appId
                    + "&app_code=" + this.appCode
                    + "&maxresults=1";

            try {
                HereIncidents hereIncident_obj = (HereIncidents) jc.getObject(url, HereIncidents.class, RequestType.GET);
                if (hereIncident_obj.getTRAFFICITEMS() != null) {
                    //Er zijn verkeersincidenten gedetecteerd
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

                    ti.setProvider(here);
                    ti.setTimestamp(LocalDateTime.parse(hereIncident_obj.getTRAFFICITEMS().getTRAFFICITEM().get(0).getENTRYTIME(), formatter));
                    ti.setTraject(traject);
                    ti.setProblem(hereIncident_obj.getTRAFFICITEMS().getTRAFFICITEM().get(0).getTRAFFICITEMDESCRIPTION().get(0).getContent());
                    trafficIncidents.add(ti);
                } else {
                    logger.warn("Provider Here: No traffic incidents found for traject " + traject.getId());
                }
            } catch (InvalidMethodException e) {
                logger.error(e);
            } catch (IOException e) {
                // Service is offline of er kan geen verbinding gemaakt worden
                logger.error(e);
                logger.warn("Could not connect to Here Incidents service");
            }
        }
        return trafficIncidents;
    }
}
