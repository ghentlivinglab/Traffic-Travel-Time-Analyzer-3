package be.ugent.tiwi.scraper;


import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.RequestType;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.tomtom.Route;
import be.ugent.tiwi.domein.tomtom.TomTom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

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
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("https://api.tomtom.com/routing/1/calculateRoute/");
            urlBuilder.append(traject.getStart_latitude());
            urlBuilder.append(",");
            urlBuilder.append(traject.getStart_longitude());
            urlBuilder.append(":");
            urlBuilder.append(traject.getEnd_latitude());
            urlBuilder.append(",");
            urlBuilder.append(traject.getEnd_longitude());
            urlBuilder.append("/json?");
            urlBuilder.append("key=");
            urlBuilder.append(this.apiKey);

            lastScrape = System.currentTimeMillis();
            TomTom tomtom = (TomTom) jc.getObject(urlBuilder.toString(), TomTom.class, RequestType.GET);
            LocalDateTime now = LocalDateTime.now();
            for(Route r : tomtom.getRoutes()) {
                int traveltime = r.getSummary().getTravelTimeInSeconds();

                Meting meting = new Meting(tomtomProv, traject, traveltime, now);

                metingen.add(meting);
                try {
                    int diff = (int) (System.currentTimeMillis() - lastScrape);
                    if(diff < 300){
                        Thread.sleep(300-diff);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return metingen;
    }
}
