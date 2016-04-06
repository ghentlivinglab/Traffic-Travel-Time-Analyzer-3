package be.ugent.tiwi;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Traject;

public class App {
    public static void main(String[] args) {
        ScheduleController scheduleC = new ScheduleController();
        scheduleC.startSchedule();
    }
}
