package be.ugent.tiwi.scraper;

import settings.Settings;

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

    public HereScraper()
    {
        this.appId = Settings.getSetting("here_appid");
        this.appCode = Settings.getSetting("here_appcode");
    }

    @Override
    public void scrape()
    {

    }

    /**
     * Here the actual rest call is made
     */
    public void makeCall()
    {

    }
}
