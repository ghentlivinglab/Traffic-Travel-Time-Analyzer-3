package be.ugent.tiwi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Created by Jan on 23/02/2016.
 */
public class JsonController {
    InputStream is = null;
    Gson jObj = null;
    String json = "";

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
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } else if(method == "GET"){
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

        // return jObj
        return jObj;

    }
}
