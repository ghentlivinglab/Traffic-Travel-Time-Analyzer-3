package be.ugent.tiwi.domein;

/**
 * Created by jelle on 18.02.16.
 */
public class Provider {
    private String naam;
    private boolean is_active;

    public Provider(String naam, boolean is_active) {
        this.naam = naam;
        this.is_active = is_active;
    }

    public String to_string()
    {
        return "De provider heet "+this.naam+" en is"+(this.is_active?"":"niet")+" actief";
    }
}
