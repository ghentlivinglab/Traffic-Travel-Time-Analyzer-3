package be.ugent.tiwi.domein;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by brent on 16/02/2016.
 */
public class ScheduleController {
    public void startSchedule(){

            ScheduledExecutorService scheduler =
                    Executors.newScheduledThreadPool(1);

            final Runnable beeper = new Runnable() {
                public void run() {
                    System.out.println("beep");
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
}
