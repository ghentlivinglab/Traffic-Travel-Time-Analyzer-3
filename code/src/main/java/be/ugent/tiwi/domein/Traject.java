package be.ugent.tiwi.domein;

import java.util.List;

/**
 * Created by jelle on 19.02.16.
 */
public class Traject {
    private int id;
    private boolean is_omgekeerd;
    private String naam;
    private String van;
    private String naar;
    private int lengte;
    private int optimale_reistijd;      //in seconden
    private boolean is_active;

    private List<Waypoint> waypoints;

    public Traject() {}

    public Traject(int id, boolean is_omgekeerd, String naam, String van, String naar, int lengte, int optimale_reistijd, boolean is_active) {
        this.id = id;
        this.is_omgekeerd = is_omgekeerd;
        this.naam = naam;
        this.setVan(van);
        this.setNaar(naar);
        this.lengte = lengte;
        this.optimale_reistijd = optimale_reistijd;
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "Traject{" +
            "id=" + id +
            ", naam='" + naam + '\'' +
            ", lengte=" + lengte +
            ", optimale_reistijd=" + optimale_reistijd +
            ", is_active=" + is_active +
            '}';
    }

    public int getId() {
        return id;
    }

    public boolean is_omgekeerd() {
        return is_omgekeerd;
    }

    public String getNaam() {
        return naam;
    }

    public int getLengte() {
        return lengte;
    }

    public int getOptimale_reistijd() {
        return optimale_reistijd;
    }

    public boolean is_active() {
        return is_active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void set_omgekeerd (boolean is_omgekeerd){
        this.is_omgekeerd = is_omgekeerd;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setLengte(int lengte) {
        this.lengte = lengte;
    }

    public void setOptimale_reistijd(int optimale_reistijd) {
        this.optimale_reistijd = optimale_reistijd;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getVan() {
        return van;
    }

    public void setVan(String van) {
        this.van = van;
    }

    public String getNaar() {
        return naar;
    }

    public void setNaar(String naar) {
        this.naar = naar;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }
}
