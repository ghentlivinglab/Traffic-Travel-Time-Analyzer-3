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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Haalt gegevens op van de Coyote webservice en zet deze om naar objecten
 */
public class CoyoteScraper implements TrafficScraper {

    private static final Logger logger = LogManager.getLogger(CoyoteScraper.class);

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
    protected String sendPost() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String cookie = getSession(httpclient);
        StringBuilder JsonString = new StringBuilder();
        HttpPost httpPost2 = new HttpPost("https://maps.coyotesystems.com/traffic/ajax/get_perturbation_list.ajax.php");
        httpPost2.addHeader("Cookie", cookie);
        httpPost2.addHeader("Connection", "close");

        logger.info("Sending Coyote-request...");
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
        } catch (IOException ex) {
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
    protected String getSession(CloseableHttpClient httpclient) throws IOException {
        String cookie = "";
        HttpPost httpPost = new HttpPost("https://maps.coyotesystems.com/traffic/index.php");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("login", Settings.getSetting("coyote_user")));
        nvps.add(new BasicNameValuePair("password", Settings.getSetting("coyote_password")));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

        CloseableHttpResponse LogInresponse = httpclient.execute(httpPost);

        try {
            logger.info("Trying to login...");
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