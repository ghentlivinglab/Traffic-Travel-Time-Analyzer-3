package be.ugent.tiwi;
import be.ugent.tiwi.controller.ScheduleController;

public class App {
    public static void main(String[] args) {
        ScheduleController scheduleC = new ScheduleController();
        scheduleC.startSchedule();
    }
}
