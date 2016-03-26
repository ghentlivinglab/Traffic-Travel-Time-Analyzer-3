package be.ugent.tiwi.scraper;

import be.ugent.tiwi.controller.JsonController;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.*;
import be.ugent.tiwi.domein.bing.Bing;
import be.ugent.tiwi.domein.bing.Resource;
import be.ugent.tiwi.domein.bing.ResourceSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eigenaar on 15/03/2016.
 */
public class BingScraper implements TrafficScraper {

    private static final Logger logger = LogManager.getLogger(BingScraper.class);

    private String apiKey;

    public BingScraper() {
        this.apiKey = Settings.getSetting("bing_apikey");
    }


    @Override
    public List<Meting> scrape(List<Traject> trajects) {
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
            if(wpts.size()>0) {
                double size = wpts.size();
                size /= 10; //Maximum toegelaten ViaWaypoints
                if(size > 1)
                    for(i = 1; i < 10; ++i){
                        int index = (int) (i * size);
                        url += "&vWp." + i + "=" + wpts.get(index).getLatitude() + "," + wpts.get(index).getLongitude();
                    }
                else
                    for(i = 1; i < wpts.size(); ++i)
                        url += "&vWp." + i + "=" + wpts.get(i).getLatitude() + "," + wpts.get(i).getLongitude();
            }
            url += "&wp."+ i+"=" + traject.getEnd_latitude() + "," + traject.getEnd_longitude();
            url += "&optimize=timeWithTraffic&maxSolutions=1&mfa=1&key=" + apiKey;

            Bing bing_obj = (Bing)jc.getObject(url, Bing.class, RequestType.GET);
            if(bing_obj.getResourceSets().size() > 0){
                ResourceSet set = bing_obj.getResourceSets().get(0);
                if(set.getResources().size() > 0){
                    Resource resource = set.getResources().get(0);
                    Meting m = new Meting(bing, traject, resource.getTravelDurationTraffic(), LocalDateTime.now());
                    metingen.add(m);
                }else{
                    logger.warn("Provider Bing: Could not scrape traject " + traject.getId() + ", adding an empty measurement [1]");
                    metingen.add(new Meting(bing, traject, -1, LocalDateTime.now()));
                }
            }else{
                logger.warn("Provider Bing: Could not scrape traject " + traject.getId() + ", adding an empty measurement [2]");
                metingen.add(new Meting(bing, traject, -1, LocalDateTime.now()));
            }

        }
        return metingen;
    }
}
