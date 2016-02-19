package be.ugent.tiwi.dal;

import org.mariadb.jdbc.*;
import settings.Settings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jelle on 18.02.16.
 */
public class DBConnector {
    private Connection connection;

    public Connection getConnection()
    {
        try {
            if (connection==null || connection.isClosed()) {
                String db_url = Settings.getSetting("db_url");
                String db_username = Settings.getSetting("db_user");
                String db_password = Settings.getSetting("db_password");
                String db_name = Settings.getSetting("db_name");
                connection = DriverManager.getConnection("jdbc:mysql://" + db_url + "/" + db_name, db_username, db_password);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }

        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
