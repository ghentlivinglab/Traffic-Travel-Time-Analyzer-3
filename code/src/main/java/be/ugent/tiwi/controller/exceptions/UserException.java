package be.ugent.tiwi.controller.exceptions;

/**
 * Created by Jeroen on 19/04/2016.
 */
public class UserException extends Exception {
    /**
     * Fouten opgooien bij het foutlopen van alles omtrent userbeheer
     * @param message
     */
    public UserException(String message) {
        super(message);
    }

}
