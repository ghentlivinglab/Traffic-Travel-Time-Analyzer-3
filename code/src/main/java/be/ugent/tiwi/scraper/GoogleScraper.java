package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.dal.MetingCRUD;
import be.ugent.tiwi.dal.ProviderCRUD;
import be.ugent.tiwi.dal.TrajectCRUD;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.RequestType;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.google.Google;
import settings.Settings;

import java.util.List;

/**
 * Created by jan on 27.02.16.
 *
 * Account settings
 * email:           vopverkeer3@gmail.com
 * password:        vopverk31
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
    public void scrape()
    {
        makeCall();
    }

    /**
     * here the actual rest call is made
     */
    public void makeCall()
    {
        //Get all trajectories which have coordinates in it
        TrajectCRUD tcrud = new TrajectCRUD();
        ProviderCRUD pcrud = new ProviderCRUD();
        List<Traject> trajectList = tcrud.getTrajectenMetCoordinaten();
        MetingCRUD metingCRUD = new MetingCRUD();

        Provider google = pcrud.getProvider("Google");
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

            metingCRUD.addMeting(google, traject, traveltime, basetime);
        }
    }
}
