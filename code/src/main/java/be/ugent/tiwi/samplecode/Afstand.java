package be.ugent.tiwi.samplecode;

import be.ugent.tiwi.domein.Waypoint;

/**
 * Created by Eigenaar on 22/03/2016.
 */
public class Afstand {
    private double afstand;
    private Waypoint wp1, wp2;

    public Afstand(Double afstand, Waypoint wp1, Waypoint wp2){
        this.afstand = afstand;
        this.wp1 = wp1;
        this.wp2 = wp2;
    }

    public double getAfstand() {
        return afstand;
    }

    public void setAfstand(double afstand) {
        this.afstand = afstand;
    }

    public Waypoint getWaypoint1() {
        return wp1;
    }

    public void setWp1(Waypoint wp1) {
        this.wp1 = wp1;
    }

    public Waypoint getWaypoint2() {
        return wp2;
    }

    public void setWaypoint2(Waypoint wp2) {
        this.wp2 = wp2;
    }

    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("Afstand: ").append(afstand).append("m - TrajID: ").append(wp1.getTraject().getId()).append(" - volgnrs: ").append(wp1.getVolgnummer()).append("->").append(wp2.getVolgnummer());
        return b.toString();
    }
}
