package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jelle on 18.02.16.
 */
public class ProviderRepository {
    private DBConnector connector;

    public ProviderRepository() {
        connector = new DBConnector();
    }

    public Provider getProvider(String naam) {
        String query = "select * from providers where naam ='" + naam + "'";
        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String naam_in_tabel = rs.getString("naam");
                if (naam_in_tabel.equals(naam))
                    return new Provider(rs.getInt("id"), naam_in_tabel, rs.getBoolean("is_active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Provider getProvider(int id) {
        String query = "select * from providers where id ='" + id + "'";
        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id_in_tabel = rs.getInt("id");
                if (id_in_tabel == id)
                    return new Provider(rs.getInt("id"), rs.getString("naam"), rs.getBoolean("is_active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Provider> getActieveProviders() {
        String query = "select * from providers where is_active = 1";
        List<Provider> providers = new ArrayList<>();
        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                providers.add(new Provider(rs.getInt("id"), rs.getString("naam"), rs.getBoolean("is_active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
