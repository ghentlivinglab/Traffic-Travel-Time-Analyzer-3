package be.ugent.tiwi.Mocks;

import be.ugent.tiwi.dal.IMetingRepository;
import be.ugent.tiwi.domein.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by brent on 14/04/2016.
 */
public class MetingRepositoryMock implements IMetingRepository {
    @Override
    public List<Meting> getMetingen(Provider provider, Traject traject) throws SQLException {
        return null;
    }

    @Override
    public List<Meting> getMetingen(int provider_id, int traject_id) {
        return null;
    }

    @Override
    public List<Meting> getMetingenFromTraject(int traject_id) {
        return null;
    }

    @Override
    public List<Meting> getMetingenFromTraject(int traject_id, LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<Provider> getMetingenFromTrajectByProvider(int traject_id) {
        return null;
    }

    @Override
    public List<Meting> getMetingenByProvider(int id) {
        return null;
    }

    @Override
    public List<Meting> getLaatsteMetingenByProvider(int id) {
        return null;
    }

    @Override
    public List<Provider> getGebruikteProviders() {
        return null;
    }

    @Override
    public void addMeting(Meting meting) {

    }

    @Override
    public ProviderTrajectStatistiek metingStatistieken(int traject_id, int provider_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        return null;
    }

    @Override
    public int getOptimaleReistijdLaatste7Dagen(int trajectId, int providerId) {
        return 0;
    }

    @Override
    public double gemiddeldeVertraging(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        return 0;
    }

    @Override
    public double gemiddeldeVertraging(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        return 0;
    }

    @Override
    public ProviderStatistiek metingProviderStatistieken(int provider_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        return null;
    }

    @Override
    public Vertraging metingTrajectStatistieken(int traject_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        return null;
    }

    @Override
    public Vertraging getDrukstePunt(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        return null;
    }

    @Override
    public Vertraging getDrukstePunt(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        return null;
    }

    @Override
    public List<Vertraging> getVertragingen(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        return null;
    }

    @Override
    public List<Vertraging> getVertragingen(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        return null;
    }

    @Override
    public void addMetingen(List<Meting> metingen) { }
}
