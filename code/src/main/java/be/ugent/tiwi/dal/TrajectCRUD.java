package be.ugent.tiwi.dal;

import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.domein.Traject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by jelle on 19.02.16.
 */
public class TrajectCRUD
{
    private DBConnector connector;

    public TrajectCRUD() {
        connector = new DBConnector();
    }

    public List<Traject> getTrajecten()
    {
        List<Traject> trajecten = new ArrayList<Traject>();
        String query = "select * from trajecten";

        try {
            Statement stmt = connector.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
            {
                int id = rs.getInt("ID");
                String letter = rs.getString("letter");
                String naam = rs.getString("naam");
                int lengte = rs.getInt("lengte");
                int optimale_reistijd = rs.getInt("optimale_reistijd");
                boolean is_active = rs.getBoolean("is_active");
                String start_latitude = rs.getString("start_latitude");
                if(rs.wasNull())
                    start_latitude = null;
                String start_longitude = rs.getString("start_longitude");
                if(rs.wasNull())
                    start_longitude = null;
                String end_latitude = rs.getString("end_latitude");
                if(rs.wasNull())
                    end_latitude = null;
                String end_longitude = rs.getString("end_longitude");
                if(rs.wasNull())
                    end_longitude = null;

                trajecten.add(new Traject(id,letter,naam,lengte,optimale_reistijd,is_active,start_latitude,
                        start_longitude,end_latitude,end_longitude));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trajecten;
    }

    public List<Traject> getTrajectenMetCoordinaten()
    {
        List<Traject> trajectList = getTrajecten();

        for(int i=0;i<trajectList.size();i++)
        {
            if(trajectList.get(i).getStart_latitude()==null)
            {
                trajectList.remove(i);
            }
        }

        return trajectList;
    }
}
