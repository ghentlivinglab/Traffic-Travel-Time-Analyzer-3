package be.ugent.tiwi.Mocks;

import be.ugent.tiwi.dal.ITrajectRepository;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;

import java.util.List;
import java.util.Map;

/**
 * Created by brent on 14/04/2016.
 */
public class TrajectRepositoryMock implements ITrajectRepository {
    @Override
    public List<Traject> getTrajecten() {
        return null;
    }

    @Override
    public List<Traject> getTrajectenMetWayPoints() {
        return null;
    }

    @Override
    public Traject getTraject(int id) {
        return null;
    }

    @Override
    public Traject getTrajectMetWaypoints(int id) {
        return null;
    }

    @Override
    public void wijzigTraject(int id, String naam, int lengte, int optimale_reistijd, Map<Integer, Integer> optimaleReistijden, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude, List<Waypoint> waypoints) {

    }

    @Override
    public void wijzigWaypoints(int id, List<Waypoint> waypoints) {

    }

    @Override
    public List<Waypoint> getWaypoints(int trajectId) {
        return null;
    }

    @Override
    public Traject getTraject(String naam) {
        return null;
    }

    @Override
    public List<Traject> getTrajectenMetCoordinaten() {
        return null;
    }

    @Override
    public List<Traject> getTrajectenMetCoordinaten(int id) {
        return null;
    }
}
