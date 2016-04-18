package be.ugent.tiwi.scraper;

import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Traject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jelle on 18.02.16.
 *
 * Updated by Jan
 */
public class WazeScraper extends TrafficScraper {
    private static final Logger logger = LogManager.getLogger(WazeScraper.class);
    @Override
    public List<Meting> scrape(List<Traject> trajects) {
        //Hier komt de scraper specifiek voor Waze
        // Start selenium first
        // cmd>java -jar selenium-server-standalone-2.52.0.jar -role hub
        // cmd>java -jar selenium-server-standalone-2.52.0.jar -role node  -hub http://localhost:4444/grid/register
        ProcessBuilder process = new ProcessBuilder("perl", "Selenium.pl");
        process.directory(new File(System.getProperty("user.dir") + "/perl"));
        try {
            Process p = process.start();
            p.waitFor();
        } catch (IOException e) {
            logger.error(e);
            logger.warn("Script not found");
        } catch (InterruptedException e) {
            logger.error(e);
            logger.warn("Script returned error");
        }
        return null;
    }
}
