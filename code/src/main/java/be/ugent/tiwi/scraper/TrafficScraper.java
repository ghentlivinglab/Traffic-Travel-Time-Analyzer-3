package be.ugent.tiwi.scraper;

import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;

import java.util.List;

/**
 * Interface waaraan alle scrapers moeten voldoen
 */
public abstract class TrafficScraper {

    /**
     * Aan de spreken methode om een bepaalde provider te scrapen
     * @param trajects Een lijst van trajecten waarvan een meting moet woren opgehaald
     * @return Een lijst van opgehaalde metingen.
     */
    abstract List<Meting> scrape(List<Traject> trajects);

    protected Traject getTraject(List<Traject> trajects, int id){
        Traject t = trajects.get(id - 1);
        if(t.getId() != id) {
            for (Traject traj : trajects)
                if (traj.getId() == id) {
                    t = traj;
                    break;
                }
        }
        return t;
    }

    protected void deleteWaypoint(Traject t, String lat, String lng){
        for(int i = 0; i < t.getWaypoints().size(); ++i){
            Waypoint w = t.getWaypoints().get(i);
            if(w.getLatitude().equals(lat) && w.getLongitude().equals(lng)){
                t.getWaypoints().remove(i);
                break;
            }
        }
    }

    protected void editWaypoint(Traject t, String lat, String lng, String newlat, String newlng){
        for(int i = 0; i < t.getWaypoints().size(); ++i){
            Waypoint w = t.getWaypoints().get(i);
            if(w.getLatitude().equals(lat) && w.getLongitude().equals(lng)){
                w.setLatitude(newlat);
                w.setLongitude(newlng);
                break;
            }

        }
    }
}
