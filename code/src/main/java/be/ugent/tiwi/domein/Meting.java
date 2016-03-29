package be.ugent.tiwi.domein;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by JelleDeBock on 23/02/16.
 */
public class Meting {
    private Provider provider;
    private Traject traject;
    private int reistijd;
    private LocalDateTime timestamp;

    /**
     * Constructor van de klasse
     */
    public Meting() {}

    public Meting(Provider provider, Traject traject, int reistijd, LocalDateTime timestamp) {
        this.provider = provider;
        this.traject = traject;
        this.reistijd = reistijd;
        this.timestamp = timestamp;
    }

    /**
     * Geeft het tijdstip terug wanneer de meting gedaan is
     * @return De tijd van de meting
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Wijzig de tijd van de meting
     * @param timestamp De nieuwe tijd
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Geeft de provider van meting terug
     * @return Een provider
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Geeft het traject van de meting terug
     * @return Een traject
     */
    public Traject getTraject() {
        return traject;
    }

    /**
     * Geeft de gemeten reistijd terug
     * @return De reistijd van de meting
     */
    public int getReistijd() {
        return reistijd;
    }

    /**
     * Wijzig de provider van de meting
     * @param provider De nieuwe provider
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * Wijzig het traject van de meting
     * @param traject Het nieuwe traject
     */
    public void setTraject(Traject traject) {
        this.traject = traject;
    }

    /**
     * Wijzig de gemeten reistijd van de meting
     * @param reistijd De nieuwe reistijd
     */
    public void setReistijd(int reistijd) {
        this.reistijd = reistijd;
    }

    /**
     * Geeft een kort overzicht van de meting terug
     * @return Een kort overzicht
     */
    @Override
    public String toString() {
        return "{" +
            "provider=" + provider.toString() +
            ", traject=" + traject.toString() +
            ", reistijd=" + reistijd +
            ", timestamp=" + timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
            '}';
    }
}
