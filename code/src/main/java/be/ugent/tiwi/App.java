package be.ugent.tiwi;

import be.ugent.tiwi.controller.ScheduleController;
<<<<<<< HEAD
import be.ugent.tiwi.samplecode.DalSamples;
import settings.Settings;
=======
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
>>>>>>> 900d5c77dd68955600cfe607715c0fcf45e5e4b5

public class App {
    public static void main(String[] args) {
        /*ScheduleController scheduleC = new ScheduleController();
        scheduleC.startSchedule();*/
        DatabaseController dc = new DatabaseController();
        Traject traj = dc.haalTrajectMetWaypoints(1);
        traj.getWaypoints().remove(51);
        dc.wijzigTraject(traj);
    }
}
