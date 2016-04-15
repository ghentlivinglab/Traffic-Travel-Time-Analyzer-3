package be.ugent.tiwi.Mocks;

import be.ugent.tiwi.dal.ITrajectRepository;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by brent on 14/04/2016.
 */
public class TrajectRepositoryMock implements ITrajectRepository {

    private List<Traject> trajecten = new ArrayList<>();
    public TrajectRepositoryMock(){
        Traject traj = new Traject(1,"Traject 1", 1339, 90,null, true, "51.05617", "3.69508", "51.06629", "3.69971");
        List<Waypoint> waypoints = new ArrayList<>();
        waypoints.add(new Waypoint(1,"51.05620", "3.69510"));
        waypoints.add(new Waypoint(2,"51.05630", "3.69515"));
        waypoints.add(new Waypoint(3,"51.05640", "3.69520"));
        traj.setWaypoints(waypoints);
        trajecten.add(traj);
        trajecten.add(new Traject(2,"Traject 2", 1339, 90,null, true, "51.05617", "3.69508", "51.06629", "3.69971"));
        trajecten.add(new Traject(3,"Traject 3", 1339, 90,null, false, "51.05617", "3.69508", "51.06629", "3.69971"));
        trajecten.add(new Traject(4,"Traject 4", 1339, 90,null, false, "51.05617", "3.69508", "51.06629", "3.69971"));
        trajecten.add(new Traject(5,"Traject 5", 1339, 90,null, true, "51.05617", "3.69508", "51.06629", "3.69971"));
    }

    @Override
    public List<Traject> getTrajecten() {
        return trajecten;
    }

    @Override
    public List<Traject> getTrajectenMetWayPoints() {
        List<Traject> trajectenMetWaypoints = new ArrayList<>();
        for (Traject t:trajecten) {
            if(t.getWaypoints()!=null){
                trajectenMetWaypoints.add(t);
            }
        }
        return trajectenMetWaypoints;
    }

    @Override
    public Traject getTraject(int id) {
        for (Traject t:trajecten) {
            if(t.getId()==id){
                return t;
            }
        }
        return null;
    }

    @Override
    public Traject getTrajectMetWaypoints(int id) {
        for (Traject t:trajecten) {
            if(t.getId()==id){
                if(t.getWaypoints()==null){
                    t.setWaypoints(new ArrayList<>());
                }
                return t;
            }
        }
        return null;
    }

    @Override
    public void wijzigTraject(int id, String naam, int lengte, int optimale_reistijd, Map<Integer, Integer> optimaleReistijden, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude, List<Waypoint> waypoints) {
        for (Traject t:trajecten) {
            if(t.getId()==id){
                t.setNaam(naam);
                t.setLengte(lengte);
                t.setOptimale_reistijd(optimale_reistijd);
                t.setIs_active(is_active);
                t.setStart_latitude(start_latitude);
                t.setStart_longitude(start_longitude);
                t.setEnd_latitude(end_latitude);
                t.setEnd_longitude(end_longitude);
            }
        }
    }

    @Override
    public void wijzigWaypoints(int id, List<Waypoint> waypoints) {
        for (Traject t:trajecten) {
            if(t.getId()==id){
                t.setWaypoints(waypoints);
            }
        }
    }

    @Override
    public List<Waypoint> getWaypoints(int trajectId) {
        for (Traject t:trajecten) {
            if(t.getId()==trajectId){
                return t.getWaypoints();
            }
        }
        return null;
    }

    @Override
    public Traject getTraject(String naam) {
        for (Traject t:trajecten) {
            if(t.getNaam().equals(naam)){
                return t;
            }
        }
        return null;
    }

    @Override
    public List<Traject> getTrajectenMetCoordinaten() {
        return getTrajectenMetWayPoints();
    }
}
