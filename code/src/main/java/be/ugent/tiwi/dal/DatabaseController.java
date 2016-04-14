package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseController {
    private static final Logger logger = LogManager.getLogger(DatabaseController.class);

    private ProviderRepository providerRepository = new ProviderRepository();
    private TrajectRepository trajectenRepository = new TrajectRepository();
    private MetingRepository metingRepository = new MetingRepository();

    /**
     * Haalt een specifieke {@link Provider} uit de databank aan de hand van de meegegeven id.
     *
     * @param id Id van de op te halen {@link Provider}.
     * @return De {@link Provider} indien deze bestaat, zoniet wordt <code>null</code> geretourneerd.
     */
    public Provider haalProviderOp(int id) {
        return providerRepository.getProvider(id);
    }

    /**
     * Haalt een specifieke {@link Provider} uit de databank aan de hand van de meegegeven naam.
     *
     * @param naam Naam van de op te halen {@link Provider}.
     * @return De {@link Provider} indien deze bestaat, zoniet wordt <code>null</code> geretourneerd.
     */
    public Provider haalProviderOp(String naam) {
        return providerRepository.getProvider(naam);
    }

    /**
     * Haalt alle {@link Traject}en op uit de databank.
     *
     * @return Een lijst van {@link Traject}en. Indien geen {@link Traject}en gevonden werden wordt een lege lijst terug gegeven.
     */
    public List<Traject> haalTrajectenOp() {
        return trajectenRepository.getTrajectenMetCoordinaten();
    }

    /**
     * Schrijft een nieuwe {@link Meting} weg naar de databank.
     *
     * @param meting De weg te schrijven {@link Meting}.
     */
    public void voegMetingToe(Meting meting) {
        metingRepository.addMeting(meting);
    }

    /**
     * Schrijft een lijst van {@link Meting}en weg naar de databank.
     *
     * @param metingenLijst Een lijst met de weg te schrijven {@link Meting}en.
     */
    public void voegMetingenToe(List<Meting> metingenLijst) {
        List<Integer> missing = new ArrayList<>();
        for (Meting meting : metingenLijst) {
            metingRepository.addMeting(meting);
            if (meting.getReistijd() == null || meting.getReistijd() < 0)
                missing.add(meting.getTraject().getId());
        }
        logger.info("Added " + (metingenLijst.size() - missing.size()) + " good measurements!");
        if (missing.size() > 0) {
            StringBuilder b = new StringBuilder();
            b.append("Added ").append(missing.size()).append(" empty measurements - trajects: [").append(missing.get(0));

            for (int i = 1; i < missing.size(); ++i)
                b.append(",").append(missing.get(i));
            b.append("]");
            logger.warn(b.toString());
        }

    }

    /**
     * Haalt een specifieke {@link Traject} uit de databank aan de hand van de meegegeven id. De bijhorende {@link Waypoint}s worden niet opgehaald.
     *
     * @param id Id van het op te halen {@link Traject}.
     * @return Het {@link Traject} indien deze bestaat, zoniet wordt <code>null</code> geretourneerd.
     */
    public Traject haalTraject(int id) {
        return trajectenRepository.getTraject(id);
    }

    /**
     * Haalt een specifieke {@link Traject} uit de databank aan de hand van de meegegeven id. De bijhorende {@link Waypoint}s worden wel opgehaald.
     *
     * @param id Id van het op te halen traject.
     * @return Het {@link Traject} indien deze bestaat, zoniet wordt <code>null</code> geretourneerd.
     * @see Waypoint
     */
    public Traject haalTrajectMetWaypoints(int id) {
        return trajectenRepository.getTrajectMetWaypoints(id);
    }

    /**
     * Schrijft de wijzigingen van een bestaand {@link Traject} weg naar de databank.
     *
     * @param traject Het aangepaste {@link Traject} dat moet worden geupdated
     */
    public void wijzigTraject(Traject traject) {
        trajectenRepository.wijzigTraject(
                traject.getId(),
                traject.getNaam(),
                traject.getLengte(),
                traject.getGlobaleOptimale_reistijd(),
                traject.getOptimaleReistijden(),
                traject.is_active(),
                traject.getStart_latitude(),
                traject.getStart_longitude(),
                traject.getEnd_latitude(),
                traject.getEnd_longitude(),
                traject.getWaypoints()
        );
    }

    /**
     * Haalt een lijst op van alle {@link Traject}en met bijhorende {@link Waypoint}s.
     *
     * @return Lijst van {@link Traject}en waarbij de {@link Waypoint}s ook ingevuld zijn.
     * @see Waypoint
     */
    public List<Traject> getTrajectenMetWaypoints() {
        return trajectenRepository.getTrajectenMetCoordinaten();
    }

    /**
     * Haalt alle {@link Provider}s op die momentaal als actief gemarkeerd zijn.
     *
     * @return Lijst met de {@link Provider}s die actief zijn.
     */
    public List<Provider> haalActieveProvidersOp() {
        return providerRepository.getActieveProviders();
    }

    /**
     * Haalt een specifieke {@link Traject} uit de databank aan de hand van de meegegeven naam. De bijhorende {@link Waypoint}s worden niet opgehaald.
     *
     * @param naam Naam van het op te halen {@link Traject}.
     * @return Het {@link Traject} indien deze bestaat, zoniet wordt <code>null</code> geretourneerd.
     */
    public Traject haalTrajectOp(String naam) {
        return trajectenRepository.getTraject(naam);
    }

    public List<Traject> getTrajectenMetWaypoints(int providerId) {
        return trajectenRepository.getTrajectenMetCoordinaten(providerId);
    }

    public List<Provider> haalAlleProvidersOp() {
        return providerRepository.getProviders();
    }

    public void setOptimaleReistijdenPerProvider(int providerId, Map<Integer, Integer> reistijdenPerProvider){
        providerRepository.setOptimaleReistijden(providerId, reistijdenPerProvider);
    }

}
