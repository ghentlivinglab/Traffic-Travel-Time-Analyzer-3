package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.TrafficIncident;
import be.ugent.tiwi.domein.Traject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeroen on 1/05/2016.
 */
public class IncidentRepository implements IIncidentRepository {
    private static final Logger logger = LogManager.getLogger(LoginRepository.class);
    private DBConnector connector;
    private PreparedStatement statIncident = null;

    /**
     * Constructor van de klasse.
     */
    public IncidentRepository() {
        connector = new DBConnector();
    }

    /**
     * Geeft een lijst van alle providers terug die trafficincidents gegenereerd hebben
     *
     * @return list providers
     */
    @Override
    public List<Provider> getProviders() {
        List<Provider> providers = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT providers.id, providers.naam, providers.is_active FROM providers " +
                    "JOIN trafficincidents on providers.id = trafficincidents.provider_id " +
                    "GROUP by providers.id";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String naam = rs.getString("naam");
                boolean is_active = rs.getInt("is_active") == 1 ? true : false;

                providers.add(new Provider(id, naam, is_active));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return providers;
    }

    /**
     * Geeft een lijst van alle providers terug die trafficincidents gegenereerd hebben tussen bepaalde data
     *
     * @param timestamp
     * @return
     */
    @Override
    public List<Provider> getProviders(LocalDateTime timestamp) {
        List<Provider> providers = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT providers.id, providers.naam, providers.is_active FROM providers " +
                    "JOIN trafficincidents on providers.id = trafficincidents.provider_id " +
                    "WHERE ? BETWEEN trafficincidents.starttime and trafficincidents.endtime " +
                    "GROUP by providers.id";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setTimestamp(1, Timestamp.valueOf(timestamp));

            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String naam = rs.getString("naam");
                boolean is_active = rs.getInt("is_active") == 1 ? true : false;

                providers.add(new Provider(id, naam, is_active));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return providers;
    }


    /**
     * Geeft een lijst terug van alle trajecten waar er ooit problemen geweest zijn
     *
     * @return
     */
    @Override
    public List<Traject> getTrajecten() {
        List<Traject> trajecten = new ArrayList<> ();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT trajecten.id, trajecten.naam, trajecten.lengte, trajecten.optimale_reistijd, trajecten.is_active, trajecten.start_latitude, trajecten.start_longitude, trajecten.end_latitude, trajecten.end_longitude FROM trajecten" +
                    " JOIN trafficincidents on trajecten.id = trafficincidents.traject_id" +
                    " GROUP by trajecten.id";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String naam = rs.getString("naam");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getInt("is_active") == 1 ? true : false;

                String start_latitude = rs.getString("start_latitude");
                String start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude");
                String end_longitude = rs.getString("end_longitude");

                trajecten.add(new Traject(id, naam, lengte, optimale_reistijd, null, is_active, start_latitude, start_longitude, end_latitude, end_longitude));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trajecten;
    }


    /**
     * Geeft een lijst terug van alle trajecten waar er problemen geweest zijn tussen bepaalde data
     *
     * @param timestamp
     * @return
     */
    @Override
    public List<Traject> getTrajecten(LocalDateTime timestamp) {
        List<Traject> trajecten = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT trajecten.id, trajecten.naam, trajecten.lengte, trajecten.optimale_reistijd, trajecten.is_active, trajecten.start_latitude, trajecten.start_longitude, trajecten.end_latitude, trajecten.end_longitude FROM trajecten" +
                    " JOIN trafficincidents on trajecten.id = trafficincidents.traject_id" +
                    " WHERE ? BETWEEN trafficincidents.starttime and trafficincidents.starttime" +
                    " GROUP by trajecten.id";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setTimestamp(1, Timestamp.valueOf(timestamp));
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String naam = rs.getString("naam");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getInt("is_active") == 1 ? true : false;

                String start_latitude = rs.getString("start_latitude");
                String start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude");
                String end_longitude = rs.getString("end_longitude");

                trajecten.add(new Traject(id, naam, lengte, optimale_reistijd, null, is_active, start_latitude, start_longitude, end_latitude, end_longitude));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trajecten;
    }

    /**
     * Geeft een lijst terug van alle trafficincidenten
     *
     * @return
     */
    @Override
    public List<TrafficIncident> getTrafficIncidents() {
        List<TrafficIncident> trafficIncidents = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT * FROM trafficincidents";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int provider_id = rs.getInt("provider_id");
                int traject_id = rs.getInt("traject_id");
                LocalDateTime startTime = rs.getTimestamp("starttime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("endtime").toLocalDateTime();
                String problem = rs.getString("problem");

                trafficIncidents.add(new TrafficIncident(id, new ProviderRepository().getProvider(provider_id), new TrajectRepository().getTraject(traject_id), startTime, endTime, problem));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidents;
    }

    /**
     * Geeft een lijst terug van alle trafficincidenten tussen bepaalde data
     *
     * @param timestamp
     * @return
     */
    @Override
    public List<TrafficIncident> getTrafficIncidents(LocalDateTime timestamp) {
        List<TrafficIncident> trafficIncidents = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT * FROM trafficincidents " +
                    "WHERE ? BETWEEN starttime and endtime";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setTimestamp(1, Timestamp.valueOf(timestamp));
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int provider_id = rs.getInt("provider_id");
                int traject_id = rs.getInt("traject_id");
                LocalDateTime startTime = rs.getTimestamp("starttime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("endtime").toLocalDateTime();
                String problem = rs.getString("problem");

                trafficIncidents.add(new TrafficIncident(id, new ProviderRepository().getProvider(provider_id), new TrajectRepository().getTraject(traject_id), startTime, endTime, problem));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidents;

    }

    /**
     * Geeft een lijst terug van alle trafficincidenten tussen bepaalde data van een bepaalde provider
     *
     * @param timestamp
     * @return
     */
    @Override
    public List<TrafficIncident> getTrafficIncidents(Provider provider, LocalDateTime timestamp) {
        List<TrafficIncident> trafficIncidents = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT * FROM trafficincidents " +
                    "WHERE provider_id = ? " +
                    "AND ? BETWEEN starttime and endtime";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setInt(1, provider.getId());
            statIncident.setTimestamp(2, Timestamp.valueOf(timestamp));
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int provider_id = rs.getInt("provider_id");
                int traject_id = rs.getInt("traject_id");
                LocalDateTime startTime = rs.getTimestamp("starttime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("endtime").toLocalDateTime();
                String problem = rs.getString("problem");

                trafficIncidents.add(new TrafficIncident(id, new ProviderRepository().getProvider(provider_id), new TrajectRepository().getTraject(traject_id), startTime, endTime, problem));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidents;

    }

    @Override
    public List<TrafficIncident> getTrafficIncidents(Traject traject) {
        List<TrafficIncident> trafficIncidents = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT * FROM trafficincidents " +
                    "WHERE traject_id = ?";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setInt(1, traject.getId());
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int provider_id = rs.getInt("provider_id");
                int traject_id = rs.getInt("traject_id");
                LocalDateTime startTime = rs.getTimestamp("starttime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("endtime").toLocalDateTime();
                String problem = rs.getString("problem");

                trafficIncidents.add(new TrafficIncident(id, new ProviderRepository().getProvider(provider_id), new TrajectRepository().getTraject(traject_id), startTime, endTime, problem));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidents;
    }

    @Override
    public List<TrafficIncident> getTrafficIncidents(Provider provider, Traject traject) {
        List<TrafficIncident> trafficIncidents = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT * FROM trafficincidents " +
                    "WHERE provider_id = ? " +
                    "AND traject_id = ?";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setInt(1, provider.getId());
            statIncident.setInt(2, traject.getId());
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int provider_id = rs.getInt("provider_id");
                int traject_id = rs.getInt("traject_id");
                LocalDateTime startTime = rs.getTimestamp("starttime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("endtime").toLocalDateTime();
                String problem = rs.getString("problem");

                trafficIncidents.add(new TrafficIncident(id, new ProviderRepository().getProvider(provider_id), new TrajectRepository().getTraject(traject_id), startTime, endTime, problem));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidents;
    }

    @Override
    public List<TrafficIncident> getTrafficIncidents(Provider provider) {
        List<TrafficIncident> trafficIncidents = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT * FROM trafficincidents " +
                    "WHERE provider_id = ?";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setInt(1, provider.getId());
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int provider_id = rs.getInt("provider_id");
                int traject_id = rs.getInt("traject_id");
                LocalDateTime startTime = rs.getTimestamp("starttime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("endtime").toLocalDateTime();
                String problem = rs.getString("problem");

                trafficIncidents.add(new TrafficIncident(id, new ProviderRepository().getProvider(provider_id), new TrajectRepository().getTraject(traject_id), startTime, endTime, problem));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidents;
    }

    /**
     * Geeft een lijst terug van alle trafficincidenten tussen bepaalde data van een bepaald traject
     *
     * @param timestamp
     * @return
     */
    @Override
    public List<TrafficIncident> getTrafficIncidents(Traject traject, LocalDateTime timestamp) {
        List<TrafficIncident> trafficIncidents = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT * FROM trafficincidents " +
                    "WHERE traject_id = ? " +
                    "AND ? BETWEEN starttime and endtime";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setInt(1, traject.getId());
            statIncident.setTimestamp(2, Timestamp.valueOf(timestamp));
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int provider_id = rs.getInt("provider_id");
                int traject_id = rs.getInt("traject_id");
                LocalDateTime startTime = rs.getTimestamp("starttime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("endtime").toLocalDateTime();
                String problem = rs.getString("problem");

                trafficIncidents.add(new TrafficIncident(id, new ProviderRepository().getProvider(provider_id), new TrajectRepository().getTraject(traject_id), startTime, endTime, problem));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidents;

    }


    /**
     * Geeft een lijst terug van alle trafficincidenten tussen bepaalde data van een bepaalde provider van een bepaald traject
     *
     * @param timestamp
     * @return
     */
    @Override
    public List<TrafficIncident> getTrafficIncidents(Provider provider, Traject traject, LocalDateTime timestamp) {
        List<TrafficIncident> trafficIncidents = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT * FROM trafficincidents " +
                    "WHERE provider_id = ? " +
                    "AND traject_id = ? " +
                    "AND ? BETWEEN starttime and endtime";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setInt(1, provider.getId());
            statIncident.setInt(2, traject.getId());
            statIncident.setTimestamp(3, Timestamp.valueOf(timestamp));
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int provider_id = rs.getInt("provider_id");
                int traject_id = rs.getInt("traject_id");
                LocalDateTime startTime = rs.getTimestamp("starttime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("endtime").toLocalDateTime();
                String problem = rs.getString("problem");

                trafficIncidents.add(new TrafficIncident(id, new ProviderRepository().getProvider(provider_id), new TrajectRepository().getTraject(traject_id), startTime, endTime, problem));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidents;

    }

    /**
     * Lijst van alle provider teruggeven die problemen over verkeersinfo hebben
     * @return
     */
    @Override
    public List<Provider> getTrafficIncidentsProviders() {
        List<Provider> trafficIncidentsProviders = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "select p.id, p.naam, p.is_active " +
                    "from providers p " +
                    "JOIN trafficincidents t ON p.id = t.provider_id " +
                    "group by p.id";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String naam = rs.getString("naam");
                boolean is_active = rs.getBoolean("is_active");

                trafficIncidentsProviders.add(new Provider(id, naam, is_active));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidentsProviders;

    }

    /**
     * Lijst van alle trajecten teruggeven die problemen hebben
     * @return
     */
    @Override
    public List<Traject> getTrafficIncidentsTrajecten() {
        List<Traject> trafficIncidentsTrajecten = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT t.id, t.naam, t.lengte, t.optimale_reistijd, " +
                    "t.is_active, t.start_latitude, t.start_longitude, t.end_latitude, t.end_longitude " +
                    "FROM trajecten t JOIN trafficincidents i " +
                    "on t.id = i.traject_id group by t.id";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            rs = statIncident.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String naam = rs.getString("naam");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getBoolean("is_active");
                String start_latitude = rs.getString("start_latitude");
                String start_longitude = rs.getString("start_longitude");
                String end_latitude = rs.getString("end_latitude");
                String end_longitude = rs.getString("end_longitude");

                trafficIncidentsTrajecten.add(new Traject(id, naam, lengte, optimale_reistijd, null, is_active, start_latitude, start_longitude, end_latitude, end_longitude));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
        return trafficIncidentsTrajecten;

    }

    /**
     * Controle indien een traject reeds in de database opgeslagen is
     *
     * @param trafficIncident
     */
    @Override
    public TrafficIncident getTrafficIncident(TrafficIncident trafficIncident) {
        TrafficIncident ti = null;
        ResultSet rs = null;
        try {
            String stringIncident = "SELECT * FROM trafficincidents WHERE provider_id = ?" +
                    " AND traject_id = ?" +
                    " AND starttime = ?" +
                    " AND problem = ?";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setInt(1, trafficIncident.getProvider().getId());
            statIncident.setInt(2, trafficIncident.getTraject().getId());
            statIncident.setTimestamp(3, Timestamp.valueOf(trafficIncident.getStartTime()));
            statIncident.setString(4, trafficIncident.getProblem());
            rs = statIncident.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                Provider p = trafficIncident.getProvider();
                Traject t = trafficIncident.getTraject();
                LocalDateTime startTime = trafficIncident.getStartTime();
                LocalDateTime endTime = trafficIncident.getEndTime();
                String problem = trafficIncident.getProblem();

                ti = new TrafficIncident(id, p, t, startTime, endTime, problem);
            }

        } catch (SQLException e) {
            logger.error("Toevoegen trafficIncident mislukt", e);
        } finally {
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }

        return ti;
    }

    /**
     * Toevoegen van een nieuw verkeersprobleem
     *
     * @param trafficIncident
     */
    @Override
    public void addTrafficIncident(TrafficIncident trafficIncident) {
        try {
            String stringIncident = "INSERT INTO trafficincidents(provider_id, traject_id, starttime, endtime, problem) VALUES(?,?,?,?,?)";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setInt(1, trafficIncident.getProvider().getId());
            statIncident.setInt(2, trafficIncident.getTraject().getId());
            statIncident.setTimestamp(3, Timestamp.valueOf(trafficIncident.getStartTime()));
            statIncident.setTimestamp(4, Timestamp.valueOf(trafficIncident.getEndTime()));
            statIncident.setString(5, trafficIncident.getProblem());
            statIncident.executeUpdate();

        } catch (SQLException e) {
            logger.error("Toevoegen trafficIncident mislukt", e);
        } finally {
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
    }

    /**
     * Aanpassen van een verkeersprobleem
     *
     * @param trafficIncident
     */
    @Override
    public void updateTrafficIncident(TrafficIncident trafficIncident) {
        try {
            String stringIncident = "UPDATE trafficincidents set provider_id = ?, traject_id = ?, starttime = ?, endtime = ?, problem = ? where id = ?";


            statIncident = connector.getConnection().prepareStatement(stringIncident);
            statIncident.setInt(1, trafficIncident.getProvider().getId());
            statIncident.setInt(2, trafficIncident.getTraject().getId());
            statIncident.setTimestamp(3, Timestamp.valueOf(trafficIncident.getStartTime()));
            statIncident.setTimestamp(4, Timestamp.valueOf(trafficIncident.getEndTime()));
            statIncident.setString(5, trafficIncident.getProblem());
            statIncident.setInt(6, trafficIncident.getId());
            statIncident.executeUpdate();

        } catch (SQLException e) {
            logger.error("Toevoegen trafficIncident mislukt", e);
        } finally {
            try {
                statIncident.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }

        }
    }
}
