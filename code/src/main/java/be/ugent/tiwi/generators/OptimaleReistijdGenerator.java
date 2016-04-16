package be.ugent.tiwi.generators;

import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.scraper.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.DependencyModules.RepositoryModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eigenaar on 12/04/2016.
 */
public class OptimaleReistijdGenerator {
    private static final Logger logger = LogManager.getLogger(OptimaleReistijdGenerator.class);

    public static void main(String args[]){
        logger.info("We halen voor ieder traject, per provider, een meting op.");
        logger.info("We zullen de tabel optimale_reistijden opvullen met deze metingen.");
        logger.info("Draai dit programma dus enkel wanneer er vrijwel geen verkeer op de autoweg is.");

        Injector injector = Guice.createInjector(new RepositoryModule());
        DatabaseController dbController = injector.getInstance(DatabaseController.class);

        List<Provider> providers = dbController.haalAlleProvidersOp();
        List<Traject> trajects = dbController.getTrajectenMetWaypoints();
        for(Provider provider : providers){
            logger.info("[" + provider.getNaam() + "] Scraping provider...");

            List<Meting> m = null;
            switch (provider.getNaam().toLowerCase()) {
                case "google maps":
                    m = new GoogleScraper().scrape(new ArrayList<>(trajects));
                    break;
                case "here":
                    m = new HereScraper().scrape(new ArrayList<>(trajects));
                    break;
                case "tomtom":
                    m = new TomTomScraper().scrape(new ArrayList<>(trajects));
                    break;
                case "coyote":
                    Map<Integer, Integer> reistijdenPerTraject = new HashMap<>();
                    for(Traject t : trajects)
                        reistijdenPerTraject.put(t.getId(), t.getOptimale_reistijd());
                    dbController.setOptimaleReistijdenPerProvider(provider.getId(), reistijdenPerTraject);
                    break;
                case "bing maps":
                    m = new BingScraper().scrape(new ArrayList<>(trajects));
                    break;
                case "waze":
                    logger.warn("Nog niet geimplementeerd!");
                    break;
                default:
                    logger.warn("Provider " + provider.getNaam() + " zit nog niet in de switch-statement!");
            }
            if(m != null) {
                Map<Integer, Integer> reistijdenPerTraject = new HashMap<>();
                int count = 0;
                for(Meting meting : m) {
                    Integer i = meting.getReistijd();
                    if (i != null) {
                        reistijdenPerTraject.put(meting.getTraject().getId(), i);
                        ++count;
                    } else
                        logger.warn("Provider " + provider.getId() + ", traject " + meting.getTraject().getId() + ": optimale_reistijd = null");
                }
                dbController.setOptimaleReistijdenPerProvider(provider.getId(), reistijdenPerTraject);

                logger.info(count + " optimale reistijden aangepast!");
            }
            logger.info("[" + provider.getNaam() + "] Done!");
        }
    }

}
