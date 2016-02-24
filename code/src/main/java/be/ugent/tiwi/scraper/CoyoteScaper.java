package be.ugent.tiwi.scraper;

/**
 * Created by brent on 23/02/2016.
 */


import be.ugent.tiwi.controller.ScheduleController;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
        }catch (IOException ex){
            logger.error("Oei, sendpost ging mis");
            logger.error(ex);
        }
    }

    private void sendPost() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String cookie="";
        HttpPost httpPost = new HttpPost("https://maps.coyotesystems.com/traffic/index.php");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("login", "110971610"));
        nvps.add(new BasicNameValuePair("password", "50c20b94"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

        CloseableHttpResponse LogInresponse = httpclient.execute(httpPost);

        try {
            System.out.println(LogInresponse.getStatusLine());
            HttpEntity entity2 = LogInresponse.getEntity();
            for (Header h:LogInresponse.getAllHeaders()) {
                if(h.getName().equals("Set-Cookie")){
                    cookie = h.getValue();
                }
                System.out.printf("%s:%s\n", h.getName(), h.getValue());
            };
            entity2.writeTo(System.out);
            EntityUtils.consume(entity2);
        } finally {
            LogInresponse.close();
        }


        HttpPost httpPost2 = new HttpPost("https://maps.coyotesystems.com/traffic/ajax/get_perturbation_list.ajax.php");
        httpPost2.addHeader("Cookie",cookie);
        httpPost2.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

        CloseableHttpResponse LogInresponse2 = httpclient.execute(httpPost2);

        try {
            System.out.println(LogInresponse2.getStatusLine());
            HttpEntity entity2 = LogInresponse2.getEntity();
            for (Header h:LogInresponse2.getAllHeaders()) {
                System.out.printf("%s:%s\n", h.getName(), h.getValue());
            };
            entity2.writeTo(System.out);
            EntityUtils.consume(entity2);
        } finally {
            LogInresponse2.close();
        }

    }

}
