package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Meting;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
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
    private static final Logger logger = LogManager.getLogger(MetingRepository.class);

    private PreparedStatement statMetingenPerProvPerTraj = null;
    private PreparedStatement statMetingen = null;
    private PreparedStatement statAddMetingen = null;

    private String stringMetingenPerProvPerTraj = "select * from metingen where traject_id = ? and provider_id = ?";
    private String stringMetingen = "select * from metingen";
    private String stringAddMetingen = "insert into metingen(provider_id,traject_id,reistijd,optimal) values (?, ?, ?, ?)";

    public MetingRepository() {
        connector = new DBConnector();
        try {
            statMetingenPerProvPerTraj = connector.getConnection().prepareStatement(stringMetingenPerProvPerTraj);
            statMetingen = connector.getConnection().prepareStatement(stringMetingen);
            statAddMetingen = connector.getConnection().prepareStatement(stringAddMetingen);
        } catch (SQLException e) {
            logger.error("Verbinden met de databank is mislukt");
        }
    }

    public List<Meting> getMetingen(Provider provider, Traject traject) {
        List<Meting> metingen = new ArrayList<>();

        try {
            statMetingenPerProvPerTraj.setInt(1,traject.getId());
            statMetingenPerProvPerTraj.setInt(2,provider.getId());

            ResultSet rs = statMetingenPerProvPerTraj.executeQuery();

            while (rs.next()) {
                int reistijd = rs.getInt("reistijd");
                int optimal = rs.getInt("optimal");
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                metingen.add(new Meting(provider, traject, reistijd, optimal, timestamp));
            }

            return metingen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Meting> getMetingen() {
        ProviderRepository pcrud = new ProviderRepository();
        TrajectRepository tcrud = new TrajectRepository();
        List<Meting> metingen = new ArrayList<Meting>();

        try {
            ResultSet rs = statMetingen.executeQuery();

            while (rs.next()) {
                int reistijd = rs.getInt("reistijd");
                int optimal = rs.getInt("optimal");
                Provider provider = pcrud.getProvider(rs.getInt("provider_id"));
                Traject traject = tcrud.getTraject(rs.getInt("traject_id"));
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                metingen.add(new Meting(provider, traject, reistijd, optimal, timestamp));
            }

            return metingen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMeting(Meting meting) {
         try {
            statAddMetingen.setInt(1, meting.getProvider().getId());
            statAddMetingen.setInt(2, meting.getTraject().getId());
            statAddMetingen.setInt(3, meting.getReistijd());
            statAddMetingen.setInt(4, meting.getOptimale_reistijd());

            statAddMetingen.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
