package settings;

import java.io.*;
import java.util.Properties;

public class Settings {
    public static void createSettings()
    {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

            // set the properties value
            prop.setProperty("db_url", "10.20.0.2");
            prop.setProperty("db_user", "vop");
            prop.setProperty("db_password", "vop");
            prop.setProperty("db_name", "vop");
            prop.setProperty("here_appid","tsliJF6nV8gV1CCk7yK8");
            prop.setProperty("here_appcode","o8KURFHJC02Zzlv8HTifkg");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static String getSetting(String key)
    {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                    return prop.getProperty(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "null";
        }
    }
}
