/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waypointfixer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 *
 * @author Eigenaar
 */
public class WaypointFixer {

    /**
     * @param args the command line arguments
     */
    public static String api_key = "AIzaSyAUdGuaEwMa-gnMK1NbjgnChdwwdMv4WsQ";
    public static String desktopDir = "c:\\users\\Eigenaar\\Desktop\\";
    
    public static void main(String[] args) {
        File input = new File(desktopDir + "nieuwe_trajecten.txt");
        File output = new File(desktopDir + "nieuwe_trajecten_fixed.txt");
        Charset charset = Charset.forName("US-ASCII");
        try {
            BufferedReader reader = Files.newBufferedReader(input.toPath(), charset);
            BufferedWriter writer = Files.newBufferedWriter(output.toPath(), charset);
            String line = null;
            int trajectid = 0;
            writer.append("INSERT INTO waypoints (volgnr, traject_id, omgekeerd, latitude, longitude) VALUES \n");
            while ((line = reader.readLine()) != null) {
                ++trajectid;
                line = reader.readLine();
                URL googleUrl = new URL("https://roads.googleapis.com/v1/snapToRoads?path=" + line + "&interpolate=true&key=" + api_key);
                HttpURLConnection http = (HttpURLConnection) googleUrl.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String inputLine, latitude = "", longitude = "", prevLat = "", prevLong = "";
                String latSearchString = "\"latitude\": ", longSearchString = "\"longitude\": "; 
                int volgnummer = 0;
		while ((inputLine = in.readLine()) != null) {
                    if(inputLine.contains(latSearchString))
                        latitude = inputLine.substring(inputLine.indexOf(latSearchString) + latSearchString.length(), inputLine.length() - 1);
                    else if(inputLine.contains(longSearchString)){
                        longitude = inputLine.substring(inputLine.indexOf(longSearchString) + longSearchString.length());
                        if(!prevLat.equals(latitude) && !prevLong.equals(longitude))
                            writer.append("\t(" + volgnummer++ + ", " + trajectid + ", 0, '" + latitude + "', '" + longitude + "'),\n");
                        prevLat = latitude;
                        prevLong = longitude;
                    }
		}
                
                in.close();
                http.disconnect();
                
                reader.readLine();
                line = reader.readLine();
                googleUrl = new URL("https://roads.googleapis.com/v1/snapToRoads?path=" + line + "&interpolate=true&key=" + api_key);
                http = (HttpURLConnection) googleUrl.openConnection();
                in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                prevLat = "";
                prevLong = "";
                volgnummer = 0;
		while ((inputLine = in.readLine()) != null) {
                    if(inputLine.contains(latSearchString))
                        latitude = inputLine.substring(inputLine.indexOf(latSearchString) + latSearchString.length(), inputLine.length() - 1);
                    else if(inputLine.contains(longSearchString)){
                        longitude = inputLine.substring(inputLine.indexOf(longSearchString) + longSearchString.length());
                        if(!prevLat.equals(latitude) && !prevLong.equals(longitude))
                            writer.append("\t(" + volgnummer++ + ", " + trajectid + ", 1, '" + latitude + "', '" + longitude + "'),\n");
                        prevLat = latitude;
                        prevLong = longitude;
                    }
		}
                in.close();
                http.disconnect();
                writer.flush();
                
                reader.readLine();
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
    
}
