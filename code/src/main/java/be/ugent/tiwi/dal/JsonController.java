package be.ugent.tiwi.dal;

import be.ugent.tiwi.controller.exceptions.InvalidMethodException;
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
 * genereren met type hinting. Bijvoorbeeld <code>jc = new JsonController&lt;Here&gt;();</code>.
 */
public class JsonController<T> {
    private static final Logger logger = LogManager.getLogger(JsonController.class);
    private Gson jObj = null;
    private String json = "";

    /**
     * De constructor van {@link JsonController}
     */
    public JsonController() {
        jObj = new Gson();
    }

    /**
     * Een functie die gebruikt wordt door de scrapers om JSON-data terug te krijgen.
     *
     * @param url         De url die een JSON-bestand teruggeeft.
     * @param requestType De request-type <code>HTTP</code> header die gebruikt wordt om de request te sturen.
     * @return Geeft een {@link Gson}-object terug van de opgehaalde JSON.
     * @throws InvalidMethodException Als de requestType niet <code>GET</code> of <code>POST</code> is
     * @throws IOException            Als er geen verbinding kan gemaakt worden met de url
     * @see RequestType
     * @see Gson
     */
    public void makeHttpRequest(String url, RequestType requestType) throws InvalidMethodException, IOException {
        // Making HTTP request
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // check for request method
        HttpResponse httpResponse;
        HttpEntity httpEntity;

        InputStream is = null;
        switch (requestType) {
            case POST:
                HttpPost httpPost = new HttpPost(url);

                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                break;
            case GET:
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);
                httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                break;
            default:
                throw new InvalidMethodException(requestType.toString());
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder str = new StringBuilder();

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            str.append(line + "\n");
        }
        json = str.toString();

        jObj = new Gson();

        // Create jObj
        jObj.toJson(json);

        httpClient.close();
    }

    /**
     * Methode om de laatst teruggekregen JSON-string op te vragen.
     *
     * @return Een string als er minstens 1 succesvolle <code>HTTP</code>-response ontvangen is. Anders <code>null</code>.
     */
    public String getJsonResponse() {
        if (!this.json.isEmpty()) {
            return this.json;
        } else {
            logger.error("No JSON received from server!");
        }
        return null;
    }

    /**
     * Deze methode kan je gebruiken voor de scrapers. Het geeft een object terug van het gewenste type.
     *
     * @param url    de **volledige** url waar je de gegevens moet ophalen
     * @param klasse de **klasse** waarin de JSON string in kan geparsed worden
     * @param method een methode van de Enum RequestType, mogelijke waarden zijn GET en POST
     * @return Een object van de klasse zoals meegeven aan de JsonController (gebruik van generieke klasses)
     * @throws InvalidMethodException Als de requestType niet <code>GET</code> of <code>POST</code> is
     * @throws IOException            Als er geen verbinding kan gemaakt worden met de url
     * @see RequestType
     */
    public T getObject(String url, Class<T> klasse, RequestType method) throws InvalidMethodException, IOException {
        makeHttpRequest(url, method);
        Gson gson_obj = new Gson();

        return gson_obj.fromJson(this.json, klasse);
    }

    /**
     * Deze methode wordt gebruikt door de GoogleScraper om JSON-bestanden uit te lezen.
     *
     * @param url  De url die een JSON-bestand teruggeeft.
     * @param type De request-type header die gebruikt wordt om de request te sturen.
     * @return Een Google-object opgemaakt uit de verkregen JSON.
     * @throws InvalidMethodException Als de requestType niet <code>GET</code> of <code>POST</code> is
     * @throws IOException            Als er geen verbinding kan gemaakt worden met de url
     * @see RequestType
     */
    public Google makeGoogleCall(String url, RequestType type) throws InvalidMethodException, IOException {
        makeHttpRequest(url, type);
        Gson obj = new Gson();

        return obj.fromJson(this.json, Google.class);
    }
}

