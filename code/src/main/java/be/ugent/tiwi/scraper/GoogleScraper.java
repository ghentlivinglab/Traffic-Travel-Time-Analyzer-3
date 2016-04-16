package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.exceptions.InvalidMethodException;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.*;
import be.ugent.tiwi.domein.google.Google;
import be.ugent.tiwi.domein.google.Leg;
import be.ugent.tiwi.domein.google.Route;
import be.ugent.tiwi.domein.google.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jan on 27.02.16.
 */
public class GoogleScraper extends TrafficScraper {

    private static final Logger logger = LogManager.getLogger(GoogleScraper.class);

    private final String apiKey;

    /**
     * Constructor van de klasse
     */
    public GoogleScraper() {
        this.apiKey = Settings.getSetting("google_apikey");
    }

    /**
     * Past de lijst trajects eerst aan zodat de {@link GoogleScraper} geen verkeerde waypoints meegeeft. Daarna wordt
     * via de Google Directions API van alle trajecten de huidige reistijd afgehaald. Deze metingen worden teruggegeven.
     *
     * @param trajects Een lijst van trajecten waarvan een meting moet worden opgehaald
     * @return Een lijst metingen
     * @see Traject
     * @see Meting
     */
    @Override
    public List<Meting> scrape(List<Traject> trajects) {
        fixGoogleTrajects(trajects);
        return makeCall(trajects);
    }

    /**
     * Pas de trajecten aan zodat de {@link GoogleScraper} straks geen problemen zal hebben.
     *
     * @param trajects Een lijst trajecten met waypoints
     */
    private void fixGoogleTrajects(List<Traject> trajects) {
        Traject t;

        t = getTraject(trajects, 1);
        editWaypoint(t, "51.063995", "3.695596", "51.064", "3.6958");

        //Delete waypoint "51.06634,3.69966" uit traject 4
        t = getTraject(trajects, 4);
        deleteWaypoint(t, "51.06634", "3.69966");

        //Delete waypoint "51.05844,3.73749" uit traject 5
        t = getTraject(trajects, 5);
        deleteWaypoint(t, "51.05844", "3.73749");

        //Edit waypoint "51.05071,3.73347" to "51.05076,3.73343" uit traject 14
        t = getTraject(trajects, 14);
        editWaypoint(t, "51.05071", "3.73347", "51.05076", "3.73343");
        //Edit waypoint "51.04576,3.73364" to "51.04576,3.73472" uit traject 14
        //editWaypoint(t, "51.04576","3.73364", "51.04576","3.73472");
        //Delete waypoint "51.18759,3.83256" uit traject 16
        deleteWaypoint(getTraject(trajects, 16), "51.18759", "3.83256");

        //Edit end-lat/long to last of waypoints & delete last waypoint uit traject 18
        t = getTraject(trajects, 18);
        Waypoint w = t.getWaypoints().get(t.getWaypoints().size() - 1);
        t.setEnd_latitude(w.getLatitude());
        t.setEnd_longitude(w.getLongitude());
        t.getWaypoints().remove(w);

        //Delete waypoint "51.07737,3.78164" uit traject 25
        t = getTraject(trajects, 25);
        deleteWaypoint(t, "51.07737", "3.78164");

        //Edit waypoint "51.07735,3.78175" to "51.07705,3.78140" uit traject 26
        t = getTraject(trajects, 26);
        editWaypoint(t, "51.07735", "3.78175", "51.07705", "3.78140");

        //Edit waypoint "51.07735,3.78175" to "51.0217109,3.733007" uit traject 28
        t = getTraject(trajects, 28);
        t.setEnd_latitude("51.0217109");
        t.setEnd_longitude("3.733007");
    }

    private List<Meting> makeCall(List<Traject> trajects) {

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
            if (wpts.size() > 0) {
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
            try {
                Google google_obj = jc.makeGoogleCall(url, RequestType.GET);
                String urlStaticMaps = "";
                char c = 'A';
                double percent = 0, distance = 0;
                if (google_obj.getRoutes().size() > 0) {
                    Route r = google_obj.getRoutes().get(0);
                    urlStaticMaps = "https://maps.googleapis.com/maps/api/staticmap?size=720x720&zoom=17&center=" + r.getLegs().get(0).getStartLocation().getLat() + "," + r.getLegs().get(0).getStartLocation().getLng();

                    for (int i = 0; i < r.getLegs().size(); ++i) {
                        Leg l = r.getLegs().get(i);
                        percent = Math.abs(l.getDistance().getValue() - traject.getLengte());
                        percent /= traject.getLengte();
                        distance = l.getDistance().getValue();
                        urlStaticMaps += "&markers=label:" + c++ + "%7C";
                        urlStaticMaps += l.getStartLocation().getLat() + "," + l.getStartLocation().getLng();

                        double lastLat = 0;
                        for (Step s : l.getSteps()) {
                            urlStaticMaps += "&markers=label:" + c++ + "%7C";
                            urlStaticMaps += s.getEndLocation().getLat() + "," + s.getEndLocation().getLng();
                            lastLat = s.getEndLocation().getLat();
                        }

                        if (i == r.getLegs().size() - 1 && lastLat != l.getEndLocation().getLat()) {
                            urlStaticMaps += "&markers=label:" + c++ + "%7C";
                            urlStaticMaps += l.getEndLocation().getLat() + "," + l.getEndLocation().getLng();
                        }
                    }
                    urlStaticMaps += "&key=AIzaSyAUdGuaEwMa-gnMK1NbjgnChdwwdMv4WsQ";

                    int traveltime = google_obj.getRoutes().get(0).getLegs().get(0).getDurationInTraffic().getValue();
                    Meting meting = new Meting(google, traject, traveltime, LocalDateTime.now());
                    metingen.add(meting);
                } else {
                    StringBuilder b = new StringBuilder();
                    b.append("Empty response: " + traject.getId() + " - waypoints: [").append(addedWaypoints.get(0).getLatitude()).append(",").append(addedWaypoints.get(0).getLongitude());
                    for (int i = 1; i < addedWaypoints.size(); ++i)
                        b.append(";").append(addedWaypoints.get(i).getLatitude()).append(",").append(addedWaypoints.get(i).getLongitude());
                    b.append("]");
                    logger.warn(b.toString());
                    Meting meting = new Meting(google, traject, null, LocalDateTime.now());
                    metingen.add(meting);
                }
                if (percent > 0.02) {
                    logger.warn(traject.getId() + " - " + traject.getLengte() + " | " + distance + " | " + percent);
                    logger.debug(urlStaticMaps);
                    logger.debug(url);
                    c = 'A';
                    urlStaticMaps = "https://maps.googleapis.com/maps/api/staticmap?size=720x720&zoom=17&center=" + traject.getStart_latitude() + "," + traject.getStart_longitude();

                    if (wpts.size() > 0) {
                        addedWaypoints.add(wpts.get(0));
                        double size = wpts.size();
                        size /= 23; //Maximum toegelaten waypoints voor google
                        if (size > 1)
                            for (int i = 1; i < 23; ++i) {
                                int index = (int) Math.round(i * size);
                                urlStaticMaps += "&markers=label:" + c++ + "%7C";
                                urlStaticMaps += wpts.get(index).getLatitude() + "," + wpts.get(index).getLongitude();
                            }
                        else
                            for (int i = 1; i < wpts.size(); ++i) {
                                urlStaticMaps += "&markers=label:" + c++ + "%7C";
                                urlStaticMaps += wpts.get(i).getLatitude() + "," + wpts.get(i).getLongitude();
                            }
                    }
                    urlStaticMaps += "&key=AIzaSyAUdGuaEwMa-gnMK1NbjgnChdwwdMv4WsQ";
                    logger.debug(urlStaticMaps);
                }
            } catch (InvalidMethodException e) {
                logger.error(e);
            } catch (IOException e) {
                // Indien de service niet beschikbaar is (of deze machine heeft geen verbinding met de service), mag een leeg traject ingegeven worden.
                Meting meting = new Meting(google, traject, null, LocalDateTime.now());
                metingen.add(meting);
                logger.error(e);
                logger.warn("Added an empty measurement");
            }
        }
        return metingen;
    }
}
