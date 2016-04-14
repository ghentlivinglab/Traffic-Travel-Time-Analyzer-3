package be.ugent.tiwi.Mocks;

import be.ugent.tiwi.dal.IProviderRepository;
import be.ugent.tiwi.domein.Provider;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by brent on 14/04/2016.
 */
public class ProviderRepositoryMock implements IProviderRepository{
    private List<Provider> providers = new ArrayList<>();
    public ProviderRepositoryMock(){
        providers.add(new Provider(1,"Provider 1",false));
        providers.add(new Provider(2,"Provider 2",true));
        providers.add(new Provider(3,"Provider 3",false));
        providers.add(new Provider(4,"Provider 4",true));
    }

    @Override
    public Provider getProvider(String naam) {
        for (Provider p:providers) {
            if(p.getNaam().equals(naam)){
                return p;
            }
        }
        return null;
    }

    @Override
    public Provider getProvider(int id) {
        for (Provider p:providers) {
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Provider> getActieveProviders() {
        List<Provider> actieveProviders = new ArrayList<>();
        for (Provider p:providers) {
            if(p.is_active()){
                actieveProviders.add(p);
            }
        }
        return actieveProviders;
    }

    @Override
    public List<Provider> getProviders() {
        return providers;
    }

    @Override
    public void setOptimaleReistijden(int provider_id, Map<Integer, Integer> optimaleReistijden) {
        throw new NotImplementedException();
    }
}
