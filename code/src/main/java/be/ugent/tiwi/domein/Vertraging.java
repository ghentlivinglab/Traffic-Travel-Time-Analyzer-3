package be.ugent.tiwi.domein;

public class Vertraging{
    Traject traject;
    double avg_vertraging;

    public Vertraging(Traject traject,double vertraging) {
        this.avg_vertraging = vertraging;
        this.traject = traject;
    }

    @Override
    public String toString() {
        return "Vertraging{" +
                "traject=" + traject +
                ", avg_vertraging=" + avg_vertraging +
                '}';
    }

    public Traject getTraject() {
        return traject;
    }

    public void setTraject(Traject traject) {
        this.traject = traject;
    }

    public double getAverageVertraging() {
        return avg_vertraging;
    }

    public void setAverageVertraging(double vertraging) {
        this.avg_vertraging = vertraging;
    }
}