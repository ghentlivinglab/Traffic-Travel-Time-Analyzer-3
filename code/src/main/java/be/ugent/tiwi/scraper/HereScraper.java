package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.*;
import be.ugent.tiwi.domein.here.Here;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jelle on 19.02.16.
 * <p>
 * Account settings
 * email:           jelle.debock@ugent.be
 * password:        vop_project_groep3
 * App ID:          tsliJF6nV8gV1CCk7yK8
 * App CODE:        o8KURFHJC02Zzlv8HTifkg
 * Expiry:          May 19, 2016
 * Sample call:
 * https://route.cit.api.here.com/routing/7.2/calculateroute.json?
 * app_id=tsliJF6nV8gV1CCk7yK8&app_code=o8KURFHJC02Zzlv8HTifkg
 * &waypoint0=geo!51.040800%2C3.614126&waypoint1=geo!51.038736%2C3.736503
 * &mode=fastest%3Bcar%3Btraffic%3Aenabled
 */
public class HereScraper implements TrafficScraper {
    private String appId;
    private String appCode;
    private String url;
    private JsonController<Here> jc;
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);


    public HereScraper() {
        this.appId = Settings.getSetting("here_appid");
        this.appCode = Settings.getSetting("here_appcode");
        this.jc = new JsonController<Here>();
    }

    @Override
    public List<Meting> scrape(List<Traject> trajects) {
        return makeCall(trajects);
    }

    /**
     * here the actual rest call is made
     */
    public List<Meting> makeCall(List<Traject> trajects) {
        List<Meting> metingen = new ArrayList<>();
        //Get all trajectories which have coordinates in it
        DatabaseController databaseController = new DatabaseController();

        Provider here = databaseController.haalProviderOp("Here");
        JsonController jc = new JsonController();
        for (Traject traject : trajects) {
            List<Waypoint> waypoints = traject.getWaypoints();
            String url = "https://route.cit.api.here.com/routing/7.2/calculateroute.json?" +
                    "app_id=" + this.appId +
                    "&app_code=" + this.appCode +
                    "&waypoint0=geo!" + waypoints.get(0).getLatitude() + "%2C" + waypoints.get(0).getLongitude() +
                    "&waypoint1=geo!" + waypoints.get(waypoints.size()-1).getLatitude() + "%2C" + waypoints.get(waypoints.size()-1).getLongitude() +
                    "&mode=fastest%3Bcar%3Btraffic%3Aenabled";
            Here here_obj = (Here) jc.getObject(url, Here.class, RequestType.GET);
            int traveltime = here_obj.getResponse().getRoute().get(0).getSummary().getTravelTime();
            int basetime = here_obj.getResponse().getRoute().get(0).getSummary().getBaseTime();
            int distance = here_obj.getResponse().getRoute().get(0).getSummary().getDistance();

            Meting meting = new Meting(here, traject, traveltime, basetime, LocalDateTime.now());

            metingen.add(meting);
        }
        return metingen;
    }
}
