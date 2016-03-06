package be.ugent.tiwi.scraper;

import be.ugent.tiwi.domein.Meting;

import java.util.List;

/**
 * Created by jelle on 18.02.16.
 */
abstract public class TrafficScraper
{
    public abstract List<Meting> scrape();
}
