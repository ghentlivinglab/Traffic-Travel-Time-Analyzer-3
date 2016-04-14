package be.ugent.tiwi.domein;

/**
 * Created by jelle on 29.03.16.
 */
public class ProviderTrajectStatistiek {
    private Provider provider;
    private Vertraging vertraging;

    public ProviderTrajectStatistiek(Provider provider, Vertraging vertraging) {
        this.provider = provider;
        this.vertraging = vertraging;
    }

    @Override
    public String toString() {
        return "ProviderTrajectStatistiek{" +
                "provider=" + provider +
                ", vertraging=" + vertraging +
                '}';
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Vertraging getVertraging() {
        return vertraging;
    }

    public void setVertraging(Vertraging vertraging) {
        this.vertraging = vertraging;
    }
}
