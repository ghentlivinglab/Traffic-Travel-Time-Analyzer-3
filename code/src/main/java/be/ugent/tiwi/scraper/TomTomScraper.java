package be.ugent.tiwi.scraper;


import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.exceptions.InvalidMethodException;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.*;
import be.ugent.tiwi.domein.tomtom.Route;
import be.ugent.tiwi.domein.tomtom.TomTom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Created on 01.03.16.
 * <p>
 * Account settings
 * email:           vopverkeer3@gmail.com
 * username:        vopverkeer3
 * password:        vopverk31
 * API key:         AIzaSyAcbAEzORRjLqSP6I4ZcUzB6YKaNr6X7Fg
 * Sample call: https://api.tomtom.com/routing/1/calculateRoute/52.509317,13.429368:52.502746,13.438724/json?key=8yafwthpctekty68x3kbae4h
 * <p>
 * <p>
 * Online Routing
 * <p>
 * Key: 8yafwthpctekty68x3kbae4h
 * <p>
 * Application: vopverkeer3 Key: 8yafwthpctekty68x3kbae4h Status: active Registered: 12 seconds ago
 * Key Rate Limits
 * 5	Calls per second
 * 1,000	Calls per day
 * Online Search
 * <p>
 * Key: naaqavj3jt4v6q74huk6wvfy
 * <p>
 * Application: vopverkeer3 Key: naaqavj3jt4v6q74huk6wvfy Status: active Registered: 12 seconds ago
 * Key Rate Limits
 * 5	Calls per second
 * 1,000	Calls per day
 * Online Traffic Flow
 * <p>
 * Key: 5jsqf2j9zjwerqudwcm8kn2f
 * <p>
 * Application: vopverkeer3 Key: 5jsqf2j9zjwerqudwcm8kn2f Status: active Registered: 12 seconds ago
 * Key Rate Limits
 * 1,000	Calls per second
 * 10,000	Calls per day
 * Online Traffic Incidents
 * <p>
 * Key: 5c64tkvt8w424hj8a6afxw3t
 * <p>
 * Application: vopverkeer3 Key: 5c64tkvt8w424hj8a6afxw3t Shared Secret: q5qt2mZ Status: active Registered: 11 seconds ago
 * Key Rate Limits
 * 30	Calls per second
 * 5,000	Calls per day
 */
public class TomTomScraper extends TrafficScraper {

    private String apiKey;
    private JsonController<TomTom> jc;
    private static final Logger logger = LogManager.getLogger(TomTomScraper.class);


    public TomTomScraper() {
        this.apiKey = Settings.getSetting("tomtom_apikey");
        this.jc = new JsonController<TomTom>();
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

        Provider tomtomProv = databaseController.haalProviderOp("TomTom");
        JsonController jc = new JsonController();
        long lastScrape;
        for (Traject traject : trajects) {
             /* https://<baseURL>/routing/<versionNumber>/calculateRoute/<locations>[/<contentType>]?key=<apiKey>
              * [&routeType=<routeType>][&traffic=<boolean>][&avoid=<avoidType>][&instructionsType=<instructionsType>]
              * [&departAt=<time>][&maxAlternatives=<alternativeRoutes>][&computeBestOrder=<boolean>]
              * [&routeRepresentation=<routeRepresentation>][&travelMode=<travelMode>][&callback=<callback>]
              * More info: http://developer.tomtom.com/products/onlinenavigation/onlinerouting/Documentation#Request
             */
            String url = "https://api.tomtom.com/routing/1/calculateRoute/" +
                    traject.getStart_latitude() + "%2C" + traject.getStart_longitude();
            List<Waypoint> wpts = traject.getWaypoints();
            int i;
            if(wpts.size()>0) {
                double size = wpts.size();
                size /= 50; //Maximum toegelaten waypoints voor here
                if(size > 1)
                    for(i = 1; i < 50; ++i){
                        int index = (int) (i * size);
                        url += "%3A" + wpts.get(index).getLatitude() + "%2C" + wpts.get(index).getLongitude();
                    }
                else
                    for(i = 1; i < wpts.size(); ++i)
                        url += "%3A" + wpts.get(i).getLatitude() + "%2C" + wpts.get(i).getLongitude();
            }
            url +=  "%3A" + traject.getEnd_latitude() + "%2C" + traject.getEnd_longitude() +
                    "/json?key=" + this.apiKey;

            lastScrape = System.currentTimeMillis();
            try {
                TomTom tomtom = (TomTom) jc.getObject(url, TomTom.class, RequestType.GET);
                LocalDateTime now = LocalDateTime.now();
                if (tomtom.getRoutes().size() > 0) {
                    String urlStaticMaps;

                    int traveltime = tomtom.getRoutes().get(0).getSummary().getTravelTimeInSeconds();
                    Meting meting = new Meting(tomtomProv, traject, traveltime, LocalDateTime.now());
                    metingen.add(meting);
                } else {
                    logger.warn("Provider TomTom: Could not scrape traject " + traject.getId() + ", adding an empty measurement [1]");
                    metingen.add(new Meting(tomtomProv, traject, -1, LocalDateTime.now()));
                }

                /*
                for (Route r : tomtom.getRoutes()) {
                    int traveltime = r.getSummary().getTravelTimeInSeconds();

                    Meting meting = new Meting(tomtomProv, traject, traveltime, now);

                    metingen.add(meting);
                    try {
                        int diff = (int) (System.currentTimeMillis() - lastScrape);
                        if (diff < 300) {
                            Thread.sleep(300 - diff);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }*/
            } catch (InvalidMethodException e) {
                logger.error(e);
            } catch (IOException e) {
                // Indien de service niet beschikbaar is (of deze machine heeft geen verbinding met de service), mag een leeg traject ingegeven worden.
                Meting meting = new Meting(tomtomProv, traject, -1, LocalDateTime.now());
                metingen.add(meting);
                logger.error(e);
                logger.warn("Added an empty measurement");
            }
        }
        return metingen;
    }
}
