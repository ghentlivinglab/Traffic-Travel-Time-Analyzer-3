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
                trajecten.add(new Traject(rs.getInt("ID"),
                                            rs.getString("letter"),
                                            rs.getString("naam"),
                                            rs.getInt("lengte"),
                                            rs.getInt("optimale_reistijd"),
                                            rs.getBoolean("is_active"),
                                            rs.getString("start_latitude"),
                                            rs.getString("start_longitude"),
                                            rs.getString("end_latitude"),
                                            rs.getString("end_longitude")

                ));
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
            if(trajectList.get(i).getEnd_latitude().isEmpty())
            {
                trajectList.remove(i);
            }
        }

        return trajectList;
    }
}
