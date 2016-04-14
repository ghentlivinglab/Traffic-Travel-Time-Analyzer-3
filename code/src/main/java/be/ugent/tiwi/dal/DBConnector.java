package be.ugent.tiwi.dal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settings.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasse die de connectie met de databank beheert.
 */
public class DBConnector {
    private static Logger logger = LogManager.getLogger(DBConnector.class);
    private Connection connection;

    /**
     * Configureert een connection voor algemeen gebruik.
     *
     * @return Connection met de databank.
     * @throws SQLException Indien de database niet bereikbaar is
     * @see Connection
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            if (connection == null || connection.isClosed()) {
                String db_url = Settings.getSetting("db_url");
                String db_username = Settings.getSetting("db_user");
                String db_password = Settings.getSetting("db_password");
                String db_name = Settings.getSetting("db_name");
                connection = DriverManager.getConnection("jdbc:mysql://" + db_url + ":3306/" + db_name, db_username, db_password);
            }
        } catch (SQLException e) {
            logger.error("Probleem met het opzetten van de verbinding met de databank");
            throw e;
        } catch (ClassNotFoundException e) {
            logger.error("Class not found exception");
        }

        return connection;
    }

    /**
     * Sluit de verbinding met de databank af.
     *
     * @throws SQLException Indien de verbinding met de databank niet kan gesloten worden
     */
    public void close() throws SQLException {
        connection.close();
    }
}
