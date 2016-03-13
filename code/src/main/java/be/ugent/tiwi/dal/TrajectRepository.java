package be.ugent.tiwi.dal;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.util.StringBuilders;

import java.sql.PreparedStatement;
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

    private PreparedStatement statTrajecten = null;
    private PreparedStatement statTrajectId = null;
    private PreparedStatement statUpdateTraject = null;
    private PreparedStatement statWaypoints = null;
    private PreparedStatement statTrajectNaam = null;

    private String stringTrajecten = "select * from trajecten";
    private String stringTrajectId = "select * from trajecten where id= ?";
    private String stringUpdateTraject = "update trajecten set naam= ?, lengte=?, start_latitude= ?, start_longitude= ?, end_latitude= ?, end_longitude= ?, is_active= ?, optimale_reistijd= ? where id= ?";
    private String stringWaypoints = "select * from waypoints where traject_id= ?";
    private String stringTrajectNaam = "select * from trajecten where naam= ?";

    public TrajectRepository() {
        connector = new DBConnector();

        try {
            statTrajecten = connector.getConnection().prepareStatement(stringTrajecten);
            statTrajectId = connector.getConnection().prepareStatement(stringTrajectId);
            statUpdateTraject = connector.getConnection().prepareStatement(stringUpdateTraject);
            statWaypoints = connector.getConnection().prepareStatement(stringWaypoints);
            statTrajectNaam = connector.getConnection().prepareStatement(stringTrajectNaam);
        } catch (SQLException e) {
            logger.error("Verbinden met de databank is mislukt");
        }
    }

    public List<Traject> getTrajecten() {
        List<Traject> trajecten = new ArrayList<Traject>();

        try {
            ResultSet rs = statTrajecten.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String naam = rs.getString("naam");
                String van = rs.getString("van");
                String naar = rs.getString("naar");
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
        try {
            statTrajectId.setInt(1,id);
            ResultSet rs = statTrajectId.executeQuery();

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
        try {
            statUpdateTraject.setString(1,naam);
            statUpdateTraject.setInt(2,lengte);
            statUpdateTraject.setString(3,start_latitude);
            statUpdateTraject.setString(4,start_longitude);
            statUpdateTraject.setString(5,end_latitude);
            statUpdateTraject.setString(6,end_longitude);
            statUpdateTraject.setBoolean(7,is_active);
            statUpdateTraject.setInt(8,optimale_reistijd);

            ResultSet rs = statUpdateTraject.executeQuery();
        }catch (SQLException e) {
            logger.error("Wijzigen van traject met id "+id+" is mislukt...");
            logger.error(e);
        }
    }

    public List<Waypoint> getWaypoints(int trajectId)
    {
        List<Waypoint> wpts = new ArrayList<>();
        try {
            statWaypoints.setInt(1, trajectId);
            ResultSet rs = statWaypoints.executeQuery();

            Traject traj = getTraject(trajectId);

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
       try {
           statTrajectNaam.setString(1,naam);
            ResultSet rs = statTrajectNaam.executeQuery();

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
