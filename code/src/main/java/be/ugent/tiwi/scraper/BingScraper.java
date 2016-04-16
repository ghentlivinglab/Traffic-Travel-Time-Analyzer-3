package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.exceptions.InvalidMethodException;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.*;
import be.ugent.tiwi.domein.bing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eigenaar on 15/03/2016.
 */
public class BingScraper extends TrafficScraper {

    private static final Logger logger = LogManager.getLogger(BingScraper.class);

    private final String apiKey;

    public BingScraper() {
        this.apiKey = Settings.getSetting("bing_apikey");
    }

    public void fixBingTrajects(List<Traject> trajects) {
        Traject t;

        //Edit waypoint "51.06378,3.69559" to "51.063995,3.695596" uit traject 1
        editWaypoint(getTraject(trajects, 1), "51.06378", "3.69559", "51.063995", "3.695596");

        //Edit waypoint "51.05258,3.69949" to "51.05278,3.69949" uit traject 1
        editWaypoint(getTraject(trajects, 10), "51.05258", "3.69949", "51.05278", "3.69949");

        //Edit start-lat/long to "51.0372705,3.737777" uit traject 13
        t = getTraject(trajects, 13);
        t.setStart_latitude("51.03727");
        t.setStart_longitude("3.737777");

        //Edit waypoint "51.04867,3.73406" to "51.04737,3.73395" uit traject 13
        editWaypoint(t, "51.04867", "3.73406", "51.04737", "3.73395");

        //Edit start-long to "3.733050" uit traject 28
        getTraject(trajects, 28).setEnd_longitude("3.733050");
    }

    @Override
    public List<Meting> scrape(List<Traject> trajects) {
        //Fix any coordinates that are placed wrongfully
        fixBingTrajects(trajects);

        List<Meting> metingen = new ArrayList<>();
        //Get all trajectories which have coordinates in it
        DatabaseController databaseController = new DatabaseController();

        Provider bing = databaseController.haalProviderOp("Bing Maps");
        JsonController jc = new JsonController();
        for (Traject traject : trajects) {
            String url = "http://dev.virtualearth.net/REST/V1/Routes/Driving?o=json&wp.0=" +
                    traject.getStart_latitude() + "," + traject.getStart_longitude();
            List<Waypoint> wpts = traject.getWaypoints();
            int i = 1;
            if (wpts.size() > 0) {
                double size = wpts.size();
                size /= 10; //Maximum toegelaten ViaWaypoints
                if (size > 1)
                    for (i = 1; i < 10; ++i) {
                        int index = (int) (i * size);
                        url += "&vWp." + i + "=" + wpts.get(index).getLatitude() + "," + wpts.get(index).getLongitude();
                    }
                else
                    for (i = 1; i < wpts.size(); ++i)
                        url += "&vWp." + i + "=" + wpts.get(i).getLatitude() + "," + wpts.get(i).getLongitude();
            }
            url += "&wp." + i + "=" + traject.getEnd_latitude() + "," + traject.getEnd_longitude();
            url += "&optimize=timeWithTraffic&maxSolutions=1&mfa=1&key=" + apiKey;
            try {
                Bing bing_obj = (Bing) jc.getObject(url, Bing.class, RequestType.GET);
                if (bing_obj.getResourceSets().size() > 0) {
                    ResourceSet set = bing_obj.getResourceSets().get(0);
                    if (set.getResources().size() > 0) {
                        Resource resource = set.getResources().get(0);
                        int distance = (int) Math.round(resource.getTravelDistance() * 1000);
                        String urlStaticMaps = "http://dev.virtualearth.net/REST/v1/Imagery/Map/Road/" + traject.getStart_latitude() + "," + traject.getStart_longitude() + "/17/Routes/Driving?waypoint.1=" + traject.getStart_latitude() + "," + traject.getStart_longitude();
                        i = 2;
                        for (RouteLeg leg : resource.getRouteLegs()) {

                            for (ItineraryItem item : leg.getItineraryItems()) {
                                ManeuverPoint point = item.getManeuverPoint();
                                urlStaticMaps += "&waypoint." + i++ + "=";
                                urlStaticMaps += point.getCoordinates().get(0) + "," + point.getCoordinates().get(1);
                            }
                        }
                        urlStaticMaps += "&mapSize=720,720&key=" + this.apiKey;


                        double percent = Math.abs(traject.getLengte() - distance);
                        percent /= traject.getLengte();
                        if (percent > 0.2) {
                            logger.warn(traject.getId() + " - " + traject.getLengte() + " | " + distance + " | " + Math.abs(traject.getLengte() - distance) + " | " + percent);
                            logger.debug(urlStaticMaps);
                            urlStaticMaps = "http://dev.virtualearth.net/REST/v1/Imagery/Map/Road/" + traject.getStart_latitude() + "," + traject.getStart_longitude() + "/17/Routes/Driving?waypoint.1=" + traject.getStart_latitude() + "," + traject.getStart_longitude();

                            if (wpts.size() > 0) {
                                double size = wpts.size();
                                size /= 10; //Maximum toegelaten ViaWaypoints
                                if (size > 1)
                                    for (i = 1; i < 10; ++i) {
                                        int index = (int) (i * size);
                                        urlStaticMaps += "&waypoint." + (i + 1) + "=" + wpts.get(index).getLatitude() + "," + wpts.get(index).getLongitude();
                                    }
                                else
                                    for (i = 1; i < wpts.size(); ++i)
                                        urlStaticMaps += "&waypoint." + (i + 1) + "=" + wpts.get(i).getLatitude() + "," + wpts.get(i).getLongitude();
                            }

                            urlStaticMaps += "&mapSize=720,720&key=" + this.apiKey;
                            logger.debug(urlStaticMaps);
                            logger.debug(url);
                        }


                        Meting m = new Meting(bing, traject, resource.getTravelDurationTraffic(), LocalDateTime.now());
                        metingen.add(m);
                    } else {
                        logger.warn("Provider Bing: Could not scrape traject " + traject.getId() + ", adding an empty measurement [1]");
                        metingen.add(new Meting(bing, traject, null, LocalDateTime.now()));
                    }
                } else {
                    logger.warn("Provider Bing: Could not scrape traject " + traject.getId() + ", adding an empty measurement [2]");
                    metingen.add(new Meting(bing, traject, null, LocalDateTime.now()));
                }
            } catch (InvalidMethodException e) {
                logger.error(e);
            } catch (IOException e) {
                // Indien de service niet beschikbaar is (of deze machine heeft geen verbinding met de service), mag een leeg traject ingegeven worden.
                Meting meting = new Meting(bing, traject, null, LocalDateTime.now());
                metingen.add(meting);
                logger.error(e);
                logger.warn("Added an empty measurement");
            }

        }
        return metingen;
    }
}
