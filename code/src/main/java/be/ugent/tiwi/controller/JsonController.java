package be.ugent.tiwi.controller;

import com.sun.deploy.net.HttpResponse;

import java.io.InputStream;

/**
 * Created by Jan on 23/02/2016.
 */
public class JsonController {
    InputStream is = null;
    Gson jObj = null;
    String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST or GET method
    public Gson makeHttpRequest(String url, String method) {
        // Making HTTP request
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

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
                //jObj.fromJson(new InputStreamReader(is));

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(is));
                StringBuilder str = new StringBuilder();

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    str.append(line + "\n");
                }
                json = str.toString();
            } catch (Exception e) {
            }


        } catch (IOException e) {
            e.printStackTrace();
        }



        // try parse the string to a JSON object
         /*   try {

                    jObj = new JSONObject(json);
            } catch (JSONException ex) {
                    //Logger.log(Level.FINE, "Error parsing data", ex);
            }*/

        // return JSON String
        return jObj;

    }
}
