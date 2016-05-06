package be.ugent.tiwi;

import be.ugent.tiwi.controller.ScheduleController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
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
