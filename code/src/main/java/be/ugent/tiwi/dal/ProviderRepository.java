package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jelle on 18.02.16.
 */
public class ProviderRepository {
    private static final Logger logger = LogManager.getLogger(ProviderRepository.class);
    private DBConnector connector;
    private PreparedStatement statProviders = null;

    private String stringActieveProviders = "select * from providers where is_active = 1 order by naam";
    private String stringProviderId = "select * from providers where id = ?";
    private String stringProviderNaam = "select * from providers where naam = ?";
    private String stringProviders = "select * from providers order by naam";
    private String stringDeleteOptimaleReistijden = "delete from optimale_reistijden where provider_id = ?";
    private String stringInsertOptimaleReistijden = "insert into optimale_reistijden(provider_id, traject_id, reistijd) values (?, ?, ?)";

    public ProviderRepository() {
        connector = new DBConnector();
    }

    public Provider getProvider(String naam) {
        ResultSet rs = null;
        try {
            statProviders = connector.getConnection().prepareStatement(stringProviderNaam);
            statProviders.setString(1, naam);
            rs = statProviders.executeQuery();

            while (rs.next()) {
                String naam_in_tabel = rs.getString("naam");
                if (naam_in_tabel.equals(naam)) {
                    Provider p = new Provider(rs.getInt("id"), naam_in_tabel, rs.getBoolean("is_active"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statProviders.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        return null;
    }

    public Provider getProvider(int id) {
        ResultSet rs = null;
        try {
            statProviders = connector.getConnection().prepareStatement(stringProviderId);
            statProviders.setInt(1, id);
            rs = statProviders.executeQuery();

            while (rs.next()) {
                int id_in_tabel = rs.getInt("id");
                if (id_in_tabel == id) {
                    Provider p = new Provider(rs.getInt("id"), rs.getString("naam"), rs.getBoolean("is_active"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statProviders.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        return null;
    }

    public List<Provider> getActieveProviders() {
        return verwerkQuery(stringActieveProviders);
    }

    public List<Provider> getProviders() {
        return verwerkQuery(stringProviders);
    }
    private List<Provider> verwerkQuery(String query){
        List<Provider> providers = new ArrayList<>();
        ResultSet rs = null;
        try {
            statProviders = connector.getConnection().prepareStatement(query);
            rs = statProviders.executeQuery();

            while (rs.next()) {
                providers.add(new Provider(rs.getInt("id"), rs.getString("naam"), rs.getBoolean("is_active")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statProviders.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        return providers;
    }

    public void setOptimaleReistijden(int provider_id, Map<Integer, Integer> optimaleReistijden){
        try{
            statProviders = connector.getConnection().prepareStatement(stringDeleteOptimaleReistijden);
            statProviders.setInt(1, provider_id);
            statProviders.execute();
        } catch (SQLException e) {
            logger.error("Verwijderen van de optimale reistijden van provider met id " + provider_id + " is mislukt...");
            logger.error(e);
        }finally {
            try {
                statProviders.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
        try{
            statProviders = connector.getConnection().prepareStatement(stringInsertOptimaleReistijden);
            statProviders.setInt(1, provider_id);
            for(Integer traject_id : optimaleReistijden.keySet()){
                statProviders.setInt(2, traject_id);
                statProviders.setInt(3, optimaleReistijden.get(traject_id));
                statProviders.addBatch();
            }
            statProviders.executeBatch();
        } catch (SQLException e) {
            logger.error("Toevoegen van de optimale reistijden van provider met id " + provider_id + " is mislukt...");
            logger.error(e);
        }finally {
            try {
                statProviders.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
    }
 }
