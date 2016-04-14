package be.ugent.tiwi.Mocks;

import be.ugent.tiwi.dal.IProviderRepository;
import be.ugent.tiwi.domein.Provider;

import java.util.List;
import java.util.Map;

/**
 * Created by brent on 14/04/2016.
 */
public class ProviderRepositoryMock implements IProviderRepository{
    @Override
    public Provider getProvider(String naam) {
        return null;
    }

    @Override
    public Provider getProvider(int id) {
        return null;
    }

    @Override
    public List<Provider> getActieveProviders() {
        return null;
    }

    @Override
    public List<Provider> getProviders() {
        return null;
    }

    @Override
    public void setOptimaleReistijden(int provider_id, Map<Integer, Integer> optimaleReistijden) {

    }
}
