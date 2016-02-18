package be.ugent.tiwi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ScheduleController {
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);

    public void startSchedule(){

            ScheduledExecutorService scheduler =
                    Executors.newScheduledThreadPool(1);

            final Runnable beeper = new Runnable() {
                public void run() {
                    logger.trace("Schedule opgestart.");
                    haalDataOp();
                }
            };
            final ScheduledFuture<?> beeperHandle =
                    scheduler.scheduleAtFixedRate(beeper, 2, 2, SECONDS);
            scheduler.schedule(new Runnable() {
                public void run() {
                    beeperHandle.cancel(true);
                }
            }, 60 * 1, SECONDS);
    }
    private void haalDataOp() throws RuntimeException
    {
        try{
            //hier komt code
            System.out.println("beep");
        }catch (RuntimeException ex){
            logger.error("Schedule gestopt door exception");
            throw ex;
        }

    }
}
