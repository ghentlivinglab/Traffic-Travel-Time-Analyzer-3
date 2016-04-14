package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by brent on 14/04/2016.
 */
public interface IMetingRepository {
    /**
     * Deze methode geeft alle metingen terug gemaakt door een provider van een traject.
     *
     * @param provider De provider waarvan je de metingen wil opvragen
     * @param traject  Het traject waarvan je de metingen wil opvragen
     * @return Een lijst van metingen
     * @throws SQLException Indien de databank niet beschikbaar is of als de query niet geldig is
     * @see Meting
     */
    List<Meting> getMetingen(Provider provider, Traject traject) throws SQLException;

    /**
     * Geeft alle metingen terug die in de database aanwezig zijn.
     *
     * @return Een lijst van metingen.
     * @see Meting
     */
    List<Meting> getMetingen();

    /**
     * Geeft alle metingen van een meegegeven traject terug.
     *
     * @param traject_id Het ID van het traject
     * @return Een lijst van metingen
     * @see Meting
     */
    List<Meting> getMetingenFromTraject(int traject_id);

    /**
     * Geeft alle metingen van een meegegeven traject terug binnen een tijdspanne
     *
     * @param traject_id Het ID van je traject
     * @param start      De begintijd van de tijdspanne
     * @param end        De eindtijd van de tijdspanne
     * @return Een lijst van metingen
     * @see Meting
     */
    List<Meting> getMetingenFromTraject(int traject_id, LocalDateTime start, LocalDateTime end);

    /**
     * Geeft een lijst terug van providers. Iedere provider bevat een volledig ingevulde lijst van metingen van het
     * meegegeven traject
     *
     * @param traject_id Een traject_id
     * @return Een lijst van providers, aangevuld met een lijst metingen per provider.
     * @see Meting
     */
    List<Provider> getMetingenFromTrajectByProvider(int traject_id);

    /**
     * Geeft alle metingen van 1 provider terug
     *
     * @param id Het ID van de provider
     * @return Een lijst van metingen
     * @see Meting
     */
    List<Meting> getMetingenByProvider(int id);

    List<Meting> getLaatsteMetingenByProvider(int id);

    /**
     * Geeft alle providers terug waarvoor al minstens 1 keer gescraped is
     *
     * @return Een lijst van providers
     * @see Provider
     */
    List<Provider> getGebruikteProviders();

    /**
     * Voegt 1 meting toe aan de database
     *
     * @param meting De meting die toegevoegd moet worden
     * @see Meting
     */
    void addMeting(Meting meting);

    /**
     * Deze functie berekent de gemiddelde vertraging voor een bepaald traject voor een bepaalde provider.
     *
     * @param traject_id het id van het traject (zoals is databank opgeslaan)
     * @param provider_id het id van de provider (zoals is databank opgeslaan)
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return Het object dat geretourneerd wordt is van de klasse @class ProviderTrajectStatistiek. Per metingstatistiek oproep wordt slechts 1 object
     * geretourneerd. Het @class ProviderTrajectStatistiek object bevat de gemiddelde vertraging, het traject (als object) en de provider (als object).
     */
    ProviderTrajectStatistiek metingStatistieken(int traject_id, int provider_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip);

    int getOptimaleReistijdLaatste7Dagen(int trajectId, int providerId);

    /**
     * Berekent de gemiddelde vertraging in Gent. Het geeft dus een vertraging voor alle trajecten rekening houdende met alle providers.
     * Wordt berekend door:
     *  1: Berekent de gemiddelde, algemene reistijd voor de periode (rekening houdend met frequenties en null-values).
     *  2: Berekent de gemiddelde, algemene optimale reistijd (houdt ook rekening met de frequenties van de metingen en null-values).
     *  3: De gemiddelde vertraging bedraagt verschil van deze twee gemiddelden.
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return de waarde van de gemiddelde vertraging (als double)
     */
    double gemiddeldeVertraging(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip);

    /**
     * Berekent de gemiddelde vertraging in Gent voor een bepaalde provider. Het geeft dus een vertraging voor alle trajecten rekening houdende de gewenste provider.
     *
     * @param provider de provider waarvoor we de gemiddelde vertraging wensen te berekenen
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return de waarde van de gemiddelde vertraging (als double)
     */
    double gemiddeldeVertraging(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip);

    /**
     * Deze functie berekent de gemiddelde reistijdvertraging van alle trajecten voor een specifieke provider.
     *
     * @param provider_id de provider waarvan de gemiddelde reistijd bepaald moet worden (id uit databank)
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return Deze functie retourneert 1 enkel object van het type ProviderStatistiek. Het bevat de gemiddelde vertraging alsook de provider in kwestie
     * (als object)
     */
    ProviderStatistiek metingProviderStatistieken(int provider_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip);

    /**
     * Deze functie berekent voor een gewenst traject de gemiddelde vertraging (over alle providers uitgemiddeld)
     *
     * @param traject_id het traject waarvoor de gemiddelde vertraging bepaald dient te worden
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return Deze functie retourneert 1 enkel object van het type Vertraging deze gevat de gemiddelde vertraging en het traject (als object)
     */
    Vertraging metingTrajectStatistieken(int traject_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip);

    /**
     * Zoekt het drukste traject voor een bepaalde tijdsinterval, geeft een Vertraging object terug
     *
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moet worden
     * @param end_tijdstip   het eind tijdstip tot waar gezocht moet worden
     * @return drukste tijdstip
     */
    Vertraging getDrukstePunt(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip);

    /**
     * Berekent het drukste punt, maar dan voor de meting van een specifieke provider
     *
     * @param provider De provider waarvan het drukste punt gewenst is
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moeten
     * @param end_tijdstip het eind tijdstip tot waar gezocht moeten
     *
     * @return het drukste punt voor die provider
     */
    Vertraging getDrukstePunt(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip);

    /**
     * Genereert een handig overzicht van alle trajecten, met de bijhorende gemiddelde vertragingen over het meegegeven tijdsinterval.
     *
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moeten
     * @param end_tijdstip   het eind tijdstip tot waar gezocht moeten
     * @return alle trajecten die een vertraging hebben over dat tijdstip
     */
    List<Vertraging> getVertragingen(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip);

    /**
     * Genereert een handig overzicht van alle trajecten, met de bijhorende gemiddelde vertragingen over het meegegeven tijdsinterval.
     * Deze functie doet dit voor 1 bepaalde provider.
     *
     * @param provider de provider waarvoor we de gemiddelde vertragingen wensen te berekenen
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moeten
     * @param end_tijdstip het eind tijdstip tot waar gezocht moeten
     *
     * @return alle trajecten die een vertraging hebben over dat tijdstip
     */
    List<Vertraging> getVertragingen(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip);
}
