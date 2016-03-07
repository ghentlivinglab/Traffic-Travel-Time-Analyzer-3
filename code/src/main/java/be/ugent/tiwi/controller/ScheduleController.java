package be.ugent.tiwi.controller;

import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.samplecode.DalSamples;
import be.ugent.tiwi.scraper.CoyoteScraper;
import be.ugent.tiwi.scraper.GoogleScraper;
import be.ugent.tiwi.scraper.HereScraper;
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

    public void startSchedule() {

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);

        final Runnable schema = new Runnable() {
            public void run() {
                logger.trace("Schedule opgestart.");
                haalDataOp();
            }
        };
        final ScheduledFuture<?> schemaHandle = scheduler.scheduleAtFixedRate(schema, 0, 5, MINUTES);
    }

    private void haalDataOp() throws RuntimeException {
        try {
            dbController = new DatabaseController();
            new CoyoteScraper().scrape();
            List<Provider> providers = dbController.haalActieveProvidersOp();
            for (Provider provider : providers) {
                haalDataVanProvider(provider.getNaam());
            }

            /*DalSamples.getProviderWithName("Waze");
            DalSamples.getTrajecten();
            DalSamples.scrapeHere();
            DalSamples.scrapeCoyote();
            DalSamples.scrapeGoogle();*/
        } catch (RuntimeException ex) {
            logger.error("Schedule gestopt door exception");
            ex.printStackTrace();
            throw ex;
        }

    }

    private void haalDataVanProvider(String naam) {
        switch (naam.toLowerCase()) {
            case "google maps":
                dbController.voegMetingenToe(new GoogleScraper().scrape());
                break;
            case "here":
                dbController.voegMetingenToe(new HereScraper().scrape());
                break;
            case "coyote":
                dbController.voegMetingenToe(new CoyoteScraper().scrape());
                break;
        }
    }
}
