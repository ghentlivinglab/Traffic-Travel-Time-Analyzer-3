package be.ugent.tiwi.samplecode;

import be.ugent.tiwi.dal.MetingRepository;
import be.ugent.tiwi.dal.ProviderRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.scraper.CoyoteScraper;
import be.ugent.tiwi.scraper.GoogleScraper;
import be.ugent.tiwi.scraper.HereScraper;
import settings.Settings;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jelle on 19.02.16.
 */
public class DalSamples {
    public static void getProviderWithName(String name) {
        Settings.createSettings();
        Provider test;
        ProviderRepository providerdb = new ProviderRepository();
        test = providerdb.getProvider(name);
        System.out.printf("%s", test.to_string());
    }

    public static void getTrajecten() {
        List<Traject> trajecten;
        TrajectRepository trajectRepository = new TrajectRepository();
        trajecten = trajectRepository.getTrajecten();
        for (Traject traject : trajecten) {
            System.out.printf("%s\n", traject.toString());
        }
    }

    public static void scrapeHere() {
        HereScraper hs = new HereScraper();
        MetingRepository mcrud = new MetingRepository();
        TrajectRepository tcrud = new TrajectRepository();
        List<Traject> trajects = tcrud.getTrajectenMetCoordinaten();
        hs.makeCall(trajects);
        List<Meting> metingen = mcrud.getMetingen();
        for (Meting meting : metingen) {
            System.out.println(meting.toString());
        }
    }

    public static void scrapeGoogle() {
        GoogleScraper gs = new GoogleScraper();
        MetingRepository mcrud = new MetingRepository();
        TrajectRepository tcrud = new TrajectRepository();
        List<Traject> trajects = tcrud.getTrajectenMetCoordinaten();
        gs.scrape(trajects);
        List<Meting> metingen = mcrud.getMetingen();
        for (Meting meting : metingen) {
            System.out.println(meting.toString());
        }
    }

    public static void scrapeCoyote() {
        CoyoteScraper cs = new CoyoteScraper();
        TrajectRepository tcrud = new TrajectRepository();
        List<Traject> trajects = tcrud.getTrajectenMetCoordinaten();
        List<Meting> metingen = cs.scrape(trajects);
        for (Meting meting : metingen) {
            System.out.println(meting.toString());
        }
    }

    public static void getStatistieken(){
        MetingRepository mrep = new MetingRepository();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        System.out.println(mrep.metingStatistieken(11,sevenDaysAgo,now).toString());
        System.out.println("Algemene gemiddelde vertraging de laatste 7 dagen "+mrep.gemiddeldeVertraging(sevenDaysAgo,now));
    }

    public static void main(String[] args)
    {
        getStatistieken();
    }
}
