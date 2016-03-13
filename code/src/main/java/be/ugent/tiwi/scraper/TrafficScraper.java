package be.ugent.tiwi.scraper;

import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Traject;

import java.util.List;

/**
 * Created by jelle on 18.02.16.
 */
public interface TrafficScraper {
    public List<Meting> scrape(List<Traject> trajects);
}
