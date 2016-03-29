package be.ugent.tiwi.domein;

import java.util.List;

/**
 * Created by jelle on 19.02.16.
 */
public class Traject {
    private int id;
    private String naam;
    private int lengte;
    private int optimale_reistijd;      //in seconden
    private boolean is_active;

    private String start_latitude;
    private String start_longitude;
    private String end_latitude;
    private String end_longitude;

    private List<Waypoint> waypoints;

    /**
     * Constructor van de klasse
     */
    public Traject() {}

    /**
     * Constructor van de klasse
     * @param id                Het ID van het traject
     * @param naam              Het naam van het traject
     * @param lengte            De lengte van het traject
     * @param optimale_reistijd De reistijd van het traject
     * @param is_active         Geeft aan of het traject actief is
     * @param start_latitude    De start-latitude van het traject
     * @param start_longitude   De start-longitude van het traject
     * @param end_latitude      De eind-latitude van het traject
     * @param end_longitude     De eind-longitude van het traject
     */
    public Traject(int id, String naam, int lengte, int optimale_reistijd, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude) {
        this.id = id;
        this.naam = naam;
        this.lengte = lengte;
        this.optimale_reistijd = optimale_reistijd;
        this.is_active = is_active;

        this.setStart_latitude(start_latitude);
        this.setStart_longitude(start_longitude);
        this.setEnd_latitude(end_latitude);
        this.setEnd_longitude(end_longitude);
    }

    /**
     * Geeft een kort overzicht van het traject
     * @return Een kort overzicht
     */
    @Override
    public String toString() {
        return "{" +
            "id=" + id +
            ", naam='" + naam + '\'' +
            ", lengte=" + lengte +
            ", optimale_reistijd=" + optimale_reistijd +
            ", is_active=" + is_active +
            ", start_latitude=" + getStart_latitude() +
            ", start_longitude=" + getStart_longitude() +
            ", end_latitude=" + getEnd_latitude() +
            ", end_longitude=" + getEnd_longitude() +
        '}';
    }

    /**
     * Geeft het ID van het traject terug
     * @return Het ID van het traject
     */
    public int getId() {
        return id;
    }

    /**
     * Geeft de naam van het traject terug
     * @return De naam van het traject
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geeft de lengte van het traject terug (in meter)
     * @return De lengte van het traject (in meter)
     */
    public int getLengte() {
        return lengte;
    }

    /**
     * Geeft de optimale reistijd van het traject terug
     * @return De optimale reistijd van het traject
     */
    public int getOptimale_reistijd() {
        return optimale_reistijd;
    }

    /**
     * Geeft aan of het traject actief is
     * @return Een boolean die aangeeft of het traject actief is
     */
    public boolean is_active() {
        return is_active;
    }

    /**
     * Wijzigt het ID van het traject
     * @param id Het nieuwe ID van het traject
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Wijzigt de naam van het traject
     * @param naam De nieuwe naam van het traject
     */
    public void setNaam(String naam) {
        this.naam = naam;
    }

    /**
     * Wijzigt de lengte van het traject
     * @param lengte De nieuwe lengte van het traject (in meter)
     */
    public void setLengte(int lengte) {
        this.lengte = lengte;
    }

    /**
     * Wijzigt de optimale reistijd van het traject
     * @param optimale_reistijd De optimale reistijd
     */
    public void setOptimale_reistijd(int optimale_reistijd) {
        this.optimale_reistijd = optimale_reistijd;
    }

    /**
     * Maakt het traject al dan niet actief
     * @param is_active Een boolean die aangeeft of het traject actief moet worden of niet
     */
    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    /**
     * Geeft alle waypoints van het traject terug
     * @return Een lijst van waypoints
     */
    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    /**
     * Wijzigt de waypoints van het traject
     * @param waypoints Een lijst van nieuwe waypoints
     */
    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    /**
     * Geeft de start-latitude van het traject terug
     * @return De start-latitude van het traject
     */
    public String getStart_latitude() {
        return start_latitude;
    }

    /**
     * Wijzigt de start-latitude van het traject
     * @param start_latitude De nieuwe start-latitude
     */
    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    /**
     * Geeft de start-longitude van het traject terug
     * @return De start-longitude van het traject
     */
    public String getStart_longitude() {
        return start_longitude;
    }

    /**
     * Wijzigt de start-longitude van het traject
     * @param start_longitude De nieuwe start-longitude
     */
    public void setStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
    }

    /**
     * Geeft de eind-latitude van het traject terug
     * @return De eind-latitude van het traject
     */
    public String getEnd_latitude() {
        return end_latitude;
    }

    /**
     * Wijzigt de end-latitude van het traject
     * @param end_latitude De nieuwe end-latitude
     */
    public void setEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    /**
     * Geeft de eind-longitude van het traject terug
     * @return De eind-longitude van het traject
     */
    public String getEnd_longitude() {
        return end_longitude;
    }

    /**
     * Wijzigt de end-longitude van het traject
     * @param end_longitude De nieuwe end-longitude
     */
    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

}
