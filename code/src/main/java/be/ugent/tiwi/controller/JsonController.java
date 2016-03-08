package be.ugent.tiwi.controller;

import be.ugent.tiwi.domein.RequestType;
import be.ugent.tiwi.domein.google.Google;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jan on 23/02/2016.
 * <p>
 * Update Jelle:
 * Deze klasse is generiek gemaakt zodat de controller met alle type providers overweg kan. Je moet nu wel de JSONController
 * genereen met type hinting. Bijvoorbeeld jc = new JsonController<Here>();
 */
public class JsonController<T extends Object> {
    private InputStream is = null;
    private Gson jObj = null;
    private String json = "";
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);


    // constructor
    public JsonController() {

    }

    // function get json from url
    // by making HTTP POST or GET method
    public Gson makeHttpRequest(String url, String method) {
        // Making HTTP request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            // check for request method
            if (method == "POST") {
                // request method is POST
                // defaultHttpClient
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } else if (method == "GET") {
                // request method is GET
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(is));
                StringBuilder str = new StringBuilder();

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    str.append(line + "\n");
                }
                json = str.toString();

                // Create jObj
                jObj.toJson(json);
            } catch (Exception e) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jObj;

    }

    public String getJsonResponse() {
        if (!this.json.isEmpty()) {
            return this.json;
        } else {
            logger.error("No JSON received from server!");
        }
        return null;
    }

    /**
     * Deze methode kan je gebruiken voor de scrapers. Het geeft een object terug van het gewenste type (paramter klass)
     *
     * @param url    de **volledige** url waar je de gegevens moet ophalen
     * @param klasse de **klasse** waarin de json string in kan geparsed worden
     * @param method een methode van de Enum RequestType, mogelijke waarden zijn bijvoorbeeld RequestType.GET en RequestType.POST
     * @return Een object van de klasse zoals meegeven aan de JsonController (gebruik van generieke klasses)
     */
    public T getObject(String url, Class<T> klasse, RequestType method) {
        makeHttpRequest(url, method.toString());
        Gson gson_obj = new Gson();
        T obj = gson_obj.fromJson(this.json, klasse);

        return obj;
    }

    public Google makeGoogleCall(String url, RequestType type) {
        makeHttpRequest(url, type.toString());
        Gson obj = new Gson();

        Google google_obj = obj.fromJson(this.json, Google.class);

        return google_obj;
    }
}

