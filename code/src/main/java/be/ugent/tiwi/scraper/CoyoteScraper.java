package be.ugent.tiwi.scraper;

/**
 * Created by brent on 23/02/2016.
 */


import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Haalt gegevens op van de Coyote webservice en zet deze om naar objecten
 */
public class CoyoteScraper implements TrafficScraper {

    private static final Logger logger = LogManager.getLogger(ScheduleController.class);

    @Override
    /**
     * Aanspreekpunt voor klassen,
     */
    public List<Meting> scrape(List<Traject> trajects) {
        try {
            return JsonToPojo(sendPost());
        } catch (IOException ex) {
            logger.error("Ophalen gegevens Coyote is mislukt.");
            logger.error(ex);
        }
        return null;
    }

    /**
     * Vraag routegegevens op van de coyote site.
     *
     * @return JsonString
     * @throws IOException
     */
    private String sendPost() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String cookie = getSession(httpclient);
        StringBuilder JsonString = new StringBuilder();
        HttpPost httpPost2 = new HttpPost("https://maps.coyotesystems.com/traffic/ajax/get_perturbation_list.ajax.php");
        httpPost2.addHeader("Cookie", cookie);
        httpPost2.addHeader("Connection", "close");

        logger.trace("Sending Coyote-request...");
        CloseableHttpResponse Dataresponse = httpclient.execute(httpPost2);

        try {
            HttpEntity entity2 = Dataresponse.getEntity();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(entity2.getContent()));
                try {
                    String temp;
                    while ((temp = br.readLine()) != null) {
                        JsonString.append(temp);
                    }
                    br.close();

                } catch (ConnectionClosedException ex) {
                }
            } catch (IOException e) {
                logger.error("Could not parse coyote-json file - Status: " + Dataresponse.getStatusLine().toString());
                throw e;
            }
            EntityUtils.consume(entity2);
        }catch(IOException ex){
            throw ex;
        } finally {
            Dataresponse.close();
        }
        return JsonString.toString();
    }


    /**
     * Post login en password naar de server om een sesionId te verkrijgen.
     *
     * @param httpclient client die instaat voor communicatie met de server.
     * @return sessionID van de server.
     * @throws IOException
     */
    private String getSession(CloseableHttpClient httpclient) throws IOException {
        String cookie = "";
        HttpPost httpPost = new HttpPost("https://maps.coyotesystems.com/traffic/index.php");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("login", "110971610"));
        nvps.add(new BasicNameValuePair("password", "50c20b94"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

        CloseableHttpResponse LogInresponse = httpclient.execute(httpPost);

        try {
            logger.trace("Trying to login...");
            HttpEntity entity2 = LogInresponse.getEntity();
            for (Header h : LogInresponse.getAllHeaders()) {
                if (h.getName().equals("Set-Cookie")) {
                    cookie = h.getValue();
                }
            }
            EntityUtils.consume(entity2);
        } catch (IOException ex) {
            logger.error("Probleem bij het aanmelden bij coyote");
            throw ex;
        } finally {
            LogInresponse.close();
        }
        return cookie;
    }

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
            int trajectIndex = 0;
            for (Map.Entry<String, JsonElement> traject : trajecten) {
                trajectIndex++;
                int optReistijd = 0, afstand = 0;
                String startlat = "", startlong = "", stoplat = "", stoplong = "";

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

                            int iIndex = 0, jIndex = 0;
                            double prevLat = 0, prevLong = 0;

                            double maxDist = 0;

                            JsonArray array = data.getValue().getAsJsonArray();
                            for(int i = 0; i < array.size(); ++i){
                                JsonArray elem = array.get(i).getAsJsonArray();
                                for(int j = 0; j < elem.size(); j++){
                                    JsonElement elem2 = elem.get(j);
                                    double lat = elem2.getAsJsonObject().get("lat").getAsDouble();
                                    double lon = elem2.getAsJsonObject().get("lng").getAsDouble();
                                    if(i != 0 || j != 0){
                                        double dist = CoyoteScraper.distance(prevLat, lat, prevLong, lon, 0, 0);
                                        if(dist > maxDist){
                                            iIndex = i;
                                            jIndex = j;
                                            maxDist = dist;
                                        }
                                    }
                                    prevLat = lat;
                                    prevLong = lon;
                                }
                            }

                            JsonArray array1 = array.get(0).getAsJsonArray();
                            JsonObject obj1 = array1.get(array1.size()-1).getAsJsonObject();
                            String url = "https://maps.googleapis.com/maps/api/staticmap?size=720x720&zoom=17&center="+obj1.get("lat").getAsString() + ","+obj1.get("lng").getAsString();
                            char c = 'A';
                            for(int i = 0; i < array.size(); ++i){
                                array1 = array.get(i).getAsJsonArray();
                                obj1 = array1.get(0).getAsJsonObject();
                                url += "&markers=label:" + c + "%7C" + obj1.get("lat").getAsString() + ","+obj1.get("lng").getAsString() + "%7C";
                                c++;
                                array1 = array.get(i).getAsJsonArray();
                                obj1 = array1.get(array1.size() - 1).getAsJsonObject();
                                url += "&markers=label:" + c + "%7C" + obj1.get("lat").getAsString() + ","+obj1.get("lng").getAsString();
                                c++;
                                if(i != array.size() -1)
                                    url += "%7C";
                            }
                            url+="&key=AIzaSyAUdGuaEwMa-gnMK1NbjgnChdwwdMv4WsQ";
                            System.out.println(trajectIndex + " - " + traject.getKey());
                            System.out.println(url);

                            coordinatendataStart = data.getValue().getAsJsonArray().get(0).getAsJsonArray().get(0).getAsJsonObject().entrySet();
                            coordinatendataStop = data.getValue().getAsJsonArray().get(0).getAsJsonArray().get(data.getValue().getAsJsonArray().get(0).getAsJsonArray().size() - 1).getAsJsonObject().entrySet();
                            break;
                    }
                }

                for (Map.Entry<String, JsonElement> data : coordinatendataStart) {
                    switch (data.getKey()) {
                        case "lng":
                            startlong = data.getValue().getAsString();
                            break;
                        case "lat":
                            startlat = data.getValue().getAsString();
                            break;

                    }
                }
                for (Map.Entry<String, JsonElement> data : coordinatendataStop) {
                    switch (data.getKey()) {
                        case "lng":
                            stoplong = data.getValue().getAsString();
                            break;
                        case "lat":
                            stoplat = data.getValue().getAsString();
                            break;
                    }
                }

                writer.write("insert into vop.trajecten(naam,lengte,optimale_reistijd,is_active,start_latitude,start_longitude,end_latitude,end_longitude) values(\"");
                writer.write(traject.getKey());
                writer.write("\",");
                writer.write(String.valueOf(afstand));
                writer.write(",");
                writer.write(String.valueOf(optReistijd));
                writer.write(",");
                writer.write(String.valueOf(1));
                writer.write(",\"");
                writer.write(startlat);
                writer.write("\",\"");
                writer.write(startlong);
                writer.write("\",\"");
                writer.write(stoplat);
                writer.write("\",\"");
                writer.write(stoplong);
                writer.write("\"");
                writer.write(");");
                writer.write(System.getProperty("line.separator"));

                for (int i = 1; i < jsonArray.size() - 1; i++) {
                    String lng = "", lat = "";

                    writer.write("insert into vop.waypoints(volgnr, traject_id,latitude,longitude) values(");
                    writer.write(String.valueOf(i));
                    writer.write(",");
                    writer.write("(select id from vop.trajecten where naam = \"");
                    writer.write(traject.getKey());
                    writer.write("\"),");

                    for (Map.Entry<String, JsonElement> data : jsonArray.get(i).getAsJsonObject().entrySet()) {
                        switch (data.getKey()) {
                            case "lng":
                                lng = data.getValue().getAsString();
                                break;
                            case "lat":
                                lat = data.getValue().getAsString();
                        }
                    }
                    writer.write(lat);
                    writer.write(",");
                    writer.write(lng);
                    writer.write(");");
                    writer.write(System.getProperty("line.separator"));
                }
                writer.write(System.getProperty("line.separator"));
            }


        } catch (Exception ex) {

        }
    }

    /**
     * Parset Json die binnen komt van Coyote en zet deze om naar Java-objecten. Parset de gegevens handmatig omdat Coyote dynamische keys gebruikt, waarmee Gson niet eenvoudig mee overweg kan.
     *
     * @param JsonString Geldige Json afkomstig van Coyote.
     * @return Een lijst van Opgehaalde metingen.
     */
    private List<Meting> JsonToPojo(String JsonString) {
        List<Meting> metingen = new ArrayList<>();
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(JsonString, JsonObject.class);
        JsonObject e = jsonObject.getAsJsonObject("Gand");
        Set<Map.Entry<String, JsonElement>> trajecten = e.entrySet();
        DatabaseController dbController = new DatabaseController();

        Provider coyote = dbController.haalProviderOp("Coyote");
        for (Map.Entry<String, JsonElement> traject : trajecten) {
            Traject trajectObj = dbController.haalTrajectOp(traject.getKey());
            Meting metingObj = new Meting();
            metingObj.setProvider(coyote);
            metingObj.setTimestamp(LocalDateTime.now());
            metingObj.setTraject(trajectObj);

            Set<Map.Entry<String, JsonElement>> trajectData = traject.getValue().getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> data : trajectData) {
                switch (data.getKey()) {
                    case "normal_time":
                        metingObj.setOptimale_reistijd(data.getValue().getAsInt());
                        break;
                    case "real_time":
                        metingObj.setReistijd(data.getValue().getAsInt());
                        break;
                }
            }

            metingen.add(metingObj);
        }


        return metingen;
    }
}