package be.ugent.tiwi.samplecode;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Traject;
import be.ugent.tiwi.domein.Waypoint;
import be.ugent.tiwi.scraper.CoyoteScraper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Eigenaar on 22/03/2016.
 */
public class WaypointsTest extends CoyoteScraper {

    private static final Logger logger = LogManager.getLogger(WaypointsTest.class);


    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = distance*distance + height*height;

        return Math.sqrt(distance);
    }

    public void genereerSQL() {
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

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tempsql.txt"), "utf-8"))) {
            //Dit bestand staat onder de databank/tool map
            BufferedReader reader = new BufferedReader(new FileReader("C:\\users\\eigenaar\\desktop\\Waypoints order coyote.txt"));
            int trajectIndex = 0;
            for (Map.Entry<String, JsonElement> traject : trajecten) {
                trajectIndex++;
                int optReistijd = 0;

                double prevLat = 0, prevLong = 0;
                double startLat = 0, startLong = 0;
                StringBuilder waypoints = new StringBuilder();
                double totalDistance = 0;
                int afstand = 0;

                Set<Map.Entry<String, JsonElement>> coordinatendataStart = null;
                Set<Map.Entry<String, JsonElement>> coordinatendataStop = null;
                JsonArray jsonArray = new JsonArray();

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
                            jsonArray = data.getValue().getAsJsonArray().get(0).getAsJsonArray();
                            int index1 = 0, index2 = 0;
                            prevLat = 0; prevLong = 0;
                            startLat = 0; startLong = 0;
                            waypoints = new StringBuilder();
                            double maxDist = 0;
                            reader.readLine();
                            reader.readLine();
                            String volgorde = reader.readLine();
                            if(volgorde.contains(" "))
                                volgorde = volgorde.split(" ")[0];
                            int vIndex = 0;
                            int volgnummer = 1;
                            waypoints.append("insert into vop.waypoints(volgnr, traject_id,latitude,longitude) values(");
                            if(traject.getKey().contains("Dok-Noord (R40) Northboun"))
                                System.out.print("");
                            while(vIndex < volgorde.length()) {
                                char a = volgorde.charAt(vIndex);
                                char b = volgorde.charAt(vIndex + 1);
                                int arrayIndex = ((int)(a - 'A'))/2;
                                JsonArray array = data.getValue().getAsJsonArray().get(arrayIndex).getAsJsonArray();
                                int startI, maxI, incr;
                                if(a < b) {
                                    startI = 0;
                                    maxI = array.size();
                                    incr = 1;
                                }else{
                                    startI = array.size()-1;
                                    maxI = -1;
                                    incr = -1;
                                }
                                for(int i = startI; i != maxI; i += incr){
                                    JsonObject pos = array.get(i).getAsJsonObject();
                                    double lat = pos.get("lat").getAsDouble();
                                    double lng = pos.get("lng").getAsDouble();
                                    if(vIndex != 0 || i != startI){
                                        waypoints.append(",").append(System.getProperty("line.separator")).append("(");
                                        double distance = distance(lat, prevLat, lng, prevLong, 0, 0);
                                        totalDistance += distance;
                                        if(maxDist < distance){
                                            maxDist = distance;
                                            index1 = vIndex;
                                            index2 = i;
                                        }
                                    }else{
                                        startLat = lat;
                                        startLong = lng;
                                    }
                                    waypoints.append(volgnummer++).append(",").append(trajectIndex).append(",\"").append(lat).append("\",\"").append(lng).append("\")");
                                    prevLat = lat;
                                    prevLong = lng;
                                }

                                vIndex += 2;
                            }
                            waypoints.append(System.getProperty("line.separator"));

                            System.out.println(trajectIndex + " - " + traject.getKey());
                            System.out.println("diff: " + (totalDistance - afstand));

                            break;
                    }
                }

                writer.write("insert into vop.trajecten(naam,lengte,optimale_reistijd,is_active,start_latitude,start_longitude,end_latitude,end_longitude) values(\"");
                writer.write(traject.getKey());
                writer.write("\",");
                writer.write(String.valueOf((int)Math.round(totalDistance)));
                writer.write(",");
                writer.write(String.valueOf(optReistijd));
                writer.write(",");
                writer.write(String.valueOf(1));
                writer.write(",\"");
                writer.write(String.valueOf(startLat));
                writer.write("\",\"");
                writer.write(String.valueOf(startLong));
                writer.write("\",\"");
                writer.write(String.valueOf(prevLat));
                writer.write("\",\"");
                writer.write(String.valueOf(prevLong));
                writer.write("\"");
                writer.write(");");
                writer.write(System.getProperty("line.separator"));
                writer.write(waypoints.toString());
                writer.write(";");
                writer.write(System.getProperty("line.separator"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void controleerWaypoints(){

        TrajectRepository repo = new TrajectRepository();
        List<Traject> trajects = repo.getTrajectenMetCoordinaten();
        List<Afstand> afstanden = new ArrayList<>();
        for(Traject t : trajects){
            List<Waypoint> waypoints = t.getWaypoints();
            for(int i = 1; i < waypoints.size(); ++i){
                Waypoint wp1 = waypoints.get(i-1), wp2 = waypoints.get(i);
                double lat1 = Double.parseDouble(wp1.getLatitude());
                double long1 = Double.parseDouble(wp1.getLongitude());
                double lat2 = Double.parseDouble(wp2.getLatitude());
                double long2 = Double.parseDouble(wp2.getLongitude());
                afstanden.add(new Afstand(distance(lat1, lat2, long1, long2, 0, 0), wp1, wp2));
            }
        }

        //Selection sort
        for(int i = afstanden.size() - 1; i > 0; --i){
            int minI = i;
            for(int j = 0; j < i; j++){
                if(afstanden.get(j).getAfstand() < afstanden.get(minI).getAfstand())
                    minI = j;
            }
            Afstand temp = afstanden.get(i);
            afstanden.set(i, afstanden.get(minI));
            afstanden.set(minI, temp);
        }

        for(int i = 0; i < 30; i++){
            System.out.println(afstanden.get(i).toString());
            System.out.print(afstanden.get(i).getWaypoint1().getTraject().getNaam());
            System.out.print(" ");
            String url = "https://maps.googleapis.com/maps/api/staticmap?size=720x720&zoom=17&center=" + afstanden.get(i).getWaypoint1().getLatitude() + "," + afstanden.get(i).getWaypoint1().getLongitude();
            char c = 'A';
            Traject t = null;
            for(Traject t2 : trajects)
                if(t2.getId() == afstanden.get(i).getWaypoint1().getTraject().getId()){
                    t = t2;
                    break;
                }
            List<Waypoint> waypoints = t.getWaypoints();

            int j = afstanden.get(i).getWaypoint1().getVolgnummer() - 5;
            if(j < 1)
                j = 1;
            int max = j + 10;
            if(max > waypoints.size() - 1)
                max = waypoints.size() - 1;
            for (; j < max; ++j) {
                Waypoint w = waypoints.get(j);
                url += "&markers=label:" + c++ + "%7C";
                if(j == afstanden.get(i).getWaypoint1().getVolgnummer() - 1 || j == afstanden.get(i).getWaypoint1().getVolgnummer()){
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
