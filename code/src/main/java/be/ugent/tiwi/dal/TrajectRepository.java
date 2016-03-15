package be.ugent.tiwi.dal;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
import org.apache.logging.log4j.LogManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jelle on 19.02.16.
 */
public class TrajectRepository {
    private DBConnector connector;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ScheduleController.class);

    public TrajectRepository() {
        connector = new DBConnector();
    }

    public List<Traject> getTrajecten() {
        List<Traject> trajecten = new ArrayList<Traject>();
        String query = "select * from trajecten";

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String naam = rs.getString("naam");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getBoolean("is_active");
                String start_latitude = rs.getString("start_latitude");
                String start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude");
                String end_longitude = rs.getString("end_longitude");
                trajecten.add(new Traject(id, naam, lengte, optimale_reistijd, is_active, start_latitude, start_longitude, end_latitude, end_longitude));
            }
            connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trajecten;
    }

    public Traject getTraject(int id) {
        String query = "select * from trajecten where id='" + id + "'";

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                String naam = rs.getString("naam");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                String start_latitude = rs.getString("start_latitude"), start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude"), end_longitude = rs.getString("end_longitude");
                boolean is_active = rs.getBoolean("is_active");
                connector.closeConnection();
                return new Traject(id, naam, lengte, optimale_reistijd, is_active, start_latitude, start_longitude, end_latitude, end_longitude);
            }
            connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void wijzigTraject(int id, String naam, int lengte, int optimale_reistijd, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude)
    {
        String query = "update trajecten set naam=\""+naam+"\","+
                "lengte="+lengte+","+
                "optimale_reistijd="+optimale_reistijd+","+
                "is_active="+is_active+
                "start_latitude=\""+start_latitude+"\","+
                "start_longitude=\""+start_longitude+"\","+
                "end_latitude=\""+end_latitude+"\","+
                "end_longitude=\""+end_longitude+"\","+
             " where id="+id;

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            connector.closeConnection();
        }catch (SQLException e) {
            logger.error("Wijzigen van traject met id "+id+" is mislukt...");
            logger.error(e);
        }
    }

    public List<Waypoint> getWaypoints(int trajectid)
    {
        String query = "select * from waypoints where traject_id=" + trajectid;
        List<Waypoint> wpts = new ArrayList<>();
        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Traject traj = getTraject(trajectid);

            while (rs.next())
            {
                int volgnr = rs.getInt("volgnr");
                String lat = rs.getString("latitude");
                if (rs.wasNull())
                    lat = null;
                String lon = rs.getString("longitude");
                if (rs.wasNull())
                    lon = null;
                wpts.add(new Waypoint(traj,volgnr,lat,lon));
            }
            connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wpts;
    }

    public Traject getTraject(String naam) {
        String query = "select * from trajecten where naam='" + naam + "'";

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getBoolean("is_active");
                String start_latitude = rs.getString("start_latitude"), start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude"), end_longitude = rs.getString("end_longitude");
                connector.closeConnection();
                return new Traject(id,  naam, lengte, optimale_reistijd, is_active, start_latitude,start_longitude,end_latitude,end_longitude);
            }
            connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public List<Traject> getTrajectenMetCoordinaten() {
        List<Traject> trajecten = getTrajecten();
        for(Traject t : trajecten){
            t.setWaypoints(getWaypoints(t.getId()));
        }
        return trajecten;
    }
}
