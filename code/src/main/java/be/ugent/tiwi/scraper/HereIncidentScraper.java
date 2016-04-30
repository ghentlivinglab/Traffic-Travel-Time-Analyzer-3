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
     * @param traject Een traject waarvan de meting opgehaald moet worden
     * @return Een lijst van lijst van metingen
     */
    @Override
    public TrafficIncident scrape(Traject traject) {
        //TrafficIncident object aanmaken
        TrafficIncident ti = new TrafficIncident();

        Injector injector = Guice.createInjector(new RepositoryModule());
        DatabaseController databaseController = injector.getInstance(DatabaseController.class);
        Provider here = databaseController.haalProviderOp("Here");

        //Centrum van het traject berekenen en deze omzetten naar een quadkey
        int quadkey = this.geo2quadkey(this.getCentre(traject));

        String url = "https://traffic.cit.api.here.com/traffic/6.0/incidents.json" +
                "?app_id=" + this.appId +
                "&app_code=" + this.appCode +
                "&quadkey=" + quadkey +
                "&maxresults=1";

        try {
            HereIncidents hereIncident_obj = (HereIncidents) jc.getObject(url, HereIncidents.class, RequestType.GET);
            if(hereIncident_obj.getTRAFFICITEMS() != null){
                //Er zijn verkeersincidenten gedetecteerd
                ti.setProvider(here);
                ti.setTimestamp(LocalDateTime.now());
                ti.setTraject(traject);
                ti.setProblem(hereIncident_obj.getTRAFFICITEMS().getTRAFFICITEM().get(0).getTRAFFICITEMDESCRIPTION().get(1).getContent());

            }
            else{
                logger.warn("Provider Here: No traffic incidents found for traject " + traject.getId());
                ti = null;
            }
        } catch (InvalidMethodException e) {
            logger.error(e);
        } catch (IOException e) {
            // Service is offline of er kan geen verbinding gemaakt worden
            ti = null;
            logger.error(e);
            logger.warn("Could not connect to Here Incidents service");
        }
        return ti;
    }
}
