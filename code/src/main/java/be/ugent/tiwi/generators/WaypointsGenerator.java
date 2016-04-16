package be.ugent.tiwi.generators;

import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
import be.ugent.tiwi.samplecode.Afstand;
import be.ugent.tiwi.scraper.CoyoteScraper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * Created by Eigenaar on 22/03/2016.
 */
public class WaypointsGenerator extends CoyoteScraper {

    private static final Logger logger = LogManager.getLogger(WaypointsGenerator.class);

    public static void main(String[] args) {
        WaypointsGenerator wg = new WaypointsGenerator();
        wg.generate();
    }

    /**
     * Leest de {@link Traject}en en {@link Waypoint}s uit de **Coyote service**, doet enkele wijzigingen aan de
     * waypoints en schrijft deze weg naar "verkeer-3\code\tempsql.txt".
     *
     * @see Traject
     * @see Waypoint
     */
    public void generate() {
        String jsonString = "";
        try {
            jsonString = sendPost();
        } catch (IOException ex) {
            logger.error("Ophalen gegevens Coyote is mislukt.");
            logger.error(ex);
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonObject e = jsonObject.getAsJsonObject("Gand");
        Set<Map.Entry<String, JsonElement>> trajecten = e.entrySet();

        List<Traject> trajects = new ArrayList<>();

        //Dit bestand staat onder de databank/tool map
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\users\\eigenaar\\desktop\\Waypoints order coyote.txt"))) {
            int trajectIndex = 0;
            for (Map.Entry<String, JsonElement> traject : trajecten) {
                trajectIndex++;
                int optReistijd = 0;

                double prevLat = 0, prevLong = 0;
                double startLat = 0, startLong = 0;
                double totalDistance = 0;
                int afstand = 0;

                Set<Map.Entry<String, JsonElement>> trajectData = traject.getValue().getAsJsonObject().entrySet();
                for (Map.Entry<String, JsonElement> data : trajectData) {
                    switch (data.getKey()) {
                        case "normal_time":
                            optReistijd = data.getValue().getAsInt();
                            break;
                        case "length":
                            afstand = data.getValue().getAsInt();
                            break;
                        case "geometries":
                            prevLat = 0;
                            prevLong = 0;
                            startLat = 0;
                            startLong = 0;
                            reader.readLine();
                            reader.readLine();
                            String volgorde = reader.readLine();
                            if (volgorde.contains(" "))
                                volgorde = volgorde.split(" ")[0];
                            int vIndex = 0;
                            int volgnummer = 1;
                            List<Waypoint> waypoints = new ArrayList<>();
                            while (vIndex < volgorde.length()) {
                                char a = volgorde.charAt(vIndex);
                                char b = volgorde.charAt(vIndex + 1);
                                int arrayIndex = (a - 'A') / 2;
                                JsonArray array = data.getValue().getAsJsonArray().get(arrayIndex).getAsJsonArray();
                                int startI, maxI, incr;
                                if (a < b) {
                                    startI = 0;
                                    maxI = array.size();
                                    incr = 1;
                                } else {
                                    startI = array.size() - 1;
                                    maxI = -1;
                                    incr = -1;
                                }
                                for (int i = startI; i != maxI; i += incr) {
                                    JsonObject pos = array.get(i).getAsJsonObject();
                                    double lat = pos.get("lat").getAsDouble();
                                    double lng = pos.get("lng").getAsDouble();
                                    if (vIndex != 0 || i != startI) {
                                        double distance = Afstand.distance(lat, prevLat, lng, prevLong);
                                        totalDistance += distance;
                                    } else {
                                        startLat = lat;
                                        startLong = lng;
                                    }
                                    if (((vIndex != 0 || i != startI) && (i != maxI - incr || vIndex != volgorde.length() - 2)) && lat != prevLat && lng != prevLong) {
                                        Waypoint w = new Waypoint(volgnummer++, String.valueOf(lat), String.valueOf(lng));
                                        if (waypoints.isEmpty() || (!waypoints.get(waypoints.size() - 1).getLatitude().equals(w.getLatitude())) && (!waypoints.get(waypoints.size() - 1).getLongitude().equals(w.getLongitude()))) {
                                            waypoints.add(w);
                                        }
                                    }
                                    prevLat = lat;
                                    prevLong = lng;
                                }
                                vIndex += 2;
                            }

                            Map<Integer, Integer> m = new HashMap<Integer, Integer>();

                            DatabaseController c = new DatabaseController();
                            m.put(c.haalProviderOp("Coyote").getId(), optReistijd);

                            Traject t = new Traject(trajectIndex, traject.getKey(), (int) Math.round(totalDistance), optReistijd, m, true, String.valueOf(startLat), String.valueOf(startLong), String.valueOf(prevLat), String.valueOf(prevLong));
                            t.setWaypoints(waypoints);
                            trajects.add(t);
                            System.out.println(trajectIndex + " - " + t.getNaam());
                            System.out.println("diff: " + (totalDistance - afstand));
                            break;
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Fix de verkeerde waypoints
        fixGlobalTrajects(trajects);

        //Schrijf naar tempsql.txt
        writeToTempSql(trajects);

        //controleerWaypoints(trajects);
    }

    /**
     * Zoekt een {@link Traject} binnen een lijst {@link Traject}en met een bepaald id
     *
     * @param trajects Een lijst {@link Traject}en
     * @param id       Het id van een {@link Traject}
     * @return Het gezochte {@link Traject}. Indien de lijst geen {@link Traject} bevat met het id, <code>null</code>
     */
    protected Traject getTraject(List<Traject> trajects, int id) {
        Traject t = trajects.get(id - 1);
        if (t.getId() != id) {
            for (Traject traj : trajects)
                if (traj.getId() == id) {
                    return t;
                }
            return null;
        }
        return t;
    }

    /**
     * Deze functie verwijderdt een aantal waypoints uit een lijst {@link Traject}en. Deze waypoints kunnen niet door
     * de {@link be.ugent.tiwi.scraper.TrafficScraper}s gebruikt worden.
     *
     * @param trajects De lijst van aan te passen {@link Traject}en
     */
    private void fixGlobalTrajects(List<Traject> trajects) {
        //Route 14: delete vanaf volgnr 106 (51.04645, 3.73437) tot volgn 114 (51.0445, 3.73421) (één-richtingsverkeer)
        Traject t = getTraject(trajects, 14);
        short step = 0;
        int deletedCount = 0;
        for (int i = 0; i < t.getWaypoints().size(); ++i) {
            Waypoint w = t.getWaypoints().get(i);
            if (step == 0) {
                if (w.getLatitude().equals("51.04645") && w.getLongitude().equals("3.73437")) {
                    step = 1;
                    t.getWaypoints().remove(w);
                    deletedCount++;
                    --i;
                }
            } else if (step == 1) {
                if (w.getLatitude().equals("51.0445") && w.getLongitude().equals("3.73421")) {
                    step = 2;
                }
                t.getWaypoints().remove(w);
                deletedCount++;
                --i;
            } else if (step == 2)
                break;
        }
        logger.debug("Deleted " + deletedCount + " waypoints!");
    }

    /**
     * Schrijft een lijst van {@link Traject}en en hun {@link Waypoint}s naar "verkeer-3\code\tempsql.txt"}.
     *
     * @param trajecten De lijst van weg te schrijven {@link Traject}en
     */
    private void writeToTempSql(List<Traject> trajecten) {

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tempsql.txt"), "utf-8"))) {
            for (Traject traject : trajecten) {
                //Ieder individueel traject
                writer.write("insert into vop.trajecten(naam,lengte,optimale_reistijd,is_active,start_latitude,start_longitude,end_latitude,end_longitude) values(\"");
                writer.write(traject.getNaam());
                writer.write("\",");
                writer.write(String.valueOf(traject.getLengte()));
                writer.write(",");
                writer.write(String.valueOf(traject.getOptimale_reistijd()));
                writer.write(",");
                writer.write(String.valueOf(1));
                writer.write(",\"");
                writer.write(String.valueOf(traject.getStart_latitude()));
                writer.write("\",\"");
                writer.write(String.valueOf(traject.getStart_longitude()));
                writer.write("\",\"");
                writer.write(String.valueOf(traject.getEnd_latitude()));
                writer.write("\",\"");
                writer.write(String.valueOf(traject.getEnd_longitude()));
                writer.write("\"");
                writer.write(");");
                writer.write(System.getProperty("line.separator"));

                //Optimale reistijden per provider
                /*writer.write("insert into vop.optimale_reistijden(traject_id, provider_id, reistijd) values(");
                int i = 0;
                for(Integer providerId : traject.getOptimaleReistijden().keySet()){
                    if (i++ != 0) {
                        writer.write(",");
                        writer.write(System.getProperty("line.separator"));
                        writer.write("(");
                    }
                    writer.write(traject.getId());
                    writer.write(",");
                    writer.write(providerId);
                    writer.write(",");
                    writer.write(traject.getOptimale_reistijdByProvider(providerId));
                    writer.write(")");
                }
                writer.write(";");
                writer.write(System.getProperty("line.separator"));*/

                //Waypoints
                writer.write("insert into vop.waypoints(volgnr, traject_id,latitude,longitude) values(");
                for (int i = 0; i < traject.getWaypoints().size(); ++i) {
                    Waypoint w = traject.getWaypoints().get(i);
                    if (i != 0) {
                        writer.write(",");
                        writer.write(System.getProperty("line.separator"));
                        writer.write("(");
                    }
                    writer.write(String.valueOf(w.getVolgnummer()));
                    writer.write(",");
                    writer.write(String.valueOf(traject.getId()));
                    writer.write(",'");
                    writer.write(w.getLatitude());
                    writer.write("','");
                    writer.write(w.getLongitude());
                    writer.write("')");

                }
                writer.write(";");
                writer.write(System.getProperty("line.separator"));
            }

            writer.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * (DEBUG) Berekent de afstanden tussen alle opeenvolgende waypoints van alle {@link Traject}en, rangschikt deze van hoog
     * naar laag en print de 30 hoogste af.
     *
     * @param trajects De lijst trajecten (met waypoints)
     */
    private void controleerWaypoints(List<Traject> trajects) {

        List<Afstand> afstanden = new ArrayList<>();
        for (Traject t : trajects) {
            List<Waypoint> waypoints = t.getWaypoints();
            for (int i = 1; i < waypoints.size(); ++i) {
                Waypoint wp1 = waypoints.get(i - 1), wp2 = waypoints.get(i);
                double lat1 = Double.parseDouble(wp1.getLatitude());
                double long1 = Double.parseDouble(wp1.getLongitude());
                double lat2 = Double.parseDouble(wp2.getLatitude());
                double long2 = Double.parseDouble(wp2.getLongitude());
                afstanden.add(new Afstand(Afstand.distance(lat1, lat2, long1, long2), wp1, wp2, t));
            }
        }

        //Selection sort
        for (int i = afstanden.size() - 1; i > 0; --i) {
            int minI = i;
            for (int j = 0; j < i; j++) {
                if (afstanden.get(j).getAfstand() < afstanden.get(minI).getAfstand())
                    minI = j;
            }
            Afstand temp = afstanden.get(i);
            afstanden.set(i, afstanden.get(minI));
            afstanden.set(minI, temp);
        }

        for (int i = 0; i < 30; i++) {
            System.out.println(afstanden.get(i).toString());
            System.out.print(afstanden.get(i).getTraject().getNaam());
            System.out.print(" ");
            String url = "https://maps.googleapis.com/maps/api/staticmap?size=720x720&zoom=17&center=" + afstanden.get(i).getWaypoint1().getLatitude() + "," + afstanden.get(i).getWaypoint1().getLongitude();
            char c = 'A';
            Traject t = null;
            for (Traject t2 : trajects)
                if (t2.getId() == afstanden.get(i).getTraject().getId()) {
                    t = t2;
                    break;
                }
            List<Waypoint> waypoints = t.getWaypoints();

            int j = afstanden.get(i).getWaypoint1().getVolgnummer() - 5;
            if (j < 1)
                j = 1;
            int max = j + 10;
            if (max > waypoints.size() - 1)
                max = waypoints.size() - 1;
            for (; j < max; ++j) {
                Waypoint w = waypoints.get(j);
                url += "&markers=label:" + c++ + "%7C";
                if (j == afstanden.get(i).getWaypoint1().getVolgnummer() - 1 || j == afstanden.get(i).getWaypoint1().getVolgnummer()) {
                    url += "color:blue%7C";
                }
                url += w.getLatitude() + "," + w.getLongitude();
                if (i != max - 1)
                    url += "%7C";
            }
            url += "&key=AIzaSyAUdGuaEwMa-gnMK1NbjgnChdwwdMv4WsQ";
            System.out.println(url);
        }

    }


}
