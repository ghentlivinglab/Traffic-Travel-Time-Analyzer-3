package be.ugent.tiwi.domein;

import java.time.LocalDateTime;

/**
 * Created by JelleDeBock on 23/02/16.
 */
public class Meting {
    private Provider provider;
    private Traject traject;
    private int reistijd;
    private int optimale_reistijd;
    private LocalDateTime timestamp;

    public Meting(Provider provider, Traject traject, int reistijd, int optimale_reistijd, LocalDateTime timestamp) {
        this.provider = provider;
        this.traject = traject;
        this.reistijd = reistijd;
        this.optimale_reistijd = optimale_reistijd;
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Provider getProvider() {
        return provider;
    }

    public Traject getTraject() {
        return traject;
    }

    public int getReistijd() {
        return reistijd;
    }

    public int getOptimale_reistijd() {
        return optimale_reistijd;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void setTraject(Traject traject) {
        this.traject = traject;
    }

    public void setReistijd(int reistijd) {
        this.reistijd = reistijd;
    }

    public void setOptimale_reistijd(int optimale_reistijd) {
        this.optimale_reistijd = optimale_reistijd;
    }

    @Override
    public String toString() {
        return "Meting{" +
                "provider=" + provider +
                ", traject=" + traject +
                ", reistijd=" + reistijd +
                ", optimale_reistijd=" + optimale_reistijd +
                ", timestamp=" + timestamp +
                '}';
    }
}
