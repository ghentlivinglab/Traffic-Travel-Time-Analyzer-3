package be.ugent.tiwi.scraper;


import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.MetingCRUD;
import be.ugent.tiwi.dal.ProviderCRUD;
import be.ugent.tiwi.dal.TrajectCRUD;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.RequestType;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.tomtom.TomTom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

/**
 * Created on 01.03.16.
 *
 * Account settings
 * email:           vopverkeer3@gmail.com
 * username:        vopverkeer3
 * password:        vopverk31
 * API key:         AIzaSyAcbAEzORRjLqSP6I4ZcUzB6YKaNr6X7Fg
 * Sample call: https://api.tomtom.com/routing/1/calculateRoute/52.509317,13.429368:52.502746,13.438724/json?key=8yafwthpctekty68x3kbae4h
 *
 *
 * Online Routing
 *
 *Key: 8yafwthpctekty68x3kbae4h

 *Application: vopverkeer3 Key: 8yafwthpctekty68x3kbae4h Status: active Registered: 12 seconds ago
 *Key Rate Limits
 *5	Calls per second
 *1,000	Calls per day
 *Online Search

 *Key: naaqavj3jt4v6q74huk6wvfy

 *Application: vopverkeer3 Key: naaqavj3jt4v6q74huk6wvfy Status: active Registered: 12 seconds ago
 *Key Rate Limits
 *5	Calls per second
 *1,000	Calls per day
 *Online Traffic Flow

 *Key: 5jsqf2j9zjwerqudwcm8kn2f

 *Application: vopverkeer3 Key: 5jsqf2j9zjwerqudwcm8kn2f Status: active Registered: 12 seconds ago
 *Key Rate Limits
 *1,000	Calls per second
 *10,000	Calls per day
 *Online Traffic Incidents

 *Key: 5c64tkvt8w424hj8a6afxw3t

 *Application: vopverkeer3 Key: 5c64tkvt8w424hj8a6afxw3t Shared Secret: q5qt2mZ Status: active Registered: 11 seconds ago
 *Key Rate Limits
 *30	Calls per second
 *5,000	Calls per day
 *
 */
public class TomTomScraper extends TrafficScraper {

    private String apiKey;
    private String url;
    private JsonController<TomTom> jc;
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);


    public TomTomScraper()
    {
        this.apiKey = Settings.getSetting("tomtom_appid");
        this.jc = new JsonController<TomTom>();
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

        Provider tomtom = pcrud.getProvider("TomTom");
        JsonController jc = new JsonController();
        for(Traject traject:trajectList)
        {
            String url = "https://api.tomtom.com/routing/1/calculateRoute/"+
                    traject.getStart_latitude()+","+
                    traject.getStart_longitude()+
                    traject.getEnd_latitude()+","+
                    traject.getEnd_longitude()+
                    "/json?"+
                    "key="+this.apiKey;

            TomTom tomtom = (TomTom) jc.getObject(url, TomTom.class, RequestType.GET);
            int traveltime = tomtom.getResponse().getRoute().get(0).getSummary().getTravelTime();
            int basetime = tomtom.getResponse().getRoute().get(0).getSummary().getBaseTime();
            int distance = tomtom.getResponse().getRoute().get(0).getSummary().getDistance();

            metingCRUD.addMeting(tomtom,traject,traveltime,basetime);
        }
    }
}
