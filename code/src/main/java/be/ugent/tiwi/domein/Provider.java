package be.ugent.tiwi.domein;

import java.util.List;

/**
 * Created by jelle on 18.02.16.
 */
public class Provider {
    private int id;
    private String naam;
    private boolean is_active;
    private List<Meting> metingen;

    public Provider() {
    }

    public Provider(int id, String naam, boolean is_active) {
        this.id = id;
        this.naam = naam;
        this.is_active = is_active;
    }

    public String to_string() {
        return "De provider heet " + this.naam + " en is" + (this.is_active ? "" : "niet") + " actief";
    }

    public String getNaam() {
        return naam;
    }

    public boolean is_active() {
        return is_active;
    }

    public int getId() {
        return id;
    }

    public List<Meting> getMetingen() {
        return metingen;
    }

    public void setMetingen(List<Meting> metingen) {
        this.metingen = metingen;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
