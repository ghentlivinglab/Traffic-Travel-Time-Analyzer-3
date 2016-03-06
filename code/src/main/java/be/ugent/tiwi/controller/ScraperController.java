package be.ugent.tiwi.controller;

import be.ugent.tiwi.scraper.TrafficScraper;

import java.util.List;

/**
 * Created by jelle on 18.02.16.
 */
public class ScraperController {
    private List<TrafficScraper> scrapers;

    public ScraperController() {
        //Hier worden de reeds geïmplementeerde scrapers geïnitialiseerd met de juiste parameters (vb. API key enz...)
    }

    public void start() {
        //Met deze methode moet het scrape proces in gang gezet kunnen worden
    }
}
