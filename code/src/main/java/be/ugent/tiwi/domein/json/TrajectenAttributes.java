package be.ugent.tiwi.domein.json;

import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;

import java.util.List;
import java.util.Map;

/**
 * Created by Eigenaar on 26/04/2016.
 */
public class TrajectenAttributes {

    private Traject traject;
    private List<Provider> providers;
    private Map<Integer, Integer> huidigeVertragingen;

    public TrajectenAttributes(Traject traject, List<Provider> providers, Map<Integer, Integer> huidigeVertragingen){
        this.traject = traject;
        this.providers = providers;
        this.huidigeVertragingen = huidigeVertragingen;
    }

    public Traject getTraject() {
        return traject;
    }

    public void setTraject(Traject traject) {
        this.traject = traject;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public Map<Integer, Integer> getHuidigeVertragingen() {
        return huidigeVertragingen;
    }

    public void setHuidigeVertragingen(Map<Integer, Integer> huidigeVertragingen) {
        this.huidigeVertragingen = huidigeVertragingen;
    }
}
