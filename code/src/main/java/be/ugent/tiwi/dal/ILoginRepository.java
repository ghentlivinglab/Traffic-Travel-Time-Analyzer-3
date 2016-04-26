package be.ugent.tiwi.dal;

import be.ugent.tiwi.controller.exceptions.UserException;
import be.ugent.tiwi.domein.*;

import java.sql.SQLException;

/**
 * Created by Jeroen on 19/04/2016.
 */
public interface ILoginRepository {

    /**
     * User toevoegen aan de database
     * @param user
     */
    void addUser(User user) throws UserException;

    /**
     * Controle indien de opgegeven user aanwezig is in de database
     * @param user
     * @return
     */
    boolean userExists(User user);

    /**
     * Controle indien de gegeven combinatie van gebruikersnaam en wachtwoord overeen komen met wat in de database zit
     * @param user
     * @return
     */
    boolean credentialsCorrect(User user);


    /**
     * Gebruiken bij het inloggen om een nieuwe sessionID te genereren
     * @param user
     * @return
     */
    void generateUserSessionID(User user) throws UserException;


    /**
     * De Session ID teruggeven die in de database zit
     * @param user
     * @return
     */
    String getUserSessionID(User user);
}
