package be.ugent.tiwi.samplecode;

import be.ugent.tiwi.dal.MetingCRUD;
import be.ugent.tiwi.dal.ProviderCRUD;
import be.ugent.tiwi.dal.TrajectCRUD;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.scraper.GoogleScraper;
import be.ugent.tiwi.scraper.HereScraper;
import settings.Settings;

import java.util.List;

/**
 * Created by jelle on 19.02.16.
 */
public class DalSamples {
    public static void getProviderWithName(String name)
    {
        Settings.createSettings();
        Provider test;
        ProviderCRUD providerdb = new ProviderCRUD();
        test=providerdb.getProvider(name);
        System.out.printf("%s",test.to_string());
    }

    public static void getTrajecten()
    {
        List<Traject> trajecten;
        TrajectCRUD trajectCrud = new TrajectCRUD();
        trajecten=trajectCrud.getTrajecten();
        for(Traject traject : trajecten)
        {
            System.out.printf("%s\n",traject.toString());
        }
    }

    public static void scrapeHere()
    {
        HereScraper hs = new HereScraper();
        MetingCRUD mcrud = new MetingCRUD();
        hs.makeCall();
        List<Meting> metingen = mcrud.getMetingen();
        for(Meting meting : metingen)
        {
            System.out.println(meting.toString());
        }
    }

    public static void scrapeGoogle() {
        GoogleScraper gs = new GoogleScraper();
        MetingCRUD mcrud = new MetingCRUD();
        gs.makeCall();
        List<Meting> metingen = mcrud.getMetingen();
        for(Meting meting : metingen)
        {
            System.out.println(meting.toString());
        }
    }
}
