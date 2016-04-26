package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;

import java.util.List;
import java.util.Map;

/**
 * Created by brent on 14/04/2016.
 */
public interface ITrajectRepository {
    /**
     * Geeft alle trajecten in de database terug
     *
     * @return Een lijst van trajecten
     */
    List<Traject> getTrajecten();

    /**
     * Geeft alle trajecten in de database terug met alle waypoints per traject
     *
     * @return Een lijst van trajecten
     */
    List<Traject> getTrajectenMetWayPoints();

    /**
     * Geeft een traject terug met het opgegeven ID
     *
     * @param id Het ID van het traject
     * @return Het gevraagde traject. Indien het ID niet bestaat, null.
     */
    Traject getTraject(int id);

    /**
     * Geeft een traject terug met het opgegeven ID, aangevuld met al zijn waypoints.
     *
     * @param id Het ID van het traject
     * @return Een traject met zijn waypoints. Indien het ID niet bestaat, null.
     */
    Traject getTrajectMetWaypoints(int id);

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
    void wijzigTraject(int id, String naam, int lengte, int optimale_reistijd, Map<Integer, Integer> optimaleReistijden, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude, List<Waypoint> waypoints);

    /**
     * Wijzigt een traject met een ID en past de waypoints aan
     *
     * @param id        Het ID van het traject dat gewijzigd moet worden
     * @param waypoints Een lijst van nieuwe waypoints
     */
    void wijzigWaypoints(int id, List<Waypoint> waypoints);

    /**
     * Geeft alle waypoints terug van een traject
     *
     * @param trajectId Het ID van het traject waarvan de waypoints opgevraagd worden
     * @return Een lijst van waypoints. Indien het traject_id niet bestaat, een lege lijst.
     */
    List<Waypoint> getWaypoints(int trajectId);

    /**
     * Geeft een traject terug met de opgegeven naam
     *
     * @param naam De naam van het traject
     * @return Een traject. Indien de naam niet bestaat, null.
     */
    Traject getTraject(String naam);

    /**
     * Geeft alle trajecten terug met alle waypoints per traject
     *
     * @return Een lijst van trajecten.
     */
    List<Traject> getTrajectenMetCoordinaten();

    /**
     * Voegt een nieuw traject aan de databank toe
     *
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
    void addTraject(String naam, int lengte, int optimale_reistijd, Map<Integer, Integer> optimaleReistijden, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude, List<Waypoint> waypoints);
}
