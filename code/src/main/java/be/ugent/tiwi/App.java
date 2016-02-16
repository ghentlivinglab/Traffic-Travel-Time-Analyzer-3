package be.ugent.tiwi;

import be.ugent.tiwi.domein.ScheduleController;

public class App {
    public static void main(String[] args) {
        Settings.createSettings();
        ScheduleController scheduleC = new ScheduleController();
        scheduleC.startSchedule();
    }
}
