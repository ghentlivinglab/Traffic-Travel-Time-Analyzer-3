package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JelleDeBock on 23/02/16.
 */
public class MetingRepository {
    private DBConnector connector;

    public MetingRepository() {
        connector = new DBConnector();
    }

    public List<Meting> getMetingen(Provider provider, Traject traject) {
        String query = "select * from metingen where traject_id ='" + traject.getId() + "' and provider_id = '" +
                provider.getId() + "'";
        List<Meting> metingen = new ArrayList<Meting>();
        ProviderRepository providerRepository = new ProviderRepository();
        TrajectRepository trajectRepository = new TrajectRepository();

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int reistijd = rs.getInt("reistijd");
                int optimal = rs.getInt("optimal");
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                metingen.add(new Meting(provider, traject, reistijd, optimal, timestamp));
            }

            connector.closeConnection();

            return metingen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Meting> getMetingen() {
        String query = "select * from metingen";
        ProviderRepository pcrud = new ProviderRepository();
        TrajectRepository tcrud = new TrajectRepository();
        List<Meting> metingen = new ArrayList<Meting>();

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int reistijd = rs.getInt("reistijd");
                int optimal = rs.getInt("optimal");
                Provider provider = pcrud.getProvider(rs.getInt("provider_id"));
                Traject traject = tcrud.getTraject(rs.getInt("traject_id"), rs.getBoolean("omgekeerd"));
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                metingen.add(new Meting(provider, traject, reistijd, optimal, timestamp));
            }

            connector.closeConnection();
            return metingen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMeting(Meting meting) {
        String query = "insert into metingen(provider_id,traject_id,omgekeerd,reistijd,optimal) values ("
                + meting.getProvider().getId() + ","
                + meting.getTraject().getId() + ","
                + meting.getTraject().is_omgekeerd() + ","
                + meting.getReistijd() + ","
                + meting.getOptimale_reistijd()
                + ")";

        try {
            Statement stmt = connector.getConnection().createStatement();
            stmt.executeUpdate(query);
            connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
