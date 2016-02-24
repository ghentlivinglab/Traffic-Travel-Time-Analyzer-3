package be.ugent.tiwi;
import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.ProviderCRUD;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.samplecode.DalSamples;
import settings.Settings;

public class App {
    public static void main(String[] args) {
        DalSamples.getProviderWithName("Waze");
        DalSamples.getTrajecten();
        DalSamples.scrapeHere();
        ScheduleController scheduleC = new ScheduleController();
        scheduleC.startSchedule();
    }
}
