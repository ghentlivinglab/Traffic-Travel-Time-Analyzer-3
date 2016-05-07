package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.TrafficIncident;
import be.ugent.tiwi.domein.Traject;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Incidenten van en naar de database halen / schrijven
 *
 * Created by Jeroen on 19/04/2016.
 */
public interface IIncidentRepository {

    /**
     * Geeft een lijst van alle providers terug die trafficincidents gegenereerd hebben
     *
     * @return list providers
     */
    List<Provider> getProviders();

    /**
     * Geeft een lijst van alle providers terug die trafficincidents gegenereerd hebben tussen bepaalde data
     * @param startdate
     * @param enddate
     * @return
     */
    List<Provider> getProviders(LocalDateTime startdate, LocalDateTime enddate);


    /**
     * Geeft een lijst terug van alle trajecten waar er ooit problemen geweest zijn
     *
     * @return
     */
    List<Traject> getTrajecten();


    /**
     * Geeft een lijst terug van alle trajecten waar er problemen geweest zijn tussen bepaalde data
     * @param startdate
     * @param enddate
     * @return
     */
    List<Traject> getTrajecten(LocalDateTime startdate, LocalDateTime enddate);

    /**
     * Geeft een lijst terug van alle trafficincidenten
     *
     * @return
     */
    List<TrafficIncident> getTrafficIncidents();

    /**
     * Geeft een lijst terug van alle trafficincidenten tussen bepaalde data
     *
     * @param startdate
     * @param enddate
     * @return
     */
    List<TrafficIncident> getTrafficIncidents(LocalDateTime startdate, LocalDateTime enddate);

    /**
     * Geeft een lijst terug van alle trafficincidenten tussen bepaalde data van een bepaalde provider
     *
     * @param startdate
     * @param enddate
     * @return
     */
    List<TrafficIncident> getTrafficIncidents(Provider provider, LocalDateTime startdate, LocalDateTime enddate);

    /**
     * Geeft een lijst terug van alle trafficincidenten tussen bepaalde data van een bepaald traject
     *
     * @param startdate
     * @param enddate
     * @return
     */
    List<TrafficIncident> getTrafficIncidents(Traject traject, LocalDateTime startdate, LocalDateTime enddate);


    /**
     * Geeft een lijst terug van alle trafficincidenten tussen bepaalde data van een bepaalde provider van een bepaald traject
     *
     * @param startdate
     * @param enddate
     * @return
     */
    List<TrafficIncident> getTrafficIncidents(Provider provider, Traject traject, LocalDateTime startdate, LocalDateTime enddate);


    /**
     * Lijst van alle provider teruggeven die problemen over verkeersinfo hebben
     * @return
     */
    public List<Provider> getTrafficIncidentsProviders();


    /**
     * Lijst van alle trajecten teruggeven die problemen hebben
     * @return
     */
    public List<Traject> getTrafficIncidentsTrajecten();


    /**
     * Toevoegen van een nieuw verkeersprobleem
     *
     * @param trafficIncident
     */
    void addTrafficIncident(TrafficIncident trafficIncident);


    /**
     * Een bestaand verkeersprobleem ophalen
     * @param trafficIncident
     * @return
     */
    public TrafficIncident getTrafficIncident(TrafficIncident trafficIncident);

    void updateTrafficIncident(TrafficIncident trafficIncident);
}
