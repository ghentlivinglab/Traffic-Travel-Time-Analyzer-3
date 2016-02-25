package be.ugent.tiwi.scraper;

/**
 * Created by brent on 23/02/2016.
 */


import be.ugent.tiwi.controller.ScheduleController;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoyoteScaper extends TrafficScraper {

    private final String USER_AGENT = "Mozilla/5.0";
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);

    @Override
    public void scrape() {
        try {
            sendPost();
        } catch (IOException ex) {
            logger.error("Oei, sendpost ging mis");
            logger.error(ex);
        }
    }

    /**
     * Vraag routegegevens op van de coyote site.
     * @throws IOException
     */
    private void sendPost() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String cookie = getSession(httpclient);

        HttpPost httpPost2 = new HttpPost("https://maps.coyotesystems.com/traffic/ajax/get_perturbation_list.ajax.php");
        httpPost2.addHeader("Cookie", cookie);
        httpPost2.addHeader("Connection", "close");
        CloseableHttpResponse Dataresponse = httpclient.execute(httpPost2);

        try {
            HttpEntity entity2 = Dataresponse.getEntity();
            try {entity2.writeTo(System.out);}catch (ConnectionClosedException ex){}
            EntityUtils.consume(entity2);
        } finally {
            Dataresponse.close();
        }

    }


    /**
     *  Post login en password naar de server om een sesionId te verkrijgen.
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
            HttpEntity entity2 = LogInresponse.getEntity();
            for (Header h : LogInresponse.getAllHeaders()) {
                if (h.getName().equals("Set-Cookie")) {
                    cookie = h.getValue();
                }
            }
            EntityUtils.consume(entity2);
        }catch (Exception ex){
            logger.error("Probleem bij het aanmelden bij coyote");
            logger.error(ex);
        } finally {
            LogInresponse.close();
        }
        return cookie;
    }

}