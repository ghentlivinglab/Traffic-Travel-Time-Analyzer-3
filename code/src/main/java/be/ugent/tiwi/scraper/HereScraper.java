package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.exceptions.InvalidMethodException;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.RequestType;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.here.Here;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jelle on 19.02.16.
 */
public class HereScraper extends TrafficScraper {
    private String appId;
    private String appCode;
    private String url;
    private JsonController<Here> jc;
    private static final Logger logger = LogManager.getLogger(HereScraper.class);


    /**
     * Constructor van de klasse
     */
    public HereScraper() {
        this.appId = Settings.getSetting("here_appid");
        this.appCode = Settings.getSetting("here_appcode");
        this.jc = new JsonController<Here>();
    }

    /**
     * Via de Here API worden van alle trajecten de huidige reistijd afgehaald. Deze metingen worden teruggegeven.
     * @param trajects Een lijst van trajecten waarvan een meting moet woren opgehaald
     * @return         Een lijst van lijst van metingen
     */
    @Override
    public List<Meting> scrape(List<Traject> trajects) {
        List<Meting> metingen = new ArrayList<>();

        DatabaseController databaseController = new DatabaseController();
        Provider here = databaseController.haalProviderOp("Here");
        JsonController jc = new JsonController();
        for (Traject traject : trajects) {
            String url = "https://route.cit.api.here.com/routing/7.2/calculateroute.json?" +
                    "app_id=" + this.appId +
                    "&app_code=" + this.appCode +
                    "&waypoint0=geo!" + traject.getStart_latitude() + "%2C" + traject.getStart_longitude() +
                    "&waypoint1=geo!" + traject.getEnd_latitude() + "%2C" + traject.getEnd_longitude() +
                    "&mode=fastest%3Bcar%3Btraffic%3Aenabled";
            try {
                Here here_obj = (Here) jc.getObject(url, Here.class, RequestType.GET);
                int traveltime = here_obj.getResponse().getRoute().get(0).getSummary().getTravelTime();

                Meting meting = new Meting(here, traject, traveltime, LocalDateTime.now());

                metingen.add(meting);
            } catch (InvalidMethodException e) {
                logger.error(e);
            } catch (IOException e) {
                // Indien de service niet beschikbaar is (of deze machine heeft geen verbinding met de service), mag een leeg traject ingegeven worden.
                Meting meting = new Meting(here, traject, -1, LocalDateTime.now());
                metingen.add(meting);
                logger.error(e);
                logger.warn("Added an empty measurement");
            }
        }
        return metingen;
    }
}
