package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;

import java.util.List;

/**
 * Created by brent on 1/03/2016.
 */
public class DatabaseController {
    private ProviderCRUD providerRepository = new ProviderCRUD();
    private TrajectCRUD trajectenRepository = new TrajectCRUD();


    public Provider haalProviderOp(int id){
        return providerRepository.getProvider(id);
    }
    public Provider haalProviderOp(String naam){
        return providerRepository.getProvider(naam);
    }

    public List<Traject> haalTrajectenOp(){
        return trajectenRepository.getTrajectenMetCoordinaten();
    }
}
