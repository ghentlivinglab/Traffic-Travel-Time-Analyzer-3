package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.exceptions.InvalidMethodException;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.RequestType;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
import be.ugent.tiwi.domein.here.Here;
import be.ugent.tiwi.domein.here.Route;
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
    private static final Logger logger = LogManager.getLogger(HereScraper.class);
    private String appId;
    private String appCode;
    private String url;
    private JsonController<Here> jc;


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
     *
     * @param trajects Een lijst van trajecten waarvan een meting moet woren opgehaald
     * @return Een lijst van lijst van metingen
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
                    "&waypoint0=geo!" + traject.getStart_latitude() + "%2C" + traject.getStart_longitude();
            List<Waypoint> wpts = traject.getWaypoints();
            int i = 1;
            if(wpts.size()>0) {
                double size = wpts.size();
                size /= 32; //Maximum toegelaten waypoints voor here
                if(size > 1)
                    for(i = 1; i < 32; ++i){
                        int index = (int) (i * size);
                        url += "&waypoint" + i + "=geo!" + wpts.get(index).getLatitude() + "%2C" + wpts.get(index).getLongitude();
                    }
                else
                    for(i = 1; i < wpts.size(); ++i)
                        url += "&vWp." + i + "=" + wpts.get(i).getLatitude() + "," + wpts.get(i).getLongitude();
            }
            url += "&waypoint" + i + "=geo!" + traject.getEnd_latitude() + "%2C" + traject.getEnd_longitude() +
                    "&mode=fastest%3Bcar%3Btraffic%3Aenabled";
            try {
                Here here_obj = (Here) jc.getObject(url, Here.class, RequestType.GET);
                if(here_obj.getResponse() != null) {
                    String urlStaticMaps = "https://image.maps.cit.api.here.com/mia/1.6/routing?" +
                            "app_id=" + this.appId +
                            "&app_code=" + this.appCode +
                            "&waypoint0=geo!" + traject.getStart_latitude() + "%2C" + traject.getStart_longitude();
                    Route route = here_obj.getResponse().getRoute().get(0);
                    for (be.ugent.tiwi.domein.here.Waypoint point : route.getWaypoint()) {
                        urlStaticMaps += "&waypoint" + i++ + "=geo!" + point.getMappedPosition().getLatitude() + "%2C" + point.getMappedPosition().getLongitude();
                    }

                    int traveltime = here_obj.getResponse().getRoute().get(0).getSummary().getTravelTime();
                    Meting meting = new Meting(here, traject, traveltime, LocalDateTime.now());
                    metingen.add(meting);
                } else {
                    logger.warn("Provider Here: Could not scrape traject " + traject.getId() + ", adding an empty measurement [1]");
                    metingen.add(new Meting(here, traject, -1, LocalDateTime.now()));
                }
            } catch (InvalidMethodException e) {
                logger.error(e);
            } catch (IOException e) {
                // Indien de service niet beschikbaar is (of deze machine heeft geen verbinding met de service), mag een leeg traject ingegeven worden.
                Meting meting = new Meting(here, traject, null, LocalDateTime.now());
                metingen.add(meting);
                logger.error(e);
                logger.warn("Added an empty measurement");
            }
        }
        return metingen;
    }
}
