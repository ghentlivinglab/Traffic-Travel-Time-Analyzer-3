package be.ugent.tiwi.domein;

/**
 * Created by jelle on 19.02.16.
 */
public class Traject {
    private int id;
    private String letter;
    private String naam;
    private int lengte;
    private int optimale_reistijd;      //in seconden
    private boolean is_active;
    private String start_latitude;
    private String start_longitude;
    private String end_latitude;
    private String end_longitude;

    public Traject(int id, String letter, String naam, int lengte, int optimale_reistijd, boolean is_active, String start_latitude, String start_longitude, String end_latitude, String end_longitude)
    {
        this.id = id;
        this.letter = letter;
        this.naam = naam;
        this.lengte = lengte;
        this.optimale_reistijd = optimale_reistijd;
        this.is_active = is_active;
        this.start_latitude = start_latitude;
        this.start_longitude = start_longitude;
        this.end_latitude = end_latitude;
        this.end_longitude = end_longitude;
    }

    @Override
    public String toString() {
        return "Traject{" +
                "id=" + id +
                ", letter='" + letter + '\'' +
                ", naam='" + naam + '\'' +
                ", lengte=" + lengte +
                ", optimale_reistijd=" + optimale_reistijd +
                ", is_active=" + is_active +
                ", start_latitude='" + start_latitude + '\'' +
                ", start_longitude='" + start_longitude + '\'' +
                ", end_latitude='" + end_latitude + '\'' +
                ", end_longitude='" + end_longitude + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getLetter() {
        return letter;
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

    public String getStart_latitude() {
        return start_latitude;
    }

    public String getStart_longitude() {
        return start_longitude;
    }

    public String getEnd_latitude() {
        return end_latitude;
    }

    public String getEnd_longitude() {
        return end_longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLetter(String letter) {
        this.letter = letter;
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

    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public void setStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
    }

    public void setEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }
}
