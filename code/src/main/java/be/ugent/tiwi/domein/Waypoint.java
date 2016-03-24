package be.ugent.tiwi.domein;

/**
 * Created by jelle on 07.03.16.
 */
public class Waypoint {
    private int volgnummer;
    private String latitude;
    private String longitude;

    public Waypoint(int volgnummer, String latitude, String longitude) {
        this.volgnummer = volgnummer;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getVolgnummer() {
        return volgnummer;
    }

    public void setVolgnummer(int volgnummer) {
        this.volgnummer = volgnummer;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Waypoint{" +
                ", volgnummer=" + volgnummer +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
