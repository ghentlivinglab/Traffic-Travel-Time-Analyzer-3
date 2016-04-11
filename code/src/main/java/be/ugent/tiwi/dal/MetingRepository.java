package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
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
    private PreparedStatement statStatistieken = null;

    private String stringMetingenPerProvPerTraj = "select * from metingen where traject_id = ? and provider_id = ?";
    private String stringMetingen = "select * from metingen";
    private String stringAddMetingen = "insert into metingen(provider_id,traject_id,reistijd) values (?, ?, ?)";

    /**
     * Constructor van de klasse.
     */
    public MetingRepository() {
        connector = new DBConnector();
    }

    /**
     * Deze methode geeft alle metingen terug gemaakt door een provider van een traject.
     *
     * @param provider  De provider waarvan je de metingen wil opvragen
     * @param traject   Het traject waarvan je de metingen wil opvragen
     * @return          Een lijst van metingen
     * @throws SQLException Indien de databank niet beschikbaar is of als de query niet geldig is
     * @see Meting
     */
    public List<Meting> getMetingen(Provider provider, Traject traject) throws SQLException {
        List<Meting> metingen = new ArrayList<>();
        ResultSet rs = null;
        try {
            statMetingenPerProvPerTraj = connector.getConnection().prepareStatement(stringMetingenPerProvPerTraj);
            statMetingenPerProvPerTraj.setInt(1,traject.getId());
            statMetingenPerProvPerTraj.setInt(2,provider.getId());

            rs = statMetingenPerProvPerTraj.executeQuery();

            while (rs.next()) {
                int reistijd = rs.getInt("reistijd");
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                metingen.add(new Meting(provider, traject, reistijd, timestamp));
            }

            return metingen;

        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }finally{
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statMetingenPerProvPerTraj.close(); } catch (Exception e) { /* ignored */ }
            try {  connector.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    /**
     * Geeft alle metingen terug die in de database aanwezig zijn.
     * @return Een lijst van metingen.
     * @see Meting
     */
    public List<Meting> getMetingen() {
        ProviderRepository pcrud = new ProviderRepository();
        TrajectRepository tcrud = new TrajectRepository();
        List<Meting> metingen = new ArrayList<Meting>();
        ResultSet rs = null;
        try {
            statMetingen = connector.getConnection().prepareStatement(stringMetingen);
            rs = statMetingen.executeQuery();

            while (rs.next()) {
                int reistijd = rs.getInt("reistijd");
                Provider provider = pcrud.getProvider(rs.getInt("provider_id"));
                Traject traject = tcrud.getTraject(rs.getInt("traject_id"));
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                metingen.add(new Meting(provider, traject, reistijd, timestamp));
            }
            return metingen;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statMetingen.close(); } catch (Exception e) { /* ignored */ }
            try {  connector.close(); } catch (Exception e) { /* ignored */ }
        }

        return null;
    }

    /**
     * Geeft alle metingen van een meegegeven traject terug.
     * @param traject_id    Het ID van het traject
     * @return              Een lijst van metingen
     * @see Meting
     */
    public List<Meting> getMetingenFromTraject(int traject_id)
    {
        String query = "select * from metingen where traject_id ='" + traject_id + "'";
        List<Meting> metingen = getMetingenFromQuery(query, traject_id);

        return metingen;
    }

    /**
     * Geeft alle metingen van een meegegeven traject terug binnen een tijdspanne
     *
     * @param traject_id    Het ID van je traject
     * @param start         De begintijd van de tijdspanne
     * @param end           De eindtijd van de tijdspanne
     * @return Een lijst van metingen
     * @see Meting
     */
    public List<Meting> getMetingenFromTraject(int traject_id, LocalDateTime start, LocalDateTime end)
    {
        String query = "select * from metingen where traject_id ='" + traject_id + "' and timestamp between '"+
                Timestamp.valueOf(start)+"' and '"+Timestamp.valueOf(end)+"'";
        List<Meting> metingen =getMetingenFromQuery(query,traject_id);

        return metingen;
    }

    /**
     * Verwerkt een meegegeven query die een traject_id als parameter verwacht en geeft een lijst van metingen terug.
     *
     * @param query         De query die uitgevoerd moet worden
     * @param traject_id    De parameter traject_id
     * @return  Een lijst van metingen
     * @see Meting
     */
    private List<Meting> getMetingenFromQuery(String query, int traject_id)
    {
        List<Meting> metingen = new ArrayList<>();
        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Traject traject = new TrajectRepository().getTraject(traject_id);

            while (rs.next()) {
                int provider_id = rs.getInt("provider_id");
                Provider provider = new ProviderRepository().getProvider(provider_id);
                int reistijd = rs.getInt("reistijd");
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
     * @param traject_id Een traject_id
     * @return Een lijst van providers, aangevuld met een lijst metingen per provider.
     * @see Meting
     */
    public List<Provider> getMetingenFromTrajectByProvider(int traject_id)
    {
        List<Provider> providers = new ProviderRepository().getActieveProviders();

        for(Provider provider : providers)
        {
            List<Meting> metingen = getMetingenByProvider(provider.getId());
            provider.setMetingen(metingen);
        }

        return providers;
    }

    /**
     * Geeft alle metingen van 1 provider terug
     *
     * @param id    Het ID van de provider
     * @return      Een lijst van metingen
     * @see Meting
     */
    public List<Meting> getMetingenByProvider(int id) {
        List<Meting> provider_metingen = new ArrayList<>();
        Provider provider = new ProviderRepository().getProvider(id);

        String query = "select * from metingen where provider_id = "+id;

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);


            TrajectRepository trajrepo = new TrajectRepository();
            while (rs.next()) {
                Traject traject = trajrepo.getTraject(rs.getInt("traject_id"));
                int reistijd = rs.getInt("reistijd");
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

    public List<Meting> getLaatsteMetingenByProvider(int id){
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

            TrajectRepository trajrepo = new TrajectRepository();
            while (rs.next()) {
                Traject traject = trajrepo.getTraject(rs.getInt("traject_id"));
                int reistijd = rs.getInt("reistijd");
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
     * @return Een lijst van providers
     * @see Provider
     */
    public List<Provider> getGebruikteProviders(){
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
                ProviderRepository repo = new ProviderRepository();
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
     * @param meting    De meting die toegevoegd moet worden
     * @see Meting
     */
    public void addMeting(Meting meting) {
         try {
             statAddMetingen = connector.getConnection().prepareStatement(stringAddMetingen);
             statAddMetingen.setInt(1, meting.getProvider().getId());
             statAddMetingen.setInt(2, meting.getTraject().getId());
             if(meting.getReistijd() >= 0)
                statAddMetingen.setInt(3, meting.getReistijd());
             else
                statAddMetingen.setNull(3, Types.INTEGER);
             statAddMetingen.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
             try { statAddMetingen.close(); } catch (Exception e) { /* ignored */ }
             try {  connector.close(); } catch (Exception e) { /* ignored */ }
         }
    }

    public ProviderTrajectStatistiek metingStatistieken(int traject_id, int provider_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        String stringStatistieken =
                "        select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging" +
                "        from metingen m1" +
                "        join metingen m2 on m1.traject_id = m2.traject_id" +
                "        join trajecten traj on traj.id = m2.traject_id" +
                "        where m1.traject_id = ? and m1.provider_id = ? and m1.timestamp between ? and ? and m1.reistijd is not null" +
                "        group by m1.traject_id";

        try {
            statStatistieken = connector.getConnection().prepareStatement(stringStatistieken);

            statStatistieken.setInt(1,traject_id);
            statStatistieken.setInt(2,provider_id);
            statStatistieken.setTimestamp(3,Timestamp.valueOf(start_tijdstip));
            statStatistieken.setTimestamp(4,Timestamp.valueOf(end_tijdstip));

            ProviderTrajectStatistiek providerTrajectStatistiek;

            ResultSet rs = statStatistieken.executeQuery();

            if(rs.next()) {
                Traject traject = new TrajectRepository().getTraject(rs.getInt("traject_id"));
                Provider provider = new ProviderRepository().getProvider(provider_id);
                Vertraging vertraging = new Vertraging(traject, rs.getDouble("avg_vertraging"));

                providerTrajectStatistiek = new ProviderTrajectStatistiek(provider,vertraging);

                return providerTrajectStatistiek;

            }

            connector.close();

            return null;
        }catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return null;
    }

    public int getOptimaleReistijdLaatste7Dagen(int trajectId, int providerId){
        String query = "select count(*) total, now() now from metingen " +
                "where provider_id = ? and traject_id = ? and reistijd is not null " +
                "and timestamp between date_sub(now(), INTERVAL 7 DAY) and now()";
        ResultSet rs = null;
        try {
            PreparedStatement stat = connector.getConnection().prepareStatement(query);
            stat.setInt(1, providerId);
            stat.setInt(2, trajectId);
            rs = stat.executeQuery();

            if(rs.next()){
                double total = rs.getInt("total");
                if(total > 0){
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
                    if(rs.next()){
                        return rs.getInt("reistijd");
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }finally{
            try{ rs.close();} catch (Exception e) { /* ignored */ }
            try{ connector.close();} catch (SQLException e) { /* ignored */ }
        }
        return -1;
    }

    public double gemiddeldeVertraging(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        String gemiddelde_vertraging = "select avg(reistijd-traj.optimale_reistijd) totale_vertraging from metingen "+
        "join trajecten traj on traj.id = traject_id "+
        "where metingen.timestamp between ? and ? and reistijd is not null ";
        ResultSet rs = null;
        try {
            statStatistieken = connector.getConnection().prepareStatement(gemiddelde_vertraging);
            statStatistieken.setTimestamp(1,Timestamp.valueOf(start_tijdstip));
            statStatistieken.setTimestamp(2,Timestamp.valueOf(end_tijdstip));
            rs = statStatistieken.executeQuery();

            if(rs.next()) {
                return rs.getDouble("totale_vertraging");
            }else{
                throw new SQLException("Oeps... We hebben geen algemene vertraging kunnen bepalen.");
            }
        }catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }finally{
            try{ rs.close();} catch (Exception e) { /* ignored */ }
            try{ connector.close();} catch (SQLException e) { /* ignored */ }
        }
        return -1;
    }

    public ProviderStatistiek metingProviderStatistieken(int provider_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip) {
        String gemiddelde_per_traject_provider = "select m1.traject_id, traj.naam, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging "+
        "from metingen m1 "+
        "join metingen m2 on m1.traject_id = m2.traject_id "+
        "join trajecten traj on traj.id = m2.traject_id "+
        "where m1.provider_id = ? and m1.timestamp between ? and ? and m1.reistijd is not null "+
        "group by m1.traject_id";
        try {
            statStatistieken = connector.getConnection().prepareStatement(gemiddelde_per_traject_provider);
            statStatistieken.setInt(1, provider_id);
            statStatistieken.setTimestamp(2,Timestamp.valueOf(start_tijdstip));
            statStatistieken.setTimestamp(3,Timestamp.valueOf(end_tijdstip));

            Provider provider = new ProviderRepository().getProvider(provider_id);
            ProviderStatistiek stat = new ProviderStatistiek(provider);
            ResultSet rs = statStatistieken.executeQuery();

            while (rs.next()) {
                Traject traject = new TrajectRepository().getTraject(rs.getInt("traject_id"));
                double avg_vertraging = rs.getDouble("avg_vertraging");
                stat.addVertraging(new Vertraging(traject,avg_vertraging));
            }

            return stat;
        }catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return null;
    }

    public Vertraging metingTrajectStatistieken(int traject_id, LocalDateTime start_tijdstip, LocalDateTime end_tijdstip){
        String traject_vertraging = "select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging"+
        " from metingen m1"+
        " join metingen m2 on m1.traject_id = m2.traject_id"+
        " join trajecten traj on traj.id = m2.traject_id"+
        " where m1.traject_id = ? and m1.timestamp between ? and ? and m1.reistijd is not null"+
        " group by m1.traject_id";

        try {
            statStatistieken = connector.getConnection().prepareStatement(traject_vertraging);
            statStatistieken.setInt(1, traject_id);
            statStatistieken.setTimestamp(2,Timestamp.valueOf(start_tijdstip));
            statStatistieken.setTimestamp(3,Timestamp.valueOf(end_tijdstip));

            Traject traject = new TrajectRepository().getTraject(traject_id);



            ResultSet rs = statStatistieken.executeQuery();

            if(rs.next()) {
                Vertraging vertraging = new Vertraging(traject, rs.getDouble("avg_vertraging"));

                return vertraging;
            }
        }catch (SQLException e) {
            logger.error("Statistieken ophalen mislukt");
            logger.error(e);
        }
        return null;
    }

    /**
     * Zoekt het drukste traject voor een bepaalde tijdsinterval, geeft een Vertraging object terug
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moeten
     * @param end_tijdstip het eind tijdstip tot waar gezocht moeten
     * @return drukste tijdstip
     */
    public Vertraging getDrukstePunt(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip){
        String trajecten_vertraging = "select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging" +
                "        from metingen m1" +
                "        join metingen m2 on m1.traject_id = m2.traject_id" +
                "        join trajecten traj on traj.id = m2.traject_id" +
                "        where m1.timestamp between ? and ? and m1.reistijd is not null" +
                "        group by m1.traject_id";

        try {
            statStatistieken = connector.getConnection().prepareStatement(trajecten_vertraging);
            statStatistieken.setTimestamp(1,Timestamp.valueOf(start_tijdstip));
            statStatistieken.setTimestamp(2,Timestamp.valueOf(end_tijdstip));

            ResultSet rs = statStatistieken.executeQuery();
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
     * @param start_tijdstip het start tijdstip waarbinnen gezocht moeten
     * @param end_tijdstip het eind tijdstip tot waar gezocht moeten
     * @return alle trajecten die een vertraging hebben over dat tijdstip
     */
    public List<Vertraging> getVertragingen(LocalDateTime start_tijdstip, LocalDateTime end_tijdstip)
    {
        String trajecten_vertraging = "select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging" +
                "        from metingen m1" +
                "        join metingen m2 on m1.traject_id = m2.traject_id" +
                "        join trajecten traj on traj.id = m2.traject_id" +
                "        where m1.timestamp between ? and ? and m1.reistijd is not null" +
                "        group by m1.traject_id";

        try {
            statStatistieken = connector.getConnection().prepareStatement(trajecten_vertraging);
            statStatistieken.setTimestamp(1, Timestamp.valueOf(start_tijdstip));
            statStatistieken.setTimestamp(2, Timestamp.valueOf(end_tijdstip));

            ResultSet rs = statStatistieken.executeQuery();
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

/*
Queries

Gemiddelde vertraging voor een traject binnen een bepaald interval


Gemiddelde vertraging per traject binnen een bepaald interval
        select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) avg_vertraging
        from metingen m1
        join metingen m2 on m1.traject_id = m2.traject_id
        join trajecten traj on traj.id = m2.traject_id
        where m1.timestamp between DATE_SUB(NOW(), INTERVAL 1 hour) and NOW() and m1.reistijd is not null
        group by m1.traject_id;

Gemiddelde vertraging voor alle trajecten binnen een bepaald interval
        select avg(y.gemiddelde_vertraging) avg_vertraging from (
        select m1.traject_id, avg(m2.reistijd-traj.optimale_reistijd) gemiddelde_vertraging
        from metingen m1
        join metingen m2 on m1.traject_id = m2.traject_id
        join trajecten traj on traj.id = m2.traject_id
        where m1.timestamp between DATE_SUB(NOW(), INTERVAL 1 hour) and NOW() and m1.reistijd is not null
        group by m1.traject_id
        ) y;
 */