package be.ugent.tiwi;
import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.ProviderCRUD;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.samplecode.DalSamples;
import be.ugent.tiwi.scraper.CoyoteScaper;
import settings.Settings;

public class App {
    public static void main(String[] args) {
        //CoyoteScaper sc = new CoyoteScaper();
        //sc.scrape();
        DalSamples.getProviderWithName("Waze");
        DalSamples.getTrajecten();
        DalSamples.scrapeHere();
        DalSamples.scrapeGoogle();
        ScheduleController scheduleC = new ScheduleController();
        scheduleC.startSchedule();
    }
}
