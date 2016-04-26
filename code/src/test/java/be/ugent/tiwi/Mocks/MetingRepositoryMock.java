package be.ugent.tiwi.Mocks;

import be.ugent.tiwi.dal.IMetingRepository;
import be.ugent.tiwi.domein.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brent on 14/04/2016.
 */
public class MetingRepositoryMock implements IMetingRepository {
    private List<Meting> metingen = new ArrayList<>();

    @Override
    public List<Meting> getMetingen(Provider provider, Traject traject) throws SQLException {
        List<Meting> outputMetingen = new ArrayList<>();
        for (Meting m:metingen) {
            if(m.getProvider().getId()==provider.getId()&&m.getTraject().getId()==traject.getId()){
                outputMetingen.add(m);
            }
        }
        return outputMetingen;
    }

    @Override
    public List<Meting> getMetingen(int provider_id, int traject_id) {
        List<Meting> outputMetingen = new ArrayList<>();
        for (Meting m:metingen) {
            if(m.getProvider().getId()==provider_id &&m.getTraject().getId()==traject_id){
                outputMetingen.add(m);
            }
        }
        return outputMetingen;
    }

    @Override
    public List<Meting> getMetingenFromTraject(int traject_id) {
        List<Meting> outputMetingen = new ArrayList<>();
        for (Meting m:metingen) {
            if(m.getTraject().getId()==traject_id){
                outputMetingen.add(m);
            }
        }
        return outputMetingen;
    }

    @Override
    public List<Meting> getMetingenFromTraject(int traject_id, LocalDateTime start, LocalDateTime end) {
        List<Meting> outputMetingen = new ArrayList<>();
        for (Meting m:metingen) {
            if(m.getTraject().getId()==traject_id && m.getTimestamp().isBefore(end) && m.getTimestamp().isAfter(start)){
                outputMetingen.add(m);
            }
        }
        return outputMetingen;
}

    @Override
    public List<Provider> getMetingenFromTrajectByProvider(int traject_id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Meting> getMetingenByProvider(int id) {
        List<Meting> outputMetingen = new ArrayList<>();
        for (Meting m:metingen) {
            if(m.getProvider().getId()==id ){
                outputMetingen.add(m);
            }
        }
        return outputMetingen;
    }

    @Override
    public List<Meting> getLaatsteMetingenByProvider(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Provider> getGebruikteProviders() {
        throw new NotImplementedException();
    }

    @Override
    public void addMeting(Meting meting) {
        metingen.add(meting);
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
    public void addMetingen(List<Meting> mtg) {
        metingen.addAll(mtg);
    }
}
