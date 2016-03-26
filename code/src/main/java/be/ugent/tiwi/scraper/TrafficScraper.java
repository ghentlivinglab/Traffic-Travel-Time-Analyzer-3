package be.ugent.tiwi.scraper;

import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Traject;

import java.util.List;

/**
 * Interface waaraan alle scrapers moeten voldoen
 */
public interface TrafficScraper {

    /**
     * Aan de spreken methode om een bepaalde provider te scrapen
     * @param trajects Een lijst van trajecten waarvan een meting moet woren opgehaald
     * @return Een lijst van opgehaalde metingen.
     */
    List<Meting> scrape(List<Traject> trajects);
}
