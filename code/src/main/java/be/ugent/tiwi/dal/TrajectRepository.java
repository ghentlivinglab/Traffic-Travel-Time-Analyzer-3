package be.ugent.tiwi.dal;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.util.StringBuilders;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by jelle on 19.02.16.
 */
public class TrajectRepository {
    private DBConnector connector;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(TrajectRepository.class);

    private PreparedStatement statTrajecten = null;
    private PreparedStatement statTrajectId = null;
    private PreparedStatement statUpdateTraject = null;
    private PreparedStatement statWaypoints = null;
    private PreparedStatement statTrajectNaam = null;
    private PreparedStatement statUpdateWaypoints = null;
    private PreparedStatement statDeleteWaypoints = null;

    private String stringTrajecten = "select * from trajecten";
    private String stringTrajectId = "select * from trajecten where id= ?";
    private String stringUpdateTraject = "update trajecten set naam= ?, lengte=?, start_latitude= ?, start_longitude= ?, end_latitude= ?, end_longitude= ?, is_active= ?, optimale_reistijd= ? where id= ?";
    private String stringWaypoints = "select * from waypoints where traject_id= ? order by volgnr asc";
    private String stringTrajectNaam = "select * from trajecten where naam= ?";
    private String stringUpdateWaypoints = "insert into waypoints(traject_id, volgnr, latitude, longitude) values(?,?,?,?) on duplicate key update latitude = values(latitude), longitude = values(longitude);";
    private String stringDeleteWaypoints = "delete from waypoints where traject_id = ? and volgnr>?";

    public TrajectRepository() {
        connector = new DBConnector();
    }

    public List<Traject> getTrajecten() {
        List<Traject> trajecten = new ArrayList<Traject>();
        ResultSet rs = null;
        try {
            statTrajecten = connector.getConnection().prepareStatement(stringTrajecten);
            rs = statTrajecten.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statTrajecten.close(); } catch (Exception e) { /* ignored */ }
            try {  connector.close(); } catch (Exception e) { /* ignored */ }

        }
        return trajecten;
    }

    public List<Traject> getTrajectenMetWayPoints() {
        List<Traject> trajecten = new ArrayList<Traject>();
        ResultSet rs = null;
        try {
            statTrajecten = connector.getConnection().prepareStatement(stringTrajecten);
            rs = statTrajecten.executeQuery();

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
                Traject traject = new Traject(id, naam, lengte, optimale_reistijd, is_active, start_latitude, start_longitude, end_latitude, end_longitude);
                traject.setWaypoints(getWaypoints(id));
                trajecten.add(traject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statTrajecten.close(); } catch (Exception e) { /* ignored */ }
            try {  connector.close(); } catch (Exception e) { /* ignored */ }

        }
        return trajecten;
    }

    public Traject getTraject(int id) {
        ResultSet rs = null;
        try {
            statTrajectId = connector.getConnection().prepareStatement(stringTrajectId);
            statTrajectId.setInt(1,id);
            rs = statTrajectId.executeQuery();

            while (rs.next()) {

                String naam = rs.getString("naam");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                String start_latitude = rs.getString("start_latitude"), start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude"), end_longitude = rs.getString("end_longitude");
                boolean is_active = rs.getBoolean("is_active");
                return new Traject(id, naam, lengte, optimale_reistijd, is_active, start_latitude, start_longitude, end_latitude, end_longitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statTrajectId.close(); } catch (Exception e) { /* ignored */ }
            try {  connector.close(); } catch (Exception e) { /* ignored */ }
        }
        return null;
    }

    public Traject getTrajectMetWaypoints(int id) {
        Traject result = getTraject(id);
        result.setWaypoints(getWaypoints(id));
        return result;
    }

    public void wijzigTraject(int id, String naam, int lengte, int optimale_reistijd, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude, List<Waypoint> waypoints)
    {
        ResultSet rs = null;
        try {
            statUpdateTraject = connector.getConnection().prepareStatement(stringUpdateTraject);
            statUpdateTraject.setString(1,naam);
            statUpdateTraject.setInt(2,lengte);
            statUpdateTraject.setString(3,start_latitude);
            statUpdateTraject.setString(4,start_longitude);
            statUpdateTraject.setString(5,end_latitude);
            statUpdateTraject.setString(6,end_longitude);
            statUpdateTraject.setBoolean(7,is_active);
            statUpdateTraject.setInt(8,optimale_reistijd);
            statUpdateTraject.setInt(9,id);
            rs = statUpdateTraject.executeQuery();
            wijzigWaypoints(id, waypoints);
        }catch (SQLException e) {
            logger.error("Wijzigen van traject met id "+id+" is mislukt...");
            logger.error(e);
        }finally{
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statUpdateTraject.close(); } catch (Exception e) { /* ignored */ }
            try {  connector.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public void wijzigWaypoints(int id, List<Waypoint> waypoints)
    {
        try {
            statUpdateWaypoints = connector.getConnection().prepareStatement(stringUpdateWaypoints);
            for(Waypoint waypoint: waypoints) {
                statUpdateWaypoints.setInt(1, id);
                statUpdateWaypoints.setInt(2, waypoint.getVolgnummer());
                statUpdateWaypoints.setString(3, waypoint.getLatitude());
                statUpdateWaypoints.setString(4, waypoint.getLongitude());
                statUpdateWaypoints.executeQuery();
            }
        }catch (SQLException e) {
            logger.error("Wijzigen van de waypoints van traject met id "+id+" is mislukt...");
            logger.error(e);
        }finally{
            try { statUpdateTraject.close(); } catch (Exception e) { /* ignored */ }
            try {  connector.close(); } catch (Exception e) { /* ignored */ }
        }
        try {
            statDeleteWaypoints = connector.getConnection().prepareStatement(stringDeleteWaypoints);

            statDeleteWaypoints.setInt(1, id);
            statDeleteWaypoints.setInt(2, waypoints.size());
            statDeleteWaypoints.executeQuery();

        }catch (SQLException e) {
            logger.error("Verwijderen van deleted waypoints mislukt");
            logger.error(e);
        }finally{
            try { statDeleteWaypoints.close(); } catch (Exception e) { /* ignored */ }
            try {  connector.close(); } catch (Exception e) { /* ignored */ }
        }



    }

    public List<Waypoint> getWaypoints(int trajectId)
    {
        List<Waypoint> wpts = new ArrayList<>();
        ResultSet rs = null;
        try {
            statWaypoints = connector.getConnection().prepareStatement(stringWaypoints);
            statWaypoints.setInt(1, trajectId);
            rs = statWaypoints.executeQuery();

            while (rs.next())
            {
                int volgnr = rs.getInt("volgnr");
                String lat = rs.getString("latitude");
                if (rs.wasNull())
                    lat = null;
                String lon = rs.getString("longitude");
                if (rs.wasNull())
                    lon = null;
                wpts.add(new Waypoint(volgnr,lat,lon));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statUpdateTraject.close(); } catch (Exception e) { /* ignored */ }
            try {  connector.close(); } catch (Exception e) { /* ignored */ }
        }
        return wpts;
    }

    public Traject getTraject(String naam) {
        ResultSet rs= null;
       try {
           statTrajectNaam = connector.getConnection().prepareStatement(stringTrajectNaam);
           statTrajectNaam.setString(1,naam);
            rs = statTrajectNaam.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getBoolean("is_active");
                String start_latitude = rs.getString("start_latitude"), start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude"), end_longitude = rs.getString("end_longitude");
                return new Traject(id,  naam, lengte, optimale_reistijd, is_active, start_latitude,start_longitude,end_latitude,end_longitude);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
           try { rs.close(); } catch (Exception e) { /* ignored */ }
           try { statTrajectNaam.close(); } catch (Exception e) { /* ignored */ }
           try {  connector.close(); } catch (Exception e) { /* ignored */ }
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
