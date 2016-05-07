package be.ugent.tiwi.domein;

import java.time.LocalDateTime;

/**
 * Created by Jeroen on 29/04/2016.
 */
public class TrafficIncident {
    private int id;
    private Provider provider;
    private Traject traject;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String problem = "";

    public TrafficIncident(){

    }

    public TrafficIncident(int id, Provider provider, Traject traject, LocalDateTime startTime, LocalDateTime endTime, String problem) {
        this.id = id;
        this.provider = provider;
        this.traject = traject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.problem = problem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Geeft de provider terug vanwaar de data afkomstig is
     * @return
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Instellen van de provider
     * @param provider
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * Geeft het traject terug waar het probleem zich voordoet
     * @return
     */
    public Traject getTraject() {
        return traject;
    }

    /**
     * Het traject instellen
     * @param traject
     */
    public void setTraject(Traject traject) {
        this.traject = traject;
    }

    /**
     * Het detectie tijdstip van het probleem opvragen
     * @return
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Het detectie tijdstip van het probleem instellen
     * @param startTime
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Het laatste detectie tijdstip van het probleem opvragen
     * @return
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Het laatste detectie tijdstip van het probleem instellen
     * @param endTime
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Oorzaak van het probleem opvragen
     * @return
     */
    public String getProblem() {
        return problem;
    }

    /**
     * Oorzaak van het probleem instellen
     * @param problem
     */
    public void setProblem(String problem) {
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "TrafficIncident{" +
                "provider=" + provider +
                ", traject=" + traject +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", problem='" + problem + '\'' +
                '}';
    }
}
