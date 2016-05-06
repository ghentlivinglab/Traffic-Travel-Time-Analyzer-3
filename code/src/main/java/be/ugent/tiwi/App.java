package be.ugent.tiwi;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.IncidentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        IncidentRepository rep = new IncidentRepository();
        List result = rep.getTrafficIncidentsTrajecten();
        result.size();
        ScheduleController scheduleC = new ScheduleController();
        try {
            scheduleC.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
