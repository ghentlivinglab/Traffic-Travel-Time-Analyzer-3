package be.ugent.tiwi.controller;

import be.ugent.tiwi.dal.DBConnector;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.scraper.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.DependencyModules.RepositoryModule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MINUTES;


public class ScheduleController {
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);
    private DatabaseController dbController;
    private boolean started = false;

    private BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    ScheduledExecutorService scheduler;

    public ScheduleController(){
        tasksConsumer.start();
    }

    public boolean start() throws SQLException {
        if (!started){
            started = true;
            startSchedule();
            return true;
        }
        return false;
    }

    public void addTask(Runnable r){
        try {
            tasks.put(r);
            logger.info("Task added");
        } catch (InterruptedException e) {
            logger.error("Could not add task!");
        }
    }

    Thread tasksConsumer = new Thread(() -> {
        while(true) {
            try {
                Runnable r = tasks.take();
                logger.info("Running task:");
                r.run();
                logger.info("Done running task; # remaining: " + tasks.size());
            } catch (InterruptedException e) {
                logger.error("'Waiting for task' was interrupted");
            }
        }
    });

    public boolean stop(){
        if(started) {
            started = false;
            scheduler.shutdown();
            return true;
        }
        return false;
    }

    public boolean isStarted(){
        return started;
    }

    /**
     * Deze functie begint een cyclus die iedere 5 minuten alle actieve {@link Provider}s overloopt en de beschikbare
     * data opslaat.
     */
    private void startSchedule() throws SQLException {
        scheduler = Executors.newScheduledThreadPool(1);

        DBConnector db = new DBConnector();
        try {
            if(db.getConnection() != null) {
                final Runnable schema = () -> {
                    logger.info("Tasks: Added task 'haalDataOp'");
                    tasks.add(() -> {
                        logger.info("Schedule STARTING - Trying to scrape data");
                        haalDataOp();
                        logger.info("Schedule DONE");
                    });
                };
                final ScheduledFuture<?> schemaHandle = scheduler.scheduleAtFixedRate(schema, 0, 5, MINUTES);
            }
        } catch (SQLException ex) {
            started = false;
            throw ex;
        }
    }

    /**
     * Overloopt alle actieve providers en verwerkt de verkregen metingen.
     */
    private void haalDataOp() {
            Injector injector = Guice.createInjector(new RepositoryModule());
            dbController = injector.getInstance(DatabaseController.class);
            List<Provider> providers = dbController.haalActieveProvidersOp();
            for (Provider provider : providers) {

                try {
                    List<Traject> trajects = dbController.getTrajectenMetWaypoints();
                    logger.info("[" + provider.getNaam() + "] Scraping provider...");
                    //Geef een kopie van de lijst mee zodat enige wijzigingen de andere scrapers niet beinvloeden.
                    haalDataVanProvider(provider.getNaam(), new ArrayList<>(trajects));
                    logger.info("[" + provider.getNaam() + "] Done!");

                } catch (RuntimeException ex) {
                    logger.error("Exception in een scraper:", ex);
                }
            }

    }

    /**
     * Roept de scraper van de provider gelabeled als **naam** op en voegt de verkregen metingen toe aan de database.
     *
     * @param naam     De naam van de provider
     * @param trajects De trajecten die meegegeven worden met de scraper
     * @see Traject
     * @see Provider
     */
    private void haalDataVanProvider(String naam, List<Traject> trajects) {
        //TrafficScrapers
        switch (naam.toLowerCase()) {
            case "google maps":
                dbController.voegMetingenToe(new GoogleScraper().scrape(trajects));
                break;
            case "here":
                dbController.voegMetingenToe(new HereScraper().scrape(trajects));
                dbController.voegIncidentToe(new HereIncidentScraper().scrape(trajects));
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
            case "waze":
                new WazeScraper().scrape(trajects);
                break;
            default:
                logger.error("'" + naam + "' is geen geldige providernaam!");
        }
    }
}
