package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
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

    private static final Logger logger = LogManager.getLogger(GoogleScraper.class);

    private String apiKey;

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
            List<Waypoint> addedWaypoints = new ArrayList<>();
            String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "&origin=" + traject.getStart_latitude() + "%2C" + traject.getStart_longitude() +
                    "&destination=" + traject.getEnd_latitude() + "%2C" + traject.getEnd_longitude() +
                    "&departure_time=now";
            List<Waypoint> wpts = traject.getWaypoints();
            if(wpts.size()>0) {
                url += "&waypoints=via:" + wpts.get(0).getLatitude() + "," + wpts.get(0).getLongitude();
                addedWaypoints.add(wpts.get(0));
                double size = wpts.size();
                size /= 23; //Maximum toegelaten waypoints voor google
                if (size > 1)
                    for (int i = 1; i < 23; ++i) {
                        int index = (int) Math.round(i * size);
                        url += "%7Cvia:" + wpts.get(index).getLatitude() + "," + wpts.get(index).getLongitude();
                        addedWaypoints.add(wpts.get(index));
                    }
                else
                    for (int i = 1; i < wpts.size(); ++i) {
                        url += "%7Cvia:" + wpts.get(i).getLatitude() + "," + wpts.get(i).getLongitude();
                        addedWaypoints.add(wpts.get(i));
                    }
            }
            url += "&key=" + this.apiKey;

            Google google_obj = jc.makeGoogleCall(url, RequestType.GET);
            if(google_obj.getRoutes().size() > 0) {
                int traveltime = google_obj.getRoutes().get(0).getLegs().get(0).getDurationInTraffic().getValue();
                Meting meting = new Meting(google, traject, traveltime, LocalDateTime.now());
                metingen.add(meting);
            }else{
                StringBuilder b = new StringBuilder();
                b.append("Empty response: waypoints: [").append(addedWaypoints.get(0).getLatitude()).append(",").append(addedWaypoints.get(0).getLongitude());
                for(int i = 1; i < addedWaypoints.size(); ++i)
                    b.append(";").append(addedWaypoints.get(i).getLatitude()).append(",").append(addedWaypoints.get(i).getLongitude());
                b.append("]");
                logger.warn(b.toString());
                Meting meting = new Meting(google, traject, -1, LocalDateTime.now());
                metingen.add(meting);
            }
        }
        return metingen;
    }
}
