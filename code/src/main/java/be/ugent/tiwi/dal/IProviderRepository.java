package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Provider;

import java.util.List;
import java.util.Map;

/**
 * Created by brent on 14/04/2016.
 */
public interface IProviderRepository {
    /**
     * Haalt de provider op met ene bepaalde naam
     * @param naam De naam van de op te halen provider
     * @return De provider met de gevraagde naam
     */
    Provider getProvider(String naam);

    /**
     * Haalt de provider op met ene bepaalde identificatie
     * @param id De identificatie van de op te halen provider
     * @return De provider met de identificatie naam
     */
    Provider getProvider(int id);

    /**
     * Haalt alle actieve providers op. Bij deze providers staat de flag isActive op 1.
     * @return Een lijst met de actieve providers
     */
    List<Provider> getActieveProviders();

    List<Provider> getProviders();

    void setOptimaleReistijden(int provider_id, Map<Integer, Integer> optimaleReistijden);
}
