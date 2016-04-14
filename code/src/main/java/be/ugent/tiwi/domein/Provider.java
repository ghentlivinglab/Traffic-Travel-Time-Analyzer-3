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

    /**
     * Constructor van de klasse
     */
    public Provider() {
    }


    /**
     * Constructor van de klasse die 3 parameters invult
     *
     * @param id        Het ID van de provider
     * @param naam      De naam van de provider
     * @param is_active Geeft aan of de provider actief is.
     */
    public Provider(int id, String naam, boolean is_active) {
        this.id = id;
        this.naam = naam;
        this.is_active = is_active;
    }

    /**
     * Geeft de naam van de provider
     *
     * @return De naam van de provider
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geeft aan of de provider actief is
     *
     * @return Een boolean die aangeeft of de provider actief is
     */
    public boolean is_active() {
        return is_active;
    }

    /**
     * Stelt in of de provider actief is
     *
     * @param is_active Een boolean die aangeeft of de provider actief is
     */
    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    /**
     * Geeft het ID van de provider terug
     *
     * @return Het ID van de provider
     */
    public int getId() {
        return id;
    }

    /**
     * Geeft een lijst van alle metingen terug
     *
     * @return Een lijst van metingen
     */
    public List<Meting> getMetingen() {
        return metingen;
    }

    /**
     * Wijzig de lijst van metingen van de provider
     *
     * @param metingen Een lijst van nieuwe metingen
     */
    public void setMetingen(List<Meting> metingen) {
        this.metingen = metingen;
    }

    /**
     * Geeft een kort overzicht van de provider
     *
     * @return een kort overzicht
     */
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
