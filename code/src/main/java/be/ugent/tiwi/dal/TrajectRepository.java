package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
import org.apache.logging.log4j.LogManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jelle on 19.02.16.
 */
public class TrajectRepository implements ITrajectRepository {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(TrajectRepository.class);
    private final DBConnector connector;
    private PreparedStatement statTrajecten = null;

    private String stringTrajecten = "select * from trajecten";

    /**
     * Constructor van de klasse
     */
    public TrajectRepository() {
        connector = new DBConnector();
    }

    /**
     * Geeft alle trajecten in de database terug
     *
     * @return Een lijst van trajecten
     */
    @Override
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

                trajecten.add(new Traject(id, naam, lengte, optimale_reistijd, getOptimaleReistijden(id), is_active, start_latitude, start_longitude, end_latitude, end_longitude));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trajecten;
    }

    private Map<Integer,Integer> getOptimaleReistijden(int id) {
        Map<Integer, Integer> reistijden = new HashMap<>();
        ResultSet rs = null;
        try{
            String stringOptimaleReistijden = "select * from optimale_reistijden where traject_id = ?";
            statTrajecten = connector.getConnection().prepareStatement(stringOptimaleReistijden);
            statTrajecten.setInt(1, id);
            rs = statTrajecten.executeQuery();

            while(rs.next())
                reistijden.put(rs.getInt("provider_id"), rs.getInt("reistijd"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        return reistijden;
    }

    /**
     * Geeft alle trajecten in de database terug met alle waypoints per traject
     *
     * @return Een lijst van trajecten
     */
    @Override
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
                Traject traject = new Traject(id, naam, lengte, optimale_reistijd, getOptimaleReistijden(id), is_active, start_latitude, start_longitude, end_latitude, end_longitude);
                traject.setWaypoints(getWaypoints(id));
                trajecten.add(traject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trajecten;
    }

    /**
     * Geeft een traject terug met het opgegeven ID
     *
     * @param id Het ID van het traject
     * @return Het gevraagde traject. Indien het ID niet bestaat, null.
     */
    @Override
    public Traject getTraject(int id) {
        ResultSet rs = null;
        try {
            String stringTrajectId = "select * from trajecten where id= ?";
            statTrajecten = connector.getConnection().prepareStatement(stringTrajectId);
            statTrajecten.setInt(1, id);
            rs = statTrajecten.executeQuery();

            if(rs.next()) {

                String naam = rs.getString("naam");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                String start_latitude = rs.getString("start_latitude"), start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude"), end_longitude = rs.getString("end_longitude");
                boolean is_active = rs.getBoolean("is_active");
                return new Traject(id, naam, lengte, optimale_reistijd, getOptimaleReistijden(id), is_active, start_latitude, start_longitude, end_latitude, end_longitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        return null;
    }

    /**
     * Geeft een traject terug met het opgegeven ID, aangevuld met al zijn waypoints.
     *
     * @param id Het ID van het traject
     * @return Een traject met zijn waypoints. Indien het ID niet bestaat, null.
     */
    @Override
    public Traject getTrajectMetWaypoints(int id) {
        Traject result = getTraject(id);
        if (result != null)
            result.setWaypoints(getWaypoints(id));
        return result;
    }

    /**
     * Wijzigt de informatie van het traject die het opgegeven ID heeft
     *
     * @param id                Het ID van het traject dat gewijzigd moet worden
     * @param naam              De nieuwe naam
     * @param lengte            De nieuwe lengte
     * @param optimale_reistijd De nieuwe reistijd
     * @param is_active         Geeft aan of het traject actief is
     * @param start_latitude    De nieuwe begin-latitude
     * @param start_longitude   De nieuwe begin-longitude
     * @param end_latitude      De nieuwe eind-latitude
     * @param end_longitude     De nieuwe eind-longitude
     * @param waypoints         Een lijst van nieuwe waypoints
     */
    @Override
    public void wijzigTraject(int id, String naam, int lengte, int optimale_reistijd, Map<Integer, Integer> optimaleReistijden, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude, List<Waypoint> waypoints) {
        ResultSet rs = null;
        try {
            String stringUpdateTraject = "update trajecten set naam= ?, lengte=?, start_latitude= ?, start_longitude= ?, end_latitude= ?, end_longitude= ?, is_active= ?, optimale_reistijd= ? where id= ?";
            statTrajecten = connector.getConnection().prepareStatement(stringUpdateTraject);
            statTrajecten.setString(1, naam);
            statTrajecten.setInt(2, lengte);
            statTrajecten.setString(3, start_latitude);
            statTrajecten.setString(4, start_longitude);
            statTrajecten.setString(5, end_latitude);
            statTrajecten.setString(6, end_longitude);
            statTrajecten.setBoolean(7, is_active);
            statTrajecten.setInt(8, optimale_reistijd);
            statTrajecten.setInt(9, id);
            rs = statTrajecten.executeQuery();
            wijzigWaypoints(id, waypoints);
            wijzigOptimaleReistijden(id, optimaleReistijden);
        } catch (SQLException e) {
            logger.error("Wijzigen van traject met id " + id + " is mislukt...");
            logger.error(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    private void wijzigOptimaleReistijden(int id, Map<Integer, Integer> optimaleReistijden) {
        try{
            String stringDeleteReistijden = "delete from optimale_reistijden where traject_id = ?";
            statTrajecten = connector.getConnection().prepareStatement(stringDeleteReistijden);
            statTrajecten.setInt(1, id);
            statTrajecten.execute();
        } catch (SQLException e) {
            logger.error("Verwijderen van de optimale reistijden van traject met id " + id + " is mislukt...");
            logger.error(e);
        }finally {
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        try{
            String stringAddReistijd = "insert into optimale_reistijden(traject_id, provider_id, reistijd) values (?, ?, ?)";
            statTrajecten = connector.getConnection().prepareStatement(stringAddReistijd);
            statTrajecten.setInt(1, id);
            if (optimaleReistijden==null){
                optimaleReistijden = getOptimaleReistijden(id);
            }
            for(Integer provider_id : optimaleReistijden.keySet()){
                statTrajecten.setInt(2, provider_id);
                statTrajecten.setInt(3, optimaleReistijden.get(provider_id));
                statTrajecten.addBatch();
            }
            statTrajecten.executeBatch();
        } catch (SQLException e) {
            logger.error("Toevoegen van de optimale reistijden van traject met id " + id + " is mislukt...");
            logger.error(e);
        }finally {
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    /**
     * Wijzigt een traject met een ID en past de waypoints aan
     *
     * @param id        Het ID van het traject dat gewijzigd moet worden
     * @param waypoints Een lijst van nieuwe waypoints
     */
    @Override
    public void wijzigWaypoints(int id, List<Waypoint> waypoints) {
        try {
            String stringUpdateWaypoints = "insert into waypoints(traject_id, volgnr, latitude, longitude) values(?,?,?,?) on duplicate key update latitude = values(latitude), longitude = values(longitude);";
            statTrajecten = connector.getConnection().prepareStatement(stringUpdateWaypoints);
            statTrajecten.setInt(1, id);
            for (Waypoint waypoint : waypoints) {
                statTrajecten.setInt(2, waypoint.getVolgnummer());
                statTrajecten.setString(3, waypoint.getLatitude());
                statTrajecten.setString(4, waypoint.getLongitude());
                statTrajecten.addBatch();
            }
            statTrajecten.executeBatch();
        } catch (SQLException e) {
            logger.error("Wijzigen van de waypoints van traject met id " + id + " is mislukt...");
            logger.error(e);
        } finally {
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        try {
            String stringDeleteWaypoints = "delete from waypoints where traject_id = ? and volgnr>?";
            statTrajecten = connector.getConnection().prepareStatement(stringDeleteWaypoints);

            statTrajecten.setInt(1, id);
            statTrajecten.setInt(2, waypoints.size());
            statTrajecten.executeQuery();
        } catch (SQLException e) {
            logger.error("Verwijderen van deleted waypoints mislukt");
            logger.error(e);
        } finally {
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    /**
     * Geeft alle waypoints terug van een traject
     *
     * @param trajectId Het ID van het traject waarvan de waypoints opgevraagd worden
     * @return Een lijst van waypoints. Indien het traject_id niet bestaat, een lege lijst.
     */
    @Override
    public List<Waypoint> getWaypoints(int trajectId) {
        List<Waypoint> wpts = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringWaypoints = "select * from waypoints where traject_id= ? order by volgnr asc";
            statTrajecten = connector.getConnection().prepareStatement(stringWaypoints);
            statTrajecten.setInt(1, trajectId);
            rs = statTrajecten.executeQuery();

            while (rs.next()) {
                int volgnr = rs.getInt("volgnr");
                String lat = rs.getString("latitude");
                if (rs.wasNull())
                    lat = null;
                String lon = rs.getString("longitude");
                if (rs.wasNull())
                    lon = null;
                wpts.add(new Waypoint(volgnr, lat, lon));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        return wpts;
    }

    /**
     * Geeft een traject terug met de opgegeven naam
     *
     * @param naam De naam van het traject
     * @return Een traject. Indien de naam niet bestaat, null.
     */
    @Override
    public Traject getTraject(String naam) {
        ResultSet rs = null;
        try {
            String stringTrajectNaam = "select * from trajecten where naam= ?";
            statTrajecten = connector.getConnection().prepareStatement(stringTrajectNaam);
            statTrajecten.setString(1, naam);
            rs = statTrajecten.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getBoolean("is_active");
                String start_latitude = rs.getString("start_latitude"), start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude"), end_longitude = rs.getString("end_longitude");
                return new Traject(id, naam, lengte, optimale_reistijd, getOptimaleReistijden(id), is_active, start_latitude, start_longitude, end_latitude, end_longitude);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statTrajecten.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        return null;
    }

    /**
     * Geeft alle trajecten terug met alle waypoints per traject
     *
     * @return Een lijst van trajecten.
     */
    @Override
    public List<Traject> getTrajectenMetCoordinaten() {
        List<Traject> trajecten = getTrajecten();
        for (Traject t : trajecten) {
            t.setWaypoints(getWaypoints(t.getId()));
        }
        return trajecten;
    }

}
