package be.ugent.tiwi.controller.exceptions;

/**
 * Created by Simon on 29/03/2016.
 */
public class InvalidMethodException extends Exception {
    /**
     * Constructor voor InvalidMethodException
     *
     * @param requestType De afgewezen RequestType.
     */
    public InvalidMethodException(String requestType) {
        super("'" + requestType + "' is geen toegelaten RequestType. Gebruik GET of POST.");
    }

}
