package be.ugent.tiwi.dal;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by brent on 1/03/2016.
 */
public class DatabaseController {
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);

    private ProviderRepository providerRepository = new ProviderRepository();
    private TrajectRepository trajectenRepository = new TrajectRepository();
    private MetingRepository metingRepository = new MetingRepository();

    public Provider haalProviderOp(int id) {
        return providerRepository.getProvider(id);
    }

    public Provider haalProviderOp(String naam) {
        return providerRepository.getProvider(naam);
    }

    public List<Traject> haalTrajectenOp() {
        return trajectenRepository.getTrajectenMetCoordinaten();
    }

    public void voegMetingToe(Meting meting) {
        metingRepository.addMeting(meting);
    }

    public void voegMetingenToe(List<Meting> metingenLijst) {
        for (Meting meting : metingenLijst) {
            metingRepository.addMeting(meting);
        }
        logger.trace("Added " + metingenLijst.size() + " measurements!");

    }

    public Traject haalTraject(int id, boolean omgekeerd)
    {
        return trajectenRepository.getTraject(id, omgekeerd);
    }

    public void wijzigTraject(Traject traject, Traject trajectOmgekeerd)
    {
        trajectenRepository.wijzigTraject(
                traject.getId(),
                traject.getNaam(),
                trajectOmgekeerd.getNaam(),
                traject.getVan(),
                traject.getNaar(),
                traject.getLengte(),
                trajectOmgekeerd.getLengte(),
                traject.getOptimale_reistijd(),
                trajectOmgekeerd.getOptimale_reistijd(),
                traject.is_active()
        );
    }

    public List<Traject> getTrajectenMetCoordinaten() {
        return trajectenRepository.getTrajectenMetCoordinaten();
    }

    public List<Provider> haalActieveProvidersOp() {
        return providerRepository.getActieveProviders();
    }

    public Traject haalTrajectOp(String naam) {
        return trajectenRepository.getTraject(naam);
    }
}
