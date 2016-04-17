package be.ugent.tiwi.scraper;


import be.ugent.tiwi.dal.JsonController;
import be.ugent.tiwi.controller.exceptions.InvalidMethodException;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.*;
import be.ugent.tiwi.domein.tomtom.Route;
import be.ugent.tiwi.domein.tomtom.TomTom;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.DependencyModules.RepositoryModule;
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

    private void fixTomTomTrajects(List<Traject> trajects) {
        Traject t;

        //Delete waypoint "51.06306,3.69491" uit traject 1
        t = getTraject(trajects, 1);
        deleteWaypoint(t, "51.06306","3.69491");

        t = getTraject(trajects, 9);
        deleteWaypoint(t, "51.0445", "3.71462");

        t = getTraject(trajects, 12);
        boolean startedDeleting = false;
        for(int i = 0; i < t.getWaypoints().size(); ++i){
            Waypoint w = t.getWaypoints().get(i);
            if(w.getLatitude().equals("51.06054") && w.getLongitude().equals("3.71274"))
                startedDeleting = true;
            if(startedDeleting){
                t.getWaypoints().remove(i);
                if(w.getLatitude().equals("51.05964") && w.getLongitude().equals("3.7116"))
                    break;
                --i;
            }
        }
        deleteWaypoint(t, "51.06054","3.71274");
        deleteWaypoint(t, "51.05964","3.7116");

        t = getTraject(trajects, 14);
        deleteWaypoint(t, "51.04686","3.73424");
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
        fixTomTomTrajects(trajects);

        List<Meting> metingen = new ArrayList<>();
        //Get all trajectories which have coordinates in it
        Injector injector = Guice.createInjector(new RepositoryModule());
        DatabaseController databaseController = injector.getInstance(DatabaseController.class);

        Provider tomtomProv = databaseController.haalProviderOp("TomTom");
        long lastScrape;
        boolean loggedExceededLimit = false;
        for (Traject traject : trajects) {
             /* Url opbouwen */
            StringBuilder url = new StringBuilder("https://api.tomtom.com/routing/1/calculateRoute/");
            url.append(traject.getStart_latitude()).append("%2C").append(traject.getStart_longitude());
            List<Waypoint> wpts = traject.getWaypoints();
            int i;
            if(wpts.size()>0) {
                double size = wpts.size();
                size /= 50; //Maximum toegelaten waypoints voor here
                if(size > 1)
                    for(i = 1; i < 50; ++i){
                        int index = (int) (i * size);
                        url.append("%3A").append(wpts.get(index).getLatitude()).append("%2C").append(wpts.get(index).getLongitude());
                    }
                else
                    for(i = 1; i < wpts.size(); ++i)
                        url.append("%3A").append(wpts.get(i).getLatitude()).append("%2C").append(wpts.get(i).getLongitude());
            }
            url.append("%3A").append(traject.getEnd_latitude()).append("%2C").append(traject.getEnd_longitude()).append("/json?key=").append(this.apiKey);


            // We geven ieder traject een delay van 300ms zodat we niet over het maximum aantal calls per seconde gaan.
            lastScrape = System.currentTimeMillis();
            int diff = (int) (System.currentTimeMillis() - lastScrape);
            try {
                if (diff < 300)
                    Thread.sleep(300 - diff);
            }catch (InterruptedException e) {
                logger.warn("Could not delay the call to the API.");
            }

            try {
                TomTom tomtom = (TomTom) jc.getObject(url.toString(), TomTom.class, RequestType.GET);
                LocalDateTime now = LocalDateTime.now();
                Meting meting = null;
                for (Route r : tomtom.getRoutes()) {
                    int traveltime = r.getSummary().getTravelTimeInSeconds();
                    int distance = r.getSummary().getLengthInMeters();
                    Meting tempM = new Meting(tomtomProv, traject, traveltime, now);
                    if (meting == null || Math.abs(tempM.getTraject().getLengte() - traveltime) < Math.abs(meting.getTraject().getLengte() - traveltime))
                        meting = tempM;
                }

                if (meting != null)
                    metingen.add(meting);
                else {
                    logger.warn("Provider TomTom: Could not scrape traject " + traject.getId() + ", adding an empty measurement [1]");
                    metingen.add(new Meting(tomtomProv, traject, null, LocalDateTime.now()));
                }

            } catch(JsonSyntaxException e) {
                //TomTom is over zijn limiet
                if(!loggedExceededLimit){
                    logger.error("The TomTom-scraper has exceeded its daily limit!");
                    loggedExceededLimit = true;
                }
                logger.warn("Added an empty measurement");
                metingen.add(new Meting(tomtomProv, traject, null, LocalDateTime.now()));
            } catch (IOException e) {
                // Indien de service niet beschikbaar is (of deze machine heeft geen verbinding met de service), mag een leeg traject ingegeven worden.
                Meting meting = new Meting(tomtomProv, traject, null, LocalDateTime.now());
                metingen.add(meting);
                logger.warn("Added an empty measurement: ", e);
            } catch (InvalidMethodException e) {
                // Zou nooit mogen gebeuren
                e.printStackTrace();
            }
        }
        return metingen;
    }
}
