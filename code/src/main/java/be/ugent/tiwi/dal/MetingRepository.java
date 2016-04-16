package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetingRepository implements IMetingRepository {
    private static final Logger logger = LogManager.getLogger(MetingRepository.class);
    private DBConnector connector;
    private PreparedStatement statMetingen = null;

    /**
     * Constructor van de klasse.
     */
    public MetingRepository() {
        connector = new DBConnector();
    }

    /**
     * Deze methode geeft alle metingen terug gemaakt door een provider van een traject.
     *
     * @param provider De provider waarvan je de metingen wil opvragen
     * @param traject  Het traject waarvan je de metingen wil opvragen
     * @return Een lijst van metingen
     * @throws SQLException Indien de databank niet beschikbaar is of als de query niet geldig is
     * @see Meting
     */
    @Override
    public List<Meting> getMetingen(Provider provider, Traject traject) throws SQLException {
        List<Meting> metingen = new ArrayList<>();
        ResultSet rs = null;
        try {
            String stringMetingenPerProvPerTraj = "select * from metingen where traject_id = ? and provider_id = ?";
            statMetingen = connector.getConnection().prepareStatement(stringMetingenPerProvPerTraj);
            statMetingen.setInt(1, traject.getId());
            statMetingen.setInt(2, provider.getId());

            rs = statMetingen.executeQuery();

            while (rs.next()) {
                Integer reistijd = rs.getInt("reistijd");
                if(rs.wasNull())
                    reistijd = null;
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                metingen.add(new Meting(provider, traject, reistijd, timestamp));
            }

            return metingen;

        } catch (SQLException e) {
            logger.error(e);
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statMetingen.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    /**
     * Geeft alle metingen terug die in de database aanwezig zijn.
     *
     * @return Een lijst van metingen.
     * @see Meting
     * @param provider_id
     * @param traject_id
     */
    @Override
    public List<Meting> getMetingen(int provider_id, int traject_id) {
        IProviderRepository pcrud = new ProviderRepository();
        ITrajectRepository tcrud = new TrajectRepository();
        List<Meting> metingen = new ArrayList<Meting>();
        ResultSet rs = null;
        try {
            String stringMetingen = "select * from metingen where provider_id = ? and traject_id = ?";
            statMetingen = connector.getConnection().prepareStatement(stringMetingen);
            statMetingen.setInt(1, provider_id);
            statMetingen.setInt(2, traject_id);
            rs = statMetingen.executeQuery();

            while (rs.next()) {
                Integer reistijd = rs.getInt("reistijd");
                if(rs.wasNull())
                    reistijd = null;
                Provider provider = pcrud.getProvider(rs.getInt("provider_id"));
                Traject traject = tcrud.getTraject(rs.getInt("traject_id"));
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                metingen.add(new Meting(provider, traject, reistijd, timestamp));
            }
            return metingen;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statMetingen.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (Exception e) { /* ignored */ }
        }

        return null;
    }

    /**
     * Geeft alle metingen van een meegegeven traject terug.
     *
     * @param traject_id Het ID van het traject
     * @return Een lijst van metingen
     * @see Meting
     */
    @Override
    public List<Meting> getMetingenFromTraject(int traject_id) {
        String query = "select * from metingen where traject_id ='" + traject_id + "'";

        return getMetingenFromQuery(query, traject_id);
    }

    /**
     * Geeft alle metingen van een meegegeven traject terug binnen een tijdspanne
     *
     * @param traject_id Het ID van je traject
     * @param start      De begintijd van de tijdspanne
     * @param end        De eindtijd van de tijdspanne
     * @return Een lijst van metingen
     * @see Meting
     */
    @Override
    public List<Meting> getMetingenFromTraject(int traject_id, LocalDateTime start, LocalDateTime end) {
        String query = "select * from metingen where traject_id ='" + traject_id + "' and timestamp between '" +
                Timestamp.valueOf(start) + "' and '" + Timestamp.valueOf(end) + "'";

        return getMetingenFromQuery(query, traject_id);
    }

    /**
     * Verwerkt een meegegeven query die een traject_id als parameter verwacht en geeft een lijst van metingen terug.
     *
     * @param query      De query die uitgevoerd moet worden
     * @param traject_id De parameter traject_id
     * @return Een lijst van metingen
     * @see Meting
     */
    private List<Meting> getMetingenFromQuery(String query, int traject_id) {
        List<Meting> metingen = new ArrayList<>();
        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Traject traject = new TrajectRepository().getTraject(traject_id);

            while (rs.next()) {
                int provider_id = rs.getInt("provider_id");
                Provider provider = new ProviderRepository().getProvider(provider_id);
                Integer reistijd = rs.getInt("reistijd");
                if(rs.wasNull())
                    reistijd = null;
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                metingen.add(new Meting(provider, traject, reistijd, timestamp));
            }

            connector.close();
            return metingen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Geeft een lijst terug van providers. Iedere provider bevat een volledig ingevulde lijst van metingen van het
     * meegegeven traject
     *
     * @return Een lijst van providers, aangevuld met een lijst metingen per provider.
     * @see Meting
     */
    public List<Provider> getMetingenFromTrajectByProvider(int traject_id) {
        List<Provider> providers = new ProviderRepository().getActieveProviders();

        for (Provider provider : providers) {
            List<Meting> metingen = getMetingen(provider.getId(), traject_id);
            provider.setMetingen(metingen);
        }

        return providers;
    }

    /**
     * Geeft alle metingen van 1 provider terug
     *
     * @param id Het ID van de provider
     * @return Een lijst van metingen
     * @see Meting
     */
    @Override
    public List<Meting> getMetingenByProvider(int id) {
        List<Meting> provider_metingen = new ArrayList<>();
        Provider provider = new ProviderRepository().getProvider(id);

        String query = "select * from metingen where provider_id = " + id;

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);


            ITrajectRepository trajrepo = new TrajectRepository();
            while (rs.next()) {
                Traject traject = trajrepo.getTraject(rs.getInt("traject_id"));
                Integer reistijd = rs.getInt("reistijd");
                if(rs.wasNull())
                    reistijd = null;
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                provider_metingen.add(new Meting(provider, traject, reistijd, timestamp));
            }

            connector.close();
            return provider_metingen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Meting> getLaatsteMetingenByProvider(int id) {
        List<Meting> provider_metingen = new ArrayList<>();
        Provider provider = new ProviderRepository().getProvider(id);
        String query = "select m.traject_id, m.reistijd, m.timestamp from metingen m " +
                "join trajecten t on m.traject_id = t.id " +
                "where m.provider_id = ? AND " +
                "m.timestamp = (select max(m2.timestamp) from metingen m2 " +
                "where m2.traject_id = m.traject_id " +
                "and m2.provider_id = m.provider_id) " +
                "order by m.traject_id";
        try {
            PreparedStatement stmt = connector.getConnection().prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            ITrajectRepository trajrepo = new TrajectRepository();
            while (rs.next()) {
                Traject traject = trajrepo.getTraject(rs.getInt("traject_id"));
                Integer reistijd = rs.getInt("reistijd");
                if(rs.wasNull())
                    reistijd = null;
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                provider_metingen.add(new Meting(provider, traject, reistijd, timestamp));
            }

            connector.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provider_metingen;
    }

    /**
     * Geeft alle providers terug waarvoor al minstens 1 keer gescraped is
     *
     * @return Een lijst van providers
     * @see Provider
     */
    @Override
    public List<Provider> getGebruikteProviders() {
        List<Provider> providers = new ArrayList<>();
        String query = "select m.provider_id from metingen m " +
                "join providers p on m.provider_id = p.id " +
                "where m.reistijd is not null " +
                "group by m.provider_id, p.naam " +
                "order by p.naam";
        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);


            while (rs.next()) {
                IProviderRepository repo = new ProviderRepository();
                Provider p = repo.getProvider(rs.getInt("provider_id"));

                providers.add(p);
            }

            connector.close();
            return providers;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Voegt 1 meting toe aan de database
     *
     * @param meting De meting die toegevoegd moet worden
     * @see Meting
     */
    @Override
    public void addMeting(Meting meting) {
        try {
            String stringAddMetingen = "insert into metingen(provider_id,traject_id,reistijd) values (?, ?, ?)";
            statMetingen = connector.getConnection().prepareStatement(stringAddMetingen);
            statMetingen.setInt(1, meting.getProvider().getId());
            statMetingen.setInt(2, meting.getTraject().getId());
            if (meting.getReistijd() != null && meting.getReistijd() >= 0)
                statMetingen.setInt(3, meting.getReistijd());
            else
                statMetingen.setNull(3, Types.INTEGER);
            statMetingen.executeUpdate();


        } catch (SQLException ex) {
            logger.error("Toevoegen meting mislopen", ex);
        }finally{
             try { statMetingen.close(); } catch (Exception e) { /* ignored */ }
             try {  connector.close(); } catch (Exception e) { /* ignored */ }
         }
    }

    /**
     * Deze functie berekent de gemiddelde vertraging voor een bepaald traject voor een bepaalde provider.
     *
     * @param traject_id het id van het traject (zoals is databank opgeslaan)
     * @param provider_id het id van de provider (zoals is databank opgeslaan)
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return Het object dat geretourneerd wordt is van de klasse @class ProviderTrajectStatistiek. Per metingstatistiek oproep wordt slechts 1 object
     * geretourneerd. Het @class ProviderTrajectStatistiek object bevat de gemiddelde vertraging, het traject (als object) en de provider (als object).
     */
    @Override
    public ProviderTrajectStatistiek metingStatistieken(int traject_id, int provider_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        String stringStatistieken = "SELECT avg(m1.reistijd-o.reistijd) avg_vertraging " +
                "FROM metingen m1 " +
                "JOIN optimale_reistijden o ON m1.traject_id = o.traject_id AND m1.provider_id = o.provider_id " +
                "WHERE m1.traject_id = ? AND m1.provider_id = ? AND m1.timestamp BETWEEN ? AND ? AND m1.reistijd IS NOT NULL";

        try {
            statMetingen = connector.getConnection().prepareStatement(stringStatistieken);

            statMetingen.setInt(1, traject_id);
            statMetingen.setInt(2, provider_id);
            statMetingen.setTimestamp(3, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(4, Timestamp.valueOf(end_tijdstip));

            ProviderTrajectStatistiek providerTrajectStatistiek;

            ResultSet rs = statMetingen.executeQuery();

            if (rs.next()) {
                Traject traject = new TrajectRepository().getTraject(traject_id);
                Provider provider = new ProviderRepository().getProvider(provider_id);
                Vertraging vertraging = new Vertraging(traject, rs.getDouble("avg_vertraging"));

                providerTrajectStatistiek = new ProviderTrajectStatistiek(provider, vertraging);

                return providerTrajectStatistiek;

            }

            connector.close();

            return null;
        }catch (SQLException ex) {
            logger.error("Statistieken ophalen mislukt", ex);
        }
        return null;
    }

    @Override
    public int getOptimaleReistijdLaatste7Dagen(int trajectId, int providerId) {
        String query = "select count(*) total, now() now from metingen " +
                "where provider_id = ? and traject_id = ? and reistijd is not null " +
                "and timestamp between date_sub(now(), INTERVAL 7 DAY) and now()";
        ResultSet rs = null;
        PreparedStatement stat = null;
        try {
            stat = connector.getConnection().prepareStatement(query);
            stat.setInt(1, providerId);
            stat.setInt(2, trajectId);
            rs = stat.executeQuery();

            if (rs.next()) {
                double total = rs.getInt("total");
                if (total > 0) {
                    int percentileVal = (int) Math.round(total * 0.05);
                    Timestamp now = rs.getTimestamp("now");
                    rs.close();
                    stat.close();

                    query = "select reistijd from metingen " +
                            "where provider_id = ? and traject_id = ? and reistijd is not null " +
                            "and timestamp between date_sub(?, INTERVAL 7 DAY) and ? " +
                            "order by reistijd asc limit ?,1";
                    stat = connector.getConnection().prepareStatement(query);
                    stat.setInt(1, providerId);
                    stat.setInt(2, trajectId);
                    stat.setTimestamp(3, now);
                    stat.setTimestamp(4, now);
                    stat.setInt(5, percentileVal);
                    rs = stat.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("reistijd");
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt", e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                stat.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (SQLException e) { /* ignored */ }
        }
        return -1;
    }


    /**
     * Berekent de gemiddelde vertraging in Gent. Het geeft dus een vertraging voor alle trajecten rekening houdende met alle providers.
     * Wordt berekend door:
     *  1: Berekent de gemiddelde, algemene reistijd voor de periode (rekening houdend met frequenties en null-values).
     *  2: Berekent de gemiddelde, algemene optimale reistijd (houdt ook rekening met de frequenties van de metingen en null-values).
     *  3: De gemiddelde vertraging bedraagt verschil van deze twee gemiddelden.
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return de waarde van de gemiddelde vertraging (als double)
     */
    @Override
    public double gemiddeldeVertraging(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        String gemiddelde_vertraging = "select avg(m.reistijd - o.reistijd) totale_vertraging " +
                "from metingen m " +
                "join optimale_reistijden o on m.provider_id = o.provider_id and m.traject_id = o.traject_id " +
                "WHERE m.reistijd IS NOT NULL " +
                "AND m.timestamp BETWEEN ? AND ?";
        ResultSet rs = null;
        try {
            statMetingen = connector.getConnection().prepareStatement(gemiddelde_vertraging);
            statMetingen.setTimestamp(1, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(2, Timestamp.valueOf(end_tijdstip));
            rs = statMetingen.executeQuery();

            if (rs.next()) {
                return rs.getDouble("totale_vertraging");
            } else {
                throw new SQLException("Oeps... We hebben geen algemene vertraging kunnen bepalen.");
            }
        } catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                connector.close();
            } catch (SQLException e) { /* ignored */ }
        }
        return -1;
    }

    /**
     * Berekent de gemiddelde vertraging in Gent voor een bepaalde provider. Het geeft dus een vertraging voor alle trajecten rekening houdende de gewenste provider.
     *
     * @param provider de provider waarvoor we de gemiddelde vertraging wensen te berekenen
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return de waarde van de gemiddelde vertraging (als double)
     */
    @Override
    public double gemiddeldeVertraging(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        String gemiddelde_vertraging_provider = "select avg(m.reistijd-o.reistijd) totale_vertraging " +
                "from metingen m " +
                "join optimale_reistijden o on o.traject_id = m.traject_id and o.provider_id = m.provider_id "+
                "where m.timestamp between ? and ? and m.provider_id = ? and m.reistijd is not null ";
        try {
            statMetingen = connector.getConnection().prepareStatement(gemiddelde_vertraging_provider);
            statMetingen.setTimestamp(1,Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(2,Timestamp.valueOf(end_tijdstip));
            statMetingen.setInt(3,provider.getId());
            ResultSet rs = statMetingen.executeQuery();

            if(rs.next()) {
                return rs.getDouble("totale_vertraging");
            }else{
                throw new SQLException("Oeps... We hebben geen algemene vertraging kunnen bepalen.");
            }
        }catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return -1;
    }

    public Map<LocalDateTime, Double> getVertragingenEveryFiveMinutes(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip){
        String gemiddeldeVertragingPeriode = "select max(b.max) timestamp, sum(b.count * b.avg) / sum(b.count) gemiddelde, sum(b.count) aantal " +
                "from ( " +
                    "SELECT    round((a.timestamp - max(m.timestamp))/(5 * 60)) id, max(m.timestamp) max, avg(m.reistijd - o.reistijd) avg, count(*) count " +
                    "FROM     metingen m " +
                    "join optimale_reistijden o on o.traject_id = m.traject_id and o.provider_id = m.provider_id " +
                    ", (" +
                        "select max(m2.timestamp) timestamp " +
                        "from metingen m2 " +
                        "where m2.timestamp between ? and ? and m2.reistijd is not null and m2.provider_id = ? " +
                    ") a " +
                    "where m.timestamp between ? and ? and m.reistijd is not null and m.provider_id = ? " +
                    "GROUP BY ROUND(UNIX_TIMESTAMP(m.timestamp)/(5 * 60)) " +
                ") b " +
                "group by b.id " +
                "order by timestamp";
        try{
            statMetingen = connector.getConnection().prepareStatement(gemiddeldeVertragingPeriode);
            statMetingen.setTimestamp(1, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(2, Timestamp.valueOf(end_tijdstip));
            statMetingen.setInt(3, provider.getId());
            statMetingen.setTimestamp(4, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(5, Timestamp.valueOf(end_tijdstip));
            statMetingen.setInt(6, provider.getId());
            ResultSet rs = statMetingen.executeQuery();

            Map<LocalDateTime, Double> map = new HashMap<LocalDateTime, Double>();
            while(rs.next()){
                Timestamp timestamp = rs.getTimestamp("timestamp");
                Double vertraging = rs.getDouble("gemiddelde");
                map.put(timestamp.toLocalDateTime(), vertraging);
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<LocalDateTime, Double> getVertragingenEveryFiveMinutes(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip){
        String gemiddeldeVertragingPeriode = "select max(b.max) timestamp, sum(b.count * b.avg) / sum(b.count) gemiddelde, sum(b.count) aantal " +
                "from ( " +
                "SELECT    round((a.timestamp - max(m.timestamp))/(5 * 60)) id, max(m.timestamp) max, avg(m.reistijd - o.reistijd) avg, count(*) count " +
                "FROM     metingen m " +
                "join optimale_reistijden o on o.traject_id = m.traject_id and o.provider_id = m.provider_id " +
                ", (" +
                "select max(m2.timestamp) timestamp " +
                "from metingen m2 " +
                "where m2.timestamp between ? and ? and m2.reistijd is not null " +
                ") a " +
                "where m.timestamp between ? and ? and m.reistijd is not null " +
                "GROUP BY ROUND(UNIX_TIMESTAMP(m.timestamp)/(5 * 60)) " +
                ") b " +
                "group by b.id " +
                "order by timestamp";
        try{
            statMetingen = connector.getConnection().prepareStatement(gemiddeldeVertragingPeriode);
            statMetingen.setTimestamp(1, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(2, Timestamp.valueOf(end_tijdstip));
            statMetingen.setTimestamp(3, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(4, Timestamp.valueOf(end_tijdstip));
            ResultSet rs = statMetingen.executeQuery();

            Map<LocalDateTime, Double> map = new HashMap<LocalDateTime, Double>();
            while(rs.next()){
                Timestamp timestamp = rs.getTimestamp("timestamp");
                Double vertraging = rs.getDouble("gemiddelde");
                map.put(timestamp.toLocalDateTime(), vertraging);
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deze functie berekent de gemiddelde reistijdvertraging van alle trajecten voor een specifieke provider.
     *
     * @param provider_id de provider waarvan de gemiddelde reistijd bepaald moet worden (id uit databank)
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return Deze functie retourneert 1 enkel object van het type ProviderStatistiek. Het bevat de gemiddelde vertraging alsook de provider in kwestie
     * (als object)
     */
    @Override
    public ProviderStatistiek metingProviderStatistieken(int provider_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        String gemiddelde_per_traject_provider = "select m1.traject_id, traj.naam, avg(m1.reistijd-o.reistijd) avg_vertraging " +
                "from metingen m1 " +
                "join optimale_reistijden o on m1.traject_id = o.traject_id and m1.provider_id = o.provider_id " +
                "join trajecten traj on traj.id = m1.traject_id " +
                "where m1.provider_id = ? and m1.timestamp between ? and ? and m1.reistijd is not null " +
                "group by m1.traject_id";
        try {
            statMetingen = connector.getConnection().prepareStatement(gemiddelde_per_traject_provider);
            statMetingen.setInt(1, provider_id);
            statMetingen.setTimestamp(2, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(3, Timestamp.valueOf(end_tijdstip));

            Provider provider = new ProviderRepository().getProvider(provider_id);
            ProviderStatistiek stat = new ProviderStatistiek(provider);
            ResultSet rs = statMetingen.executeQuery();

            while (rs.next()) {
                Traject traject = new TrajectRepository().getTraject(rs.getInt("traject_id"));
                double avg_vertraging = rs.getDouble("avg_vertraging");
                stat.addVertraging(new Vertraging(traject, avg_vertraging));
            }

            return stat;
        } catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return null;
    }

    /**
     * Deze functie berekent voor een gewenst traject de gemiddelde vertraging (over alle providers uitgemiddeld)
     *
     * @param traject_id het traject waarvoor de gemiddelde vertraging bepaald dient te worden
     * @param start_tijdstip het starttijdstip vanaf waar het gemiddelde berekend moet worden
     * @param end_tijdstip het eindtijdstip tot waar het gemiddelde berekend moet worden
     *
     * @return Deze functie retourneert 1 enkel object van het type Vertraging deze gevat de gemiddelde vertraging en het traject (als object)
     */
    @Override
    public Vertraging metingTrajectStatistieken(int traject_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip){
        String traject_vertraging = "select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging"+
        " from metingen m1"+
        " join metingen m2 on m1.traject_id = m2.traject_id"+
        " join trajecten traj on traj.id = m2.traject_id"+
        " where m1.traject_id = ? and m1.timestamp between ? and ? and m1.reistijd is not null"+
        " group by m1.traject_id";

        try {
            statMetingen = connector.getConnection().prepareStatement(traject_vertraging);
            statMetingen.setInt(1, traject_id);
            statMetingen.setTimestamp(2, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(3, Timestamp.valueOf(end_tijdstip));

            Traject traject = new TrajectRepository().getTraject(traject_id);


            ResultSet rs = statMetingen.executeQuery();

            if (rs.next()) {

                return new Vertraging(traject, rs.getDouble("avg_vertraging"));
            }
        } catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return null;
    }

    /**
     * Zoekt het drukste traject voor een bepaalde tijdsinterval, geeft een Vertraging object terug
     *
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moet worden
     * @param end_tijdstip   het eind tijdstip tot waar gezocht moet worden
     * @return drukste tijdstip
     */
    @Override
    public Vertraging getDrukstePunt(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        String trajecten_vertraging = "select m1.traject_id, avg(m1.reistijd-o.reistijd) avg_vertraging " +
                "from metingen m1 " +
                "join optimale_reistijden o on o.traject_id = m1.traject_id and o.provider_id = m1.provider_id " +
                "where m1.timestamp between ? and ? and m1.reistijd is not null " +
                "group by m1.traject_id";

        try {
            statMetingen = connector.getConnection().prepareStatement(trajecten_vertraging);
            statMetingen.setTimestamp(1, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(2, Timestamp.valueOf(end_tijdstip));

            ResultSet rs = statMetingen.executeQuery();
            Vertraging drukste_traject = null;
            if (rs.next()) {
                drukste_traject = new Vertraging(new TrajectRepository().getTraject(rs.getInt("traject_id")), rs.getDouble("avg_vertraging"));
                while (rs.next()) {
                    double avg_vertraging = rs.getDouble("avg_vertraging");
                    if (avg_vertraging > drukste_traject.getAverageVertraging())
                        drukste_traject.setTraject(new TrajectRepository().getTraject(rs.getInt("traject_id")));
                    drukste_traject.setAverageVertraging(avg_vertraging);
                }
            }
            return drukste_traject;
        } catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return null;
    }

    /**
     * Berekent het drukste punt, maar dan voor de meting van een specifieke provider
     *
     * @param provider De provider waarvan het drukste punt gewenst is
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moeten
     * @param end_tijdstip het eind tijdstip tot waar gezocht moeten
     *
     * @return het drukste punt voor die provider
     */
    @Override
    public Vertraging getDrukstePunt(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip){
        String trajecten_vertraging_provider = "select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging" +
                "        from metingen m1" +
                "        join metingen m2 on m1.traject_id = m2.traject_id" +
                "        join trajecten traj on traj.id = m2.traject_id" +
                "        where m1.timestamp between ? and ? and m1.provider_id = ? and m1.reistijd is not null" +
                "        group by m1.traject_id";

        try {
            statMetingen = connector.getConnection().prepareStatement(trajecten_vertraging_provider);
            statMetingen.setTimestamp(1,Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(2,Timestamp.valueOf(end_tijdstip));
            statMetingen.setInt(3,provider.getId());

            ResultSet rs = statMetingen.executeQuery();
            Vertraging drukste_traject = null;
            if(rs.next()) {
                drukste_traject =new Vertraging(new TrajectRepository().getTraject(rs.getInt("traject_id")),rs.getDouble("avg_vertraging"));
                while (rs.next()){
                    double avg_vertraging = rs.getDouble("avg_vertraging");
                    if(avg_vertraging>drukste_traject.getAverageVertraging())
                        drukste_traject.setTraject(new TrajectRepository().getTraject(rs.getInt("traject_id")));
                    drukste_traject.setAverageVertraging(avg_vertraging);
                }
            }
            return drukste_traject;
        }catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return null;
    }

    /**
     * Genereert een handig overzicht van alle trajecten, met de bijhorende gemiddelde vertragingen over het meegegeven tijdsinterval.
     *
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moeten
     * @param end_tijdstip   het eind tijdstip tot waar gezocht moeten
     * @return alle trajecten die een vertraging hebben over dat tijdstip
     */
    @Override
    public List<Vertraging> getVertragingen(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        String trajecten_vertraging = "select m1.traject_id, avg(m1.reistijd - o.reistijd) avg_vertraging " +
                "from metingen m1 " +
                "join optimale_reistijden o on o.provider_id = m1.provider_id and o.traject_id = m1.traject_id " +
                "where m1.timestamp between ? and ? and m1.reistijd is not null " +
                "group by m1.traject_id";

        try {
            statMetingen = connector.getConnection().prepareStatement(trajecten_vertraging);
            statMetingen.setTimestamp(1, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(2, Timestamp.valueOf(end_tijdstip));

            ResultSet rs = statMetingen.executeQuery();
            List<Vertraging> vertragingen = new ArrayList<>();

            while (rs.next()) {
                double avg_vertraging = rs.getDouble("avg_vertraging");
                Traject traject = new TrajectRepository().getTrajectMetWaypoints(rs.getInt("traject_id"));
                vertragingen.add(new Vertraging(traject, avg_vertraging));
            }

            return vertragingen;
        } catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return null;
    }

    /**
     * Genereert een handig overzicht van alle trajecten, met de bijhorende gemiddelde vertragingen over het meegegeven tijdsinterval.
     * Deze functie doet dit voor 1 bepaalde provider.
     *
     * @param provider de provider waarvoor we de gemiddelde vertragingen wensen te berekenen
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moeten
     * @param end_tijdstip het eind tijdstip tot waar gezocht moeten
     *
     * @return alle trajecten die een vertraging hebben over dat tijdstip
     */
    @Override
    public List<Vertraging> getVertragingen(Provider provider, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip){
        String trajecten_vertraging = "select m1.traject_id, avg(m1.reistijd-o.reistijd) avg_vertraging " +
                "from metingen m1 " +
                "join optimale_reistijden o on m1.traject_id = o.traject_id and m1.provider_id = o.provider_id " +
                "where m1.timestamp between ? and ? and m1.provider_id = ? and m1.reistijd is not null " +
                "group by m1.traject_id";

        try {
            statMetingen = connector.getConnection().prepareStatement(trajecten_vertraging);
            statMetingen.setTimestamp(1, Timestamp.valueOf(start_tijdstip));
            statMetingen.setTimestamp(2, Timestamp.valueOf(end_tijdstip));
            statMetingen.setInt(3,provider.getId());

            ResultSet rs = statMetingen.executeQuery();
            List<Vertraging> vertragingen = new ArrayList<>();

            while (rs.next()){
                double avg_vertraging = rs.getDouble("avg_vertraging");
                Traject traject = new TrajectRepository().getTrajectMetWaypoints(rs.getInt("traject_id"));
                vertragingen.add(new Vertraging(traject,avg_vertraging));
            }

            return vertragingen;
        } catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return null;
    }
}
