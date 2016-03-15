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
            String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "&origin=" + traject.getStart_latitude() + "%2C" + traject.getStart_longitude() +
                    "&destination=" + traject.getEnd_latitude() + "%2C" + traject.getEnd_longitude() +
                    "&departure_time=now";
            List<Waypoint> wpts = traject.getWaypoints();
            /*if(wpts.size()>0) {
                url += "&waypoints=via:" + wpts.get(0).getLatitude() + "," + wpts.get(0).getLongitude();
                double size = wpts.size();
                size /= 23; //Maximum toegelaten waypoints voor google
                if (size > 1)
                    for (int i = 1; i < 23; ++i) {
                        int index = (int) Math.round(i * size);
                        url += "%7Cvia:" + wpts.get(index).getLatitude() + "," + wpts.get(index).getLongitude();
                    }
                else
                    for (int i = 1; i < wpts.size(); ++i) {
                        url += "%7Cvia:" + wpts.get(i).getLatitude() + "," + wpts.get(i).getLongitude();
                    }
            }*/
            url += "&key=" + this.apiKey;

            System.out.println(url);
            Google google_obj = jc.makeGoogleCall(url, RequestType.GET);
            if(google_obj.getRoutes().size() > 0) {
                int traveltime = google_obj.getRoutes().get(0).getLegs().get(0).getDurationInTraffic().getValue();
                int basetime = google_obj.getRoutes().get(0).getLegs().get(0).getDuration().getValue();
                int distance = google_obj.getRoutes().get(0).getLegs().get(0).getDistance().getValue();

                System.out.println(traject.getId() + " distance: " + distance + "; travel: " + traveltime + "; basetime: " + basetime);

                Meting meting = new Meting(google, traject, traveltime, basetime, LocalDateTime.now());

                metingen.add(meting);
            }
        }
        return metingen;
    }
}
