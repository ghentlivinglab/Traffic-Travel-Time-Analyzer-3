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
                String van = rs.getString("van");
                String naar = rs.getString("naar");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getBoolean("is_active");
                trajecten.add(new Traject(id, false, naam, van, naar, lengte, optimale_reistijd, is_active));

                naam = rs.getString("omgekeerde_naam");
                lengte = rs.getInt("omgekeerde_lengte");
                optimale_reistijd = rs.getInt("omgekeerde_optimale_reistijd");
                trajecten.add(new Traject(id, true, naam, naar, van, lengte, optimale_reistijd, is_active));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trajecten;
    }

    public Traject getTraject(int id, boolean omgekeerd) {
        String query = "select * from trajecten where id='" + id + "'";

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String naam;
                String van, naar;
                int lengte, optimale_reistijd;
                if(omgekeerd) {
                    naam = rs.getString("omgekeerde_naam");
                    van = rs.getString("naar");
                    naar = rs.getString("van");
                    lengte = rs.getInt("omgekeerde_lengte");
                    optimale_reistijd = rs.getInt("omgekeerde_optimale_reistijd");
                }else {
                    naam = rs.getString("naam");
                    van = rs.getString("van");
                    naar = rs.getString("naar");
                    lengte = rs.getInt("lengte");
                    optimale_reistijd = rs.getInt("optimale_reistijd");
                }
                boolean is_active = rs.getBoolean("is_active");

                return new Traject(id, omgekeerd, naam, van, naar, lengte, optimale_reistijd, is_active);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void wijzigTraject(int id, String naam, String omgekeerde_naam, String van, String naar, int lengte, int omgekeerde_lengte, int optimale_reistijd, int omgekeerde_optimale_reistijd, boolean is_active)
    {
        String query = "update trajecten set naam=\""+naam+"\","+
                "omgekeerde_naam=\""+omgekeerde_naam+"\","+
                "van=\""+van+"\","+
                "naar=\""+naar+"\","+
                "lengte="+lengte+","+
                "omgekeerde_lengte="+omgekeerde_lengte+","+
                "optimale_reistijd="+optimale_reistijd+","+
                "omgekeerde_optimale_reistijd=\""+optimale_reistijd+","+
                "is_active="+is_active+
             " where id="+id;

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }catch (SQLException e) {
            logger.error("Wijzigen van traject met id "+id+" is mislukt...");
            logger.error(e);
        }
    }

    public List<Waypoint> getWaypoints(int trajectid, boolean omgekeerd)
    {
        int i;
        if(omgekeerd)
            i = 1;
        else
            i = 0;
        String query = "select * from waypoints where traject_id='" + trajectid + "' and omgekeerd = " + i;
        List<Waypoint> wpts = new ArrayList<>();
        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Traject traj = getTraject(trajectid,omgekeerd);

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
        String query = "select * from trajecten where naam='" + naam + "' or omgekeerde_naam='"+naam+"'";

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id, lengte, optimale_reistijd;
                boolean omgekeerd;
                String van, naar;
                if(naam.equals(rs.getString("naam"))){
                    lengte = rs.getInt("lengte");
                    optimale_reistijd = rs.getInt("optimale_reistijd");
                    van = rs.getString("van");
                    naar = rs.getString("naar");
                    omgekeerd = false;
                }else{
                    lengte = rs.getInt("omgekeerde_lengte");
                    optimale_reistijd = rs.getInt("omgekeerde_optimale_reistijd");
                    van = rs.getString("naar");
                    naar = rs.getString("van");
                    omgekeerd = true;
                }

                id = rs.getInt("id");
                boolean is_active = rs.getBoolean("is_active");
                return new Traject(id, omgekeerd, naam, van, naar, lengte, optimale_reistijd, is_active);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Traject> getTrajectenMetCoordinaten() {
        List<Traject> trajecten = getTrajecten();
        for(Traject t : trajecten){
            t.setWaypoints(getWaypoints(t.getId(), t.is_omgekeerd()));
        }
        return trajecten;
    }
}
