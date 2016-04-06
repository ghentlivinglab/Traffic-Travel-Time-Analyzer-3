package be.ugent.tiwi.samplecode;

import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;

/**
 * Created by Eigenaar on 22/03/2016.
 */
public class Afstand {
    private double afstand;
    private Waypoint wp1, wp2;

    private Traject traject;

    /**
     * Constructor van de klasse
     * @param afstand   De afstand tussen Waypoints **wp1** en **wp2** in meter
     * @param wp1       De eerste waypoint
     * @param wp2       De tweede waypoint
     * @param traject   Het traject waaronder beide waypoints vallen
     */
    public Afstand(Double afstand, Waypoint wp1, Waypoint wp2, Traject traject){
        this.afstand = afstand;
        this.wp1 = wp1;
        this.wp2 = wp2;
        this.traject = traject;
    }

    /**
     * Geeft de afstand terug
     * @return De afstand tussen de 2 waypoints
     */
    public double getAfstand() {
        return afstand;
    }

    /**
     * Wijzigt de afstand
     * @param afstand De nieuwe afstand tussen de 2 waypoints
     */
    public void setAfstand(double afstand) {
        this.afstand = afstand;
    }

    /**
     * Geeft de eerste waypoint terug
     * @return De eerste waypoint
     * @see Waypoint
     */
    public Waypoint getWaypoint1() {
        return wp1;
    }

    /**
     * Wijzigt de eerste waypoint
     * @param wp1 De nieuwe eerste waypoint
     * @see Waypoint
     */
    public void setWp1(Waypoint wp1) {
        this.wp1 = wp1;
        this.afstand = distance(Double.valueOf(wp1.getLatitude()), Double.valueOf(wp2.getLatitude()), Double.valueOf(wp1.getLongitude()), Double.valueOf(wp2.getLongitude()), 0, 0);
    }

    /**
     * Geeft de tweede waypoint terug
     * @return De tweede waypoint
     * @see Waypoint
     */
    public Waypoint getWaypoint2() {
        return wp2;
    }

    /**
     * Wijzigt de tweede waypoint
     * @param wp2 De nieuwe tweede waypoint
     * @see Waypoint
     */
    public void setWaypoint2(Waypoint wp2) {
        this.wp2 = wp2;
        this.afstand = distance(Double.valueOf(wp1.getLatitude()), Double.valueOf(wp2.getLatitude()), Double.valueOf(wp1.getLongitude()), Double.valueOf(wp2.getLongitude()), 0, 0);
    }

    /**
     * Geeft het traject van de waypoints terug
     * @return Het traject
     * @see Traject
     */
    public Traject getTraject() {
        return traject;
    }

    /**
     * Wijzigt het traject van de twee waypoints
     * @param traject Het nieuwe traject
     * @see Traject
     */
    public void setTraject(Traject traject) {
        this.traject = traject;
    }

    /**
     * Geeft een kort overzicht van de gegevens tussen de twee waypoints
     * @return Een kort overzicht van het object
     */
    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("Afstand: ").append(afstand).append("m - TrajID: ").append(traject.getId()).append(" - volgnrs: ").append(wp1.getVolgnummer()).append("->").append(wp2.getVolgnummer());
        return b.toString();
    }

    /**
     * Berekent de afstand tussen 2 coordinaten. De functie houdt rekening met het hoogteverschil.
     * @param lat1  De latitude van de eerste waypoint
     * @param lat2  De latitude van de tweede waypoint
     * @param lon1  De longitude van de eerste waypoint
     * @param lon2  De longitude van de tweede waypoint
     * @param el1   Het hoogteverschil tussen het eerste waypoint en de zeespiegel (in meter)
     * @param el2   Het hoogteverschil tussen het tweede waypoint en de zeespiegel (in meter)
     * @return De afstand tussen de twee coordinaten
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = distance*distance + height*height;

        return Math.sqrt(distance);
    }
}
