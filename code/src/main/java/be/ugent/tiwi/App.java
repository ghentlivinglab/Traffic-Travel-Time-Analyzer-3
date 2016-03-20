package be.ugent.tiwi;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.scraper.CoyoteScraper;

public class App {
    public static void main(String[] args) {
        //ScheduleController scheduleC = new ScheduleController();
        //scheduleC.startSchedule();

        CoyoteScraper s = new CoyoteScraper();
        s.genereerSQL();
    }
}
