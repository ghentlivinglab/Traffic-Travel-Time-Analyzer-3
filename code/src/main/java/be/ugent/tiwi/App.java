package be.ugent.tiwi;
import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.ProviderCRUD;
import be.ugent.tiwi.domein.Provider;
import settings.Settings;

public class App {
    public static void main(String[] args) {
        /* --Kan je runnen als je een databank draaien hebt waarvan je het ip weet
        Settings.createSettings();
        Provider test;
        ProviderCRUD providerdb = new ProviderCRUD();
        test=providerdb.getProvider("Waze");
        System.out.printf("%s",test.to_string());
        */
        ScheduleController scheduleC = new ScheduleController();
        scheduleC.startSchedule();
    }
}
