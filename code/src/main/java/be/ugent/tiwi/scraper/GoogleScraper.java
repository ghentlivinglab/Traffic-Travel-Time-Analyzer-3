package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.dal.MetingRepository;
import be.ugent.tiwi.dal.ProviderRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.RequestType;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.google.Google;
import settings.Settings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jan on 27.02.16.
 *
 * Account settings
 * email:           vopverkeer3@gmail.com
 * password:        vopverk3
 * API key:         AIzaSyAcbAEzORRjLqSP6I4ZcUzB6YKaNr6X7Fg
 * Sample call:
 * https://maps.googleapis.com/maps/api/directions/json?origin=Brugge&destination=Gent
 *                  &key=AIzaSyAcbAEzORRjLqSP6I4ZcUzB6YKaNr6X7Fg
 *
 */
public class GoogleScraper extends TrafficScraper {
    private String apiKey;
    private String url;

    public GoogleScraper()
    {
        this.apiKey = Settings.getSetting("google_apikey");
    }

    @Override
    public List<Meting> scrape()
    {
        return makeCall();
    }

    /**
     * here the actual rest call is made
     */
    public List<Meting> makeCall()
    {
        List<Meting> metingen = new ArrayList<>();
        //Get all trajectories which have coordinates in it
        DatabaseController databaseController = new DatabaseController();
        List<Traject> trajectList = databaseController.getTrajectenMetCoordinaten();

        Provider google = databaseController.haalProviderOp("Google Maps");
        JsonController jc = new JsonController();
        for(Traject traject:trajectList)
        {
            String url = "https://maps.googleapis.com/maps/api/directions/json?"+
                    "&origin="+traject.getStart_latitude()+"%2C"+traject.getStart_longitude()+
                    "&destination="+traject.getEnd_latitude()+"%2C"+traject.getEnd_longitude()+
                    "&key="+this.apiKey;
            Google google_obj = jc.makeGoogleCall(url, RequestType.GET);
            int traveltime = google_obj.getRoutes().get(0).getLegs().get(0).getDuration().getValue();
            int basetime = google_obj.getRoutes().get(0).getLegs().get(0).getDuration().getValue();
            int distance = google_obj.getRoutes().get(0).getLegs().get(0).getDistance().getValue();

            Meting meting = new Meting(google, traject, traveltime, basetime, LocalDateTime.now());

            metingen.add(meting);
        }
        return metingen;
    }
}
