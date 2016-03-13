package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.*;
import be.ugent.tiwi.domein.google.Google;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jan on 27.02.16.
 * <p>
 * Account settings
 * email:           vopverkeer3@gmail.com
 * password:        vopverk3
 * API key:         AIzaSyAcbAEzORRjLqSP6I4ZcUzB6YKaNr6X7Fg
 * Sample call:
 * https://maps.googleapis.com/maps/api/directions/json?origin=Brugge&destination=Gent
 * &key=AIzaSyAcbAEzORRjLqSP6I4ZcUzB6YKaNr6X7Fg
 */
public class GoogleScraper implements TrafficScraper {

    private static final Logger logger = LogManager.getLogger(ScheduleController.class);

    private String apiKey;
    private String url;

    public GoogleScraper() {
        this.apiKey = Settings.getSetting("google_apikey");
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

        Provider google = databaseController.haalProviderOp("Google Maps");
        JsonController jc = new JsonController();
        for (Traject traject : trajects) {
            List<Waypoint> waypoints = traject.getWaypoints();
            String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "&origin=" + waypoints.get(0).getLatitude() + "%2C" + waypoints.get(0).getLongitude() +
                    "&destination=" + waypoints.get(waypoints.size()-1).getLatitude() + "%2C" + waypoints.get(waypoints.size()-1).getLongitude() +
                    "&key=" + this.apiKey;
            Google google_obj = jc.makeGoogleCall(url, RequestType.GET);
            int traveltime = google_obj.getRoutes().get(0).getLegs().get(0).getDuration().getValue();
            int basetime = google_obj.getRoutes().get(0).getLegs().get(0).getDuration().getValue();
            //int distance = google_obj.getRoutes().get(0).getLegs().get(0).getDistance().getValue();

            Meting meting = new Meting(google, traject, traveltime, basetime, LocalDateTime.now());

            metingen.add(meting);
        }
        return metingen;
    }
}
