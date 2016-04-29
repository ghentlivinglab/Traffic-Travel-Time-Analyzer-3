package be.ugent.tiwi.domein;

import java.time.LocalDateTime;

/**
 * Created by Jeroen on 29/04/2016.
 */
public class TrafficIncident {
    private Provider provider;
    private Traject traject;
    private LocalDateTime timestamp;
    private String problem = "";

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Traject getTraject() {
        return traject;
    }

    public void setTraject(Traject traject) {
        this.traject = traject;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "TrafficIncident{" +
                "provider=" + provider +
                ", traject=" + traject +
                ", timestamp=" + timestamp +
                ", problem='" + problem + '\'' +
                '}';
    }
}
