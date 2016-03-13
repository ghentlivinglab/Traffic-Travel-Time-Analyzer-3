package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
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
    private static final Logger logger = LogManager.getLogger(ProviderRepository.class);

    private PreparedStatement statActieveProviders = null;
    private PreparedStatement statProviderId = null;
    private PreparedStatement statProviderNaam = null;

    private String stringActieveProviders = "select * from providers where is_active = 1";
    private String stringProviderId = "select * from providers where id = ?";
    private String stringProviderNaam = "select * from providers where naam = ?";

    public ProviderRepository() {
        connector = new DBConnector();
        try {
            statActieveProviders = connector.getConnection().prepareStatement(stringActieveProviders);
            statProviderId = connector.getConnection().prepareStatement(stringProviderId);
            statProviderNaam = connector.getConnection().prepareStatement(stringProviderNaam);
        } catch (SQLException e) {
            logger.error("Verbinden met de databank is mislukt");
        }
    }

    public Provider getProvider(String naam) {

        try {
            statProviderId.setString(1,naam);
            ResultSet rs = statProviderNaam.executeQuery();

            while (rs.next()) {
                String naam_in_tabel = rs.getString("naam");
                if (naam_in_tabel.equals(naam)){
                    Provider p = new Provider(rs.getInt("id"), naam_in_tabel, rs.getBoolean("is_active"));
                    connector.closeConnection();
                    return p;
                }
            }
            connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Provider getProvider(int id) {
        try {
            statProviderId.setInt(1,id);
            ResultSet rs = statProviderId.executeQuery();

            while (rs.next()) {
                int id_in_tabel = rs.getInt("id");
                if (id_in_tabel == id) {
                    Provider p = new Provider(rs.getInt("id"), rs.getString("naam"), rs.getBoolean("is_active"));
                    connector.closeConnection();
                    return p;
                }
            }
            connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Provider> getActieveProviders() {
        List<Provider> providers = new ArrayList<>();
        try {
            ResultSet rs = statActieveProviders.executeQuery();

            while (rs.next()) {
                providers.add(new Provider(rs.getInt("id"), rs.getString("naam"), rs.getBoolean("is_active")));
            }

            connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return providers;
    }
}
