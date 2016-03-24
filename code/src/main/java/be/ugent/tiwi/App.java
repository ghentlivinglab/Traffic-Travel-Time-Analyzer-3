package be.ugent.tiwi;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;

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
