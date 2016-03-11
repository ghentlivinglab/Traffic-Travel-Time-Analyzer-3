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
import java.util.logging.Logger;

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
                if (rs.wasNull())
                    start_latitude = null;
                String start_longitude = rs.getString("start_longitude");
                if (rs.wasNull())
                    start_longitude = null;
                String end_latitude = rs.getString("end_latitude");
                if (rs.wasNull())
                    end_latitude = null;
                String end_longitude = rs.getString("end_longitude");
                if (rs.wasNull())
                    end_longitude = null;

                trajecten.add(new Traject(id, naam, lengte, optimale_reistijd, is_active, start_latitude,
                        start_longitude, end_latitude, end_longitude));
            }
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
                boolean is_active = rs.getBoolean("is_active");
                String start_latitude = rs.getString("start_latitude");
                if (rs.wasNull())
                    start_latitude = null;
                String start_longitude = rs.getString("start_longitude");
                if (rs.wasNull())
                    start_longitude = null;
                String end_latitude = rs.getString("end_latitude");
                if (rs.wasNull())
                    end_latitude = null;
                String end_longitude = rs.getString("end_longitude");
                if (rs.wasNull())
                    end_longitude = null;

                return new Traject(id, naam, lengte, optimale_reistijd, is_active, start_latitude, start_longitude, end_latitude, end_longitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void wijzigTraject(int id, String naam, String letter, int lengte, int optimale_reistijd, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude)
    {
        String query = "update trajecten set naam=\""+naam+"\","+
                                            "letter=\""+letter+"\","+
                                            "start_latitude=\""+start_latitude+"\","+
                                            "start_longitude=\""+start_longitude+"\","+
                                            "end_latitude=\""+end_latitude+"\","+
                                            "end_longitude=\""+end_longitude+"\","+
                                            "is_active="+is_active+","+
                                            "optimale_reistijd="+optimale_reistijd+
                     " where id="+id;

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }catch (SQLException e) {
            logger.error("Wijzigen van traject met id "+id+" is mislukt...");
            logger.error(e);
        }
    }

    public List<Traject> getTrajectenMetCoordinaten() {
        List<Traject> trajectList = getTrajecten();

        for (int i = 0; i < trajectList.size(); i++) {
            if (trajectList.get(i).getStart_latitude() == null) {
                trajectList.remove(i);
            }
        }

        return trajectList;
    }

    public List<Waypoint> getWaypoints(int trajectid)
    {
        String query = "select * from waypoints where traject_id='" + trajectid + "'";
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
                naam = rs.getString("naam");
                int id = rs.getInt("id");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getBoolean("is_active");
                String start_latitude = rs.getString("start_latitude");
                if (rs.wasNull())
                    start_latitude = null;
                String start_longitude = rs.getString("start_longitude");
                if (rs.wasNull())
                    start_longitude = null;
                String end_latitude = rs.getString("end_latitude");
                if (rs.wasNull())
                    end_latitude = null;
                String end_longitude = rs.getString("end_longitude");
                if (rs.wasNull())
                    end_longitude = null;

                return new Traject(id, naam, lengte, optimale_reistijd, is_active, start_latitude, start_longitude, end_latitude, end_longitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
