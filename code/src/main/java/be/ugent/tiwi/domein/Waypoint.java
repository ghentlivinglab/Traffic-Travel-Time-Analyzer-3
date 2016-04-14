package be.ugent.tiwi.domein;

/**
 * Created by jelle on 07.03.16.
 */
public class Waypoint {
    private int volgnummer;
    private String latitude;
    private String longitude;

    /**
     * Constructor van de klasse
     *
     * @param volgnummer Het volgnummer van de waypoint
     * @param latitude   De latitude van de waypoint
     * @param longitude  De longitude van de waypoint
     */
    public Waypoint(int volgnummer, String latitude, String longitude) {
        this.volgnummer = volgnummer;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Geeft het volgnummer terug
     *
     * @return Het volgnummer van de waypoint
     */
    public int getVolgnummer() {
        return volgnummer;
    }

    /**
     * Wijzigt het volgnummer
     *
     * @param volgnummer Het nieuwe volgnummer van de waypoint
     */
    public void setVolgnummer(int volgnummer) {
        this.volgnummer = volgnummer;
    }

    /**
     * Geeft de latitude terug
     *
     * @return De latitude van het waypoint
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Wijzigt de latitude
     *
     * @param latitude De nieuwe latitude van de waypoint
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Geeft de longitude terug
     *
     * @return De longitude van het waypoint
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Wijzigt de longitude
     *
     * @param longitude De nieuwe longitude van de waypoint
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Geeft een kort overzicht van het waypoint terug
     *
     * @return Een kort overzicht
     */
    @Override
    public String toString() {
        return "{" +
                ", volgnr=" + volgnummer +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
