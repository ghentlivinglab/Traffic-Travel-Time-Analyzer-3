package be.ugent.tiwi.samplecode;

import be.ugent.tiwi.dal.ProviderCRUD;
import be.ugent.tiwi.dal.TrajectCRUD;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
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
        hs.makeCall();
    }
}
