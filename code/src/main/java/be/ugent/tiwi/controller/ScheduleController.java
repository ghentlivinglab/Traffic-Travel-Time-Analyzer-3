package be.ugent.tiwi.controller;

import be.ugent.tiwi.samplecode.DalSamples;
import be.ugent.tiwi.scraper.CoyoteScaper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MINUTES;

public class ScheduleController {
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);

    public void startSchedule(){

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
    private void haalDataOp() throws RuntimeException
    {
        try{
            System.out.println("Beep");
            CoyoteScaper sc = new CoyoteScaper();
            sc.scrape();
            /*DalSamples.getProviderWithName("Waze");
            DalSamples.getTrajecten();
            DalSamples.scrapeHere();
            DalSamples.scrapeGoogle();*/
        }catch (RuntimeException ex){
            logger.error("Schedule gestopt door exception");
            ex.printStackTrace();
            throw ex;
        }

    }
}
