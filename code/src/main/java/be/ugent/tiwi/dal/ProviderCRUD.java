package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jelle on 18.02.16.
 */
public class ProviderCRUD {
    private DBConnector connector;

    public ProviderCRUD() {
        connector = new DBConnector();
    }

    public Provider getProvider(String naam)
    {
        String query = "select * from providers";
        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
            {
                String naam_in_tabel = rs.getString("naam");
                if(naam_in_tabel.equals(naam))
                    return new Provider(naam_in_tabel, rs.getBoolean("is_active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
