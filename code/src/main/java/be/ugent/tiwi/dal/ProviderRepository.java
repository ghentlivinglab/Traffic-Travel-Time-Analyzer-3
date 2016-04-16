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
public class ProviderRepository implements IProviderRepository {
    private static final Logger logger = LogManager.getLogger(ProviderRepository.class);
    private final DBConnector connector;
    private PreparedStatement statProviders = null;

    public ProviderRepository() {
        connector = new DBConnector();
    }

    /**
     * Haalt de provider op met ene bepaalde naam
     * @param naam De naam van de op te halen provider
     * @return De provider met de gevraagde naam
     */
    @Override
    public Provider getProvider(String naam) {
        ResultSet rs = null;
        try {
            String stringProviderNaam = "select * from providers where naam = ?";
            statProviders = connector.getConnection().prepareStatement(stringProviderNaam);
            statProviders.setString(1, naam);
            rs = statProviders.executeQuery();

            while (rs.next()) {
                String naam_in_tabel = rs.getString("naam");
                if (naam_in_tabel.equals(naam)) {
                    return new Provider(rs.getInt("id"), naam_in_tabel, rs.getBoolean("is_active"));
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

    /**
     * Haalt de provider op met ene bepaalde identificatie
     * @param id De identificatie van de op te halen provider
     * @return De provider met de identificatie naam
     */
    @Override
    public Provider getProvider(int id) {
        ResultSet rs = null;
        try {
            String stringProviderId = "select * from providers where id = ?";
            statProviders = connector.getConnection().prepareStatement(stringProviderId);
            statProviders.setInt(1, id);
            rs = statProviders.executeQuery();

            while (rs.next()) {
                int id_in_tabel = rs.getInt("id");
                if (id_in_tabel == id) {
                    return new Provider(rs.getInt("id"), rs.getString("naam"), rs.getBoolean("is_active"));
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

    /**
     * Haalt alle actieve providers op. Bij deze providers staat de flag isActive op 1.
     * @return Een lijst met de actieve providers
     */
    @Override
    public List<Provider> getActieveProviders() {
        String stringActieveProviders = "select * from providers where is_active = 1 order by naam";
        return verwerkQuery(stringActieveProviders);
    }

    @Override
    public List<Provider> getProviders() {
        String stringProviders = "select * from providers order by naam";
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

    @Override
    public void setOptimaleReistijden(int provider_id, Map<Integer, Integer> optimaleReistijden){
        try{
            String stringDeleteOptimaleReistijden = "delete from optimale_reistijden where provider_id = ?";
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
            String stringInsertOptimaleReistijden = "insert into optimale_reistijden(provider_id, traject_id, reistijd) values (?, ?, ?)";
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
