package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
    private static final Logger logger = LogManager.getLogger(DatabaseController.class);

    private ProviderRepository providerRepository = new ProviderRepository();
    private TrajectRepository trajectenRepository = new TrajectRepository();
    private MetingRepository metingRepository = new MetingRepository();

    /**
     * Haalt een specifieke provider uit de databank aan de hand van de meegegeven id.
     * @param id Id van de op te halen provider.
     * @return De provider indien deze bestaat, zoniet wordt null geretourneerd.
     */
    public Provider haalProviderOp(int id) {
        return providerRepository.getProvider(id);
    }

    /**
     * Haalt een specifieke provider uit de databank aan de hand van de meegegeven naam.
     * @param naam Naam van de op te halen provider.
     * @return De provider indien deze bestaat, zoniet wordt null geretourneerd.
     */
    public Provider haalProviderOp(String naam) {
        return providerRepository.getProvider(naam);
    }

    /**
     * Haalt alle trajecten op uit de databank.
     * @return Een lijst van trajecten. Indien geen trajecten gevonden werden wordt een lege lijst terug gegeven.
     */
    public List<Traject> haalTrajectenOp() {
        return trajectenRepository.getTrajectenMetCoordinaten();
    }

    /**
     * Schrijft een nieuwe meting weg naar de databank.
     * @param meting De weg te schrijven meting.
     */
    public void voegMetingToe(Meting meting) {
        metingRepository.addMeting(meting);
    }

    /**
     * Schrijft een lijst van metingen weg naar de databank.
     * @param metingenLijst Lijst met de weg te Schrijven metingen.
     */
    public void voegMetingenToe(List<Meting> metingenLijst) {
        List<Integer> missing = new ArrayList<>();
        for (Meting meting : metingenLijst) {
            metingRepository.addMeting(meting);
            if(meting.getReistijd() < 0)
                missing.add(meting.getTraject().getId());
        }
        logger.info("Added " + (metingenLijst.size() - missing.size()) + " good measurements!");
        if(missing.size() > 0){
            StringBuilder b = new StringBuilder();
            b.append("Added ").append(missing.size()).append(" empty measurements - trajects: [").append(missing.get(0));

            for(int i = 1; i < missing.size(); ++i)
                b.append(",").append(missing.get(i));
            b.append("]");
            logger.warn(b.toString());
        }

    }

    /**
     * Haalt een specifieke traject uit de databank aan de hand van de meegegeven id. De bijhorende waypoints worden niet opgehaald.
     * @param id Id van het op te halen traject.
     * @return Het traject indien deze bestaat, zoniet wordt null geretourneerd.
     */
    public Traject haalTraject(int id)
    {
        return trajectenRepository.getTraject(id);
    }

    /**
     * Haalt een specifieke traject uit de databank aan de hand van de meegegeven id. De bijhorende waypoints worden wel opgehaald.
     * @param id Id van het op te halen traject.
     * @return Het traject indien deze bestaat, zoniet wordt null geretourneerd.
     */
    public Traject haalTrajectMetWaypoints(int id)
    {
        return trajectenRepository.getTrajectMetWaypoints(id);
    }

    /**
     * Schrijft de wijzigingen van een bestaand traject weg naar de databank.
     * @param traject Het aangepaste traject dat moet worden geupdated
     */
    public void wijzigTraject(Traject traject)
    {
        trajectenRepository.wijzigTraject(
                traject.getId(),
                traject.getNaam(),
                traject.getLengte(),
                traject.getOptimale_reistijd(),
                traject.is_active(),
                traject.getStart_latitude(),
                traject.getStart_longitude(),
                traject.getEnd_latitude(),
                traject.getEnd_longitude(),
                traject.getWaypoints()
        );
    }

    /**
     * Haalt een lijst op van alle trajecten met bijhorende waypoints.
     * @return Lijst van Trajecten waarbij de waypoints ook ingevuld zijn.
     */
    public List<Traject> getTrajectenMetWaypoints() {
        return trajectenRepository.getTrajectenMetCoordinaten();
    }

    /**
     * Haalt alle providers op die momentaal als actief gemarkeerd zijn.
     * @return Lijst met de providers die actief zijn.
     */
    public List<Provider> haalActieveProvidersOp() {
        return providerRepository.getActieveProviders();
    }

    /**
     * Haalt een specifieke traject uit de databank aan de hand van de meegegeven naam. De bijhorende waypoints worden niet opgehaald.
     * @param naam Naam van het op te halen traject.
     * @return Het traject indien deze bestaat, zoniet wordt null geretourneerd.
     */
    public Traject haalTrajectOp(String naam) {
        return trajectenRepository.getTraject(naam);
    }
}
