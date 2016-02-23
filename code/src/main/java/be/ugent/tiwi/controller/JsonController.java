package be.ugent.tiwi.controller;

import be.ugent.tiwi.domein.here.*;
import be.ugent.tiwi.domein.RequestType;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jan on 23/02/2016.
 */
public class JsonController {
    private InputStream is = null;
    private Gson jObj = null;
    private String json = "";

    // constructor
    public JsonController() {
    }

    public String getJson() {
        return json;
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

    public Here makeHereCall(String url, RequestType type)
    {
        makeHttpRequest(url,type.toString());
        Gson obj = new Gson();

        Here here_obj = obj.fromJson(this.json,Here.class);

        return here_obj;
    }
}

