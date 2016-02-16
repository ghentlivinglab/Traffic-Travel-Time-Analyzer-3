package be.ugent.tiwi;

import settings.Settings;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Settings.createSettings();
        System.out.println("Settings created!");
    }
}
