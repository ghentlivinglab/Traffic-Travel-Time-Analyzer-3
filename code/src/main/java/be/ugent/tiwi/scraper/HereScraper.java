package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.MetingCRUD;
import be.ugent.tiwi.dal.ProviderCRUD;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.RequestType;
import be.ugent.tiwi.dal.TrajectCRUD;
import be.ugent.tiwi.domein.here.*;
import be.ugent.tiwi.domein.Traject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;
import java.util.List;

/**
 * Created by jelle on 19.02.16.
 *
 * Account settings
 * email:           jelle.debock@ugent.be
 * password:        vop_project_groep3
 * App ID:          tsliJF6nV8gV1CCk7yK8
 * App CODE:        o8KURFHJC02Zzlv8HTifkg
 * Expiry:          May 19, 2016
 * Sample call:
 *  https://route.cit.api.here.com/routing/7.2/calculateroute.json?
 *              app_id=tsliJF6nV8gV1CCk7yK8&app_code=o8KURFHJC02Zzlv8HTifkg
 *              &waypoint0=geo!51.040800%2C3.614126&waypoint1=geo!51.038736%2C3.736503
 *              &mode=fastest%3Bcar%3Btraffic%3Aenabled
 *
 */
public class HereScraper extends TrafficScraper{
    private String appId;
    private String appCode;
    private String url;
    private JsonController<Here> jc;
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);


    public HereScraper()
    {
        this.appId = Settings.getSetting("here_appid");
        this.appCode = Settings.getSetting("here_appcode");
        this.jc = new JsonController<Here>();
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

        Provider here = pcrud.getProvider("Here");
        JsonController jc = new JsonController();
        for(Traject traject:trajectList)
        {
            String url = "https://route.cit.api.here.com/routing/7.2/calculateroute.json?"+
                    "app_id="+this.appId+
                    "&app_code="+this.appCode+
                    "&waypoint0=geo!"+traject.getStart_latitude()+"%2C"+traject.getStart_longitude()+
                    "&waypoint1=geo!"+traject.getEnd_latitude()+"%2C"+traject.getEnd_longitude()+
                    "&mode=fastest%3Bcar%3Btraffic%3Aenabled";
            Here here_obj = (Here) jc.getObject(url, Here.class, RequestType.GET);
            int traveltime = here_obj.getResponse().getRoute().get(0).getSummary().getTravelTime();
            int basetime = here_obj.getResponse().getRoute().get(0).getSummary().getBaseTime();
            int distance = here_obj.getResponse().getRoute().get(0).getSummary().getDistance();

            metingCRUD.addMeting(here,traject,traveltime,basetime);
        }
    }
}
