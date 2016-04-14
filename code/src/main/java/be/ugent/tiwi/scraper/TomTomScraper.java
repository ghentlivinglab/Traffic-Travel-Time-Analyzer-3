package be.ugent.tiwi.scraper;


import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.exceptions.InvalidMethodException;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.RequestType;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.tomtom.Route;
import be.ugent.tiwi.domein.tomtom.TomTom;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Created on 01.03.16.
 */
public class TomTomScraper extends TrafficScraper {

    private static final Logger logger = LogManager.getLogger(TomTomScraper.class);
    private String apiKey;
    private JsonController<TomTom> jc;


    public TomTomScraper() {
        this.apiKey = Settings.getSetting("tomtom_apikey");
        this.jc = new JsonController<TomTom>();
    }

    /**
     * Haalt per traject de huidige reistijd op van de TomTom provider. Deze reistijden worden als metingen
     * teruggestuurd.
     *
     * @param trajects Een lijst van trajecten waarvan een meting moet woren opgehaald
     * @return Een lijst van metingen
     */
    @Override
    public List<Meting> scrape(List<Traject> trajects) {
        List<Meting> metingen = new ArrayList<>();
        //Get all trajectories which have coordinates in it
        DatabaseController databaseController = new DatabaseController();

        Provider tomtomProv = databaseController.haalProviderOp("TomTom");
        JsonController jc = new JsonController();
        long lastScrape;
        boolean exceededLimit = false;
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
            try {
                TomTom tomtom = (TomTom) jc.getObject(urlBuilder.toString(), TomTom.class, RequestType.GET);
                LocalDateTime now = LocalDateTime.now();
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
                }
            } catch(JsonSyntaxException e) {
                //TomTom is over zijn limiet
                if(!exceededLimit){
                    logger.error("The TomTom-scraper has exceeded its daily limit!");
                    exceededLimit = true;
                }
                logger.warn("Added an empty measurement");
                metingen.add(new Meting(tomtomProv, traject, null, LocalDateTime.now()));
            }catch (InvalidMethodException e) {
                logger.error(e);
            } catch (IOException e) {
                // Indien de service niet beschikbaar is (of deze machine heeft geen verbinding met de service), mag een leeg traject ingegeven worden.
                Meting meting = new Meting(tomtomProv, traject, null, LocalDateTime.now());
                metingen.add(meting);
                logger.error(e);
                logger.warn("Added an empty measurement");
            }
        }
        return metingen;
    }
}
