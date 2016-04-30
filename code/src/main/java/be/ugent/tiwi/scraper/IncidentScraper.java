package be.ugent.tiwi.scraper;

import be.ugent.tiwi.domein.TrafficIncident;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;


/**
 * Interface waaraan alle scrapers die verkeersproblemen ophalen moeten voldoen
 * Created by Jeroen on 30.04.16.
 */
public abstract class IncidentScraper {

    /**
     * Ophalen van een verkeersprobleem adhv een bepaald traject
     *
     * @param traject traject waar het probleem zich zou voordoen
     * @return een verkeersprobleem
     */
    abstract TrafficIncident scrape(Traject traject);


    /**
     * Converteert geografische coordinaten naar UTM (Universal Transverse Mercator)
     *
     * @param wp een waypoint
     * @return list met 3 doubles: x(easting), y(northing) en z(zone)
     */
    protected double[] geo2UTM(Waypoint wp) {
        double lattitude = Double.parseDouble(wp.getLatitude());
        double longitude = Double.parseDouble(wp.getLongitude());


        double Easting;
        double Northing;
        int Zone;
        char Letter;

        Zone = (int) Math.floor(longitude / 6 + 31);
        if (lattitude < -72)
            Letter = 'C';
        else if (lattitude < -64)
            Letter = 'D';
        else if (lattitude < -56)
            Letter = 'E';
        else if (lattitude < -48)
            Letter = 'F';
        else if (lattitude < -40)
            Letter = 'G';
        else if (lattitude < -32)
            Letter = 'H';
        else if (lattitude < -24)
            Letter = 'J';
        else if (lattitude < -16)
            Letter = 'K';
        else if (lattitude < -8)
            Letter = 'L';
        else if (lattitude < 0)
            Letter = 'M';
        else if (lattitude < 8)
            Letter = 'N';
        else if (lattitude < 16)
            Letter = 'P';
        else if (lattitude < 24)
            Letter = 'Q';
        else if (lattitude < 32)
            Letter = 'R';
        else if (lattitude < 40)
            Letter = 'S';
        else if (lattitude < 48)
            Letter = 'T';
        else if (lattitude < 56)
            Letter = 'U';
        else if (lattitude < 64)
            Letter = 'V';
        else if (lattitude < 72)
            Letter = 'W';
        else
            Letter = 'X';
        Easting = 0.5 * Math.log((1 + Math.cos(lattitude * Math.PI / 180) * Math.sin(longitude * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)) / (1 - Math.cos(lattitude * Math.PI / 180) * Math.sin(longitude * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) * 0.9996 * 6399593.62 / Math.pow((1 + Math.pow(0.0820944379, 2) * Math.pow(Math.cos(lattitude * Math.PI / 180), 2)), 0.5) * (1 + Math.pow(0.0820944379, 2) / 2 * Math.pow((0.5 * Math.log((1 + Math.cos(lattitude * Math.PI / 180) * Math.sin(longitude * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)) / (1 - Math.cos(lattitude * Math.PI / 180) * Math.sin(longitude * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)))), 2) * Math.pow(Math.cos(lattitude * Math.PI / 180), 2) / 3) + 500000;
        Easting = Math.round(Easting * 100) * 0.01;
        Northing = (Math.atan(Math.tan(lattitude * Math.PI / 180) / Math.cos((longitude * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) - lattitude * Math.PI / 180) * 0.9996 * 6399593.625 / Math.sqrt(1 + 0.006739496742 * Math.pow(Math.cos(lattitude * Math.PI / 180), 2)) * (1 + 0.006739496742 / 2 * Math.pow(0.5 * Math.log((1 + Math.cos(lattitude * Math.PI / 180) * Math.sin((longitude * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) / (1 - Math.cos(lattitude * Math.PI / 180) * Math.sin((longitude * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)))), 2) * Math.pow(Math.cos(lattitude * Math.PI / 180), 2)) + 0.9996 * 6399593.625 * (lattitude * Math.PI / 180 - 0.005054622556 * (lattitude * Math.PI / 180 + Math.sin(2 * lattitude * Math.PI / 180) / 2) + 4.258201531e-05 * (3 * (lattitude * Math.PI / 180 + Math.sin(2 * lattitude * Math.PI / 180) / 2) + Math.sin(2 * lattitude * Math.PI / 180) * Math.pow(Math.cos(lattitude * Math.PI / 180), 2)) / 4 - 1.674057895e-07 * (5 * (3 * (lattitude * Math.PI / 180 + Math.sin(2 * lattitude * Math.PI / 180) / 2) + Math.sin(2 * lattitude * Math.PI / 180) * Math.pow(Math.cos(lattitude * Math.PI / 180), 2)) / 4 + Math.sin(2 * lattitude * Math.PI / 180) * Math.pow(Math.cos(lattitude * Math.PI / 180), 2) * Math.pow(Math.cos(lattitude * Math.PI / 180), 2)) / 3);
        if (Letter < 'M')
            Northing = Northing + 10000000;
        Northing = Math.round(Northing * 100) * 0.01;

        return new double[]{Easting, Northing, Zone};
    }


    /**
     * Convertie van geografische coördinaten naar quadkeys
     *
     * @param wp een waypoint
     * @return de quadkey
     */
    protected int geo2quadkey(Waypoint wp){
        int quadKey = 0;
        double [] UTM = geo2UTM(wp);
        //UTM[0] = x;
        //UTM[1] = y;
        //UTM[2] = z;

        //Bijna alles gebeurt via BITwise operatoren
        for (int i = (int)UTM[2]; i > 0; i--) {
            int digit = 0, mask = 1 << (i - 1);
            if (((int)UTM[0] & mask) != 0) {
                digit++;
            }

            if (((int)UTM[1] & mask) != 0) {
                digit = digit + 2;
            }
            quadKey += digit;
        }

        return quadKey;
    }


    /**
     * Bepaalt het geografische midden van een verzameling coordinaten
     * @param traject
     * @return geografische coördinaten in een waypoint object
     */
    protected Waypoint getCentre(Traject traject) {
        if(traject.getWaypoints() !=null){
            // als het traject maar 1 waypoint bevat
            if(traject.getWaypoints().size() == 1){
                return traject.getWaypoints().get(0);
            }
            else{
                double lattitude = 0;
                double longitude = 0;

                for(int i=0;i<=traject.getWaypoints().size();i++){
                    lattitude += Double.parseDouble(traject.getWaypoints().get(i).getLatitude());
                    longitude += Double.parseDouble(traject.getWaypoints().get(i).getLongitude());
                }

                lattitude /= traject.getWaypoints().size();
                longitude /= traject.getWaypoints().size();

                return new Waypoint(0,lattitude+"",longitude+"");
            }
        }
        else{
            return null;
        }
    }

}
