package be.ugent.tiwi.controller;

import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.scraper.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MINUTES;


public class ScheduleController {
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);
    private DatabaseController dbController;

    /**
     * Deze functie begint een cyclus die iedere 5 minuten alle actieve {@link Provider}s overloopt en de beschikbare
     * data opslaat.
     */
    public void startSchedule() {

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);

        final Runnable schema = new Runnable() {
            public void run() {
                logger.info("Schedule STARTING - Trying to scrape data");
                haalDataOp();
                logger.info("Schedule DONE     - Waiting for next call");
            }
        };
        final ScheduledFuture<?> schemaHandle = scheduler.scheduleAtFixedRate(schema, 0, 5, MINUTES);
    }

    /**
     * Overloopt alle actieve providers en verwerkt de verkregen metingen.
     *
     * @throws RuntimeException Als een onopgevangen fout gebeurt
     */
    private void haalDataOp() throws RuntimeException {
        try {
            dbController = new DatabaseController();
            List<Provider> providers = dbController.haalActieveProvidersOp();
            List<Traject> trajects = dbController.getTrajectenMetWaypoints();
            for (Provider provider : providers) {

                logger.info("[" + provider.getNaam() + "] Scraping provider...");
                haalDataVanProvider(provider.getNaam(), trajects);
                logger.info("[" + provider.getNaam() + "] Done!");

            }
        } catch (RuntimeException ex) {
            logger.error("Excetion in scheduler", ex);
        }

    }

    /**
     * Roept de scraper van de provider gelabeled als **naam** op en voegt de verkregen metingen toe aan de database.
     * @param naam      De naam van de provider
     * @param trajects  De trajecten die meegegeven worden met de scraper
     *
     * @see Traject
     * @see Provider
     */
    private void haalDataVanProvider(String naam, List<Traject> trajects) {
        switch (naam.toLowerCase()) {
            case "google maps":
                dbController.voegMetingenToe(new GoogleScraper().scrape(trajects));
                break;
            case "here":
                dbController.voegMetingenToe(new HereScraper().scrape(trajects));
                break;
            case "coyote":
                dbController.voegMetingenToe(new CoyoteScraper().scrape(trajects));
                break;
            case "tomtom":
                dbController.voegMetingenToe(new TomTomScraper().scrape(trajects));
                break;
            case "bing maps":
                dbController.voegMetingenToe(new BingScraper().scrape(trajects));
                break;
            default:
                logger.error("'" + naam + "' is geen geldige providernaam!");
        }
    }
}
