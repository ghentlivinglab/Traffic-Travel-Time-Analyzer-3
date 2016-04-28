package be.ugent.tiwi.dal;

import be.ugent.tiwi.controller.exceptions.UserException;
import be.ugent.tiwi.domein.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoginRepository implements ILoginRepository {
    private static final Logger logger = LogManager.getLogger(LoginRepository.class);
    private DBConnector connector;
    private PreparedStatement statUser = null;

    /**
     * Constructor van de klasse.
     */
    public LoginRepository() {
        connector = new DBConnector();
    }

    /**
     * User toevoegen
     * @param user
     * @throws SQLException Wanneer er een sql-error opgetreden is.
     * @throws UserException Wanneer de user reeds bestaat.
     */
    @Override
    public void addUser(User user) throws UserException {
        if (userExists(user)) {
            throw new UserException("Username " + user.getUsername() + " reeds in gebruik.");
        } else {
            try {
                String stringAddUser = "INSERT INTO users(username, password) VALUES(?,?)";
                statUser = connector.getConnection().prepareStatement(stringAddUser);
                statUser.setString(1, user.getUsername());
                statUser.setString(2, user.getPassword());
                statUser.executeUpdate();

            } catch (SQLException ex) {
                logger.error("Toevoegen user mislukt", ex);
            } finally {
                try {
                    statUser.close();
                } catch (Exception e) { /* ignored */ }
                try {
                    connector.close();
                } catch (Exception e) { /* ignored */ }
            }
        }
    }


    /**
     * Controle indien de opgegeven user aanwezig is in de database
     * @param user
     * @throws SQLException Wanneer er een sql-error opgetreden is
     * @return
     */
    @Override
    public boolean userExists(User user){
        boolean exists = true;
        ResultSet rs = null;
        try {
            String stringUser = "select * from users where username = ?";
            statUser = connector.getConnection().prepareStatement(stringUser);
            statUser.setString(1, user.getUsername());
            rs = statUser.executeQuery();

            if (!rs.next()) {
                exists = false;
            }
            //Nodig??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            //return exists;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statUser.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }

        return exists;
    }

    /**
     * Controle indien de gegeven combinatie van gebruikersnaam en wachtwoord overeen komen met wat in de database zit
     * Nieuwe random SessionID wordt hier aangemaakt
     * @param user
     * @return
     */
    @Override
    public boolean credentialsCorrect(User user) {
        boolean correct = true;
        ResultSet rs = null;
        try {
            String stringUser = "select * from users where username = ? and password = ?";
            statUser = connector.getConnection().prepareStatement(stringUser);
            statUser.setString(1, user.getUsername());
            statUser.setString(2, user.getPassword());
            rs = statUser.executeQuery();

            if (!rs.next()) {
                correct = false;
            }
            //Nodig??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            //return exists;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statUser.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }

        return correct;
    }


    /**
     * Controle indien de gegeven combinatie van gebruikersnaam en wachtwoord overeen komen met wat in de database zit
     * Nieuwe random SessionID wordt hier aangemaakt
     * @param user
     * @return
     */
    @Override
    public void generateUserSessionID(User user) throws UserException{
        if (!credentialsCorrect(user)) {
            throw new UserException("SessionID kon niet gegenereerd worden. Login incorrect");
        } else {
            try {
                String stringGenSessionID = "UPDATE users SET sessionID=? WHERE username=? and password=?";

                statUser = connector.getConnection().prepareStatement(stringGenSessionID);

                Random randomGenerator = new Random();
                //8 digits
                statUser.setInt(1,randomGenerator.nextInt(100000000));
                statUser.setString(2, user.getUsername());
                statUser.setString(3, user.getPassword());
                statUser.executeUpdate();

            } catch (SQLException ex) {
                logger.error("SessionId genereren failed", ex);
            } finally {
                try {
                    statUser.close();
                } catch (Exception e) { /* ignored */ }
                try {
                    connector.close();
                } catch (Exception e) { /* ignored */ }
            }
        }

    }




    /**
     * De Session ID teruggeven die in de database zit
     * @param user
     * @return
     */
    public String getUserSessionID(User user){
        String sessionID = "";

        ResultSet rs = null;
        try {
            String stringUser = "select * from users where username = ?";
            statUser = connector.getConnection().prepareStatement(stringUser);
            statUser.setString(1, user.getUsername());
            rs = statUser.executeQuery();

            if (rs.next()) {
                sessionID = rs.getString("sessionID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statUser.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }

        return sessionID;
    }

    /**
     * Geeft alle users terug die zich in de database bevinden
     *
     * @return Lijst van users
     */
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        ResultSet rs = null;
        try {
            String stringUser = "select * from users";
            statUser = connector.getConnection().prepareStatement(stringUser);
            rs = statUser.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int sessionID = rs.getInt("sessionID");

                users.add(new User(id, username,password,sessionID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statUser.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return users;
    }
}
