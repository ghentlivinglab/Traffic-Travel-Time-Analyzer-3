package be.ugent.tiwi;

import be.ugent.tiwi.controller.ScheduleController;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        ScheduleController scheduleC = new ScheduleController();
        try {
            scheduleC.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
