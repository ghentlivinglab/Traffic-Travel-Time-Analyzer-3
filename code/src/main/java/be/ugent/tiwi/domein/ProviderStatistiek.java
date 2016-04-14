package be.ugent.tiwi.domein;

import java.util.ArrayList;
import java.util.List;

public class ProviderStatistiek {
    Provider provider;
    private List<Vertraging> vertragingen;

    public ProviderStatistiek(Provider provider) {
        this.provider = provider;
        this.vertragingen = new ArrayList<>();
    }

    public void addVertraging(Vertraging vertraging) {
        this.vertragingen.add(vertraging);
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<Vertraging> getStatistiekList() {
        return vertragingen;
    }

    @Override
    public String toString() {
        return "ProviderStatistiek{" +
                "provider=" + provider +
                ", vertragingen=" + vertragingen.toString() +
                '}';
    }
}
