package com.peanutBarrel.dao.object;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.peanutBarrel.actions.ActionsDelegate;
import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.services.DatabaseServices;

// Referenced classes of package com.peanutBarrel.dao.object:
//            AdultDAO

public class FamilyDAO extends DAO
{

    public FamilyDAO()
    {
    }

    public static Long getNextFamilyId()
    {
        Long nextFamilyId;
        ResultSet rs;
        nextFamilyId = null;
        rs = executeQuery("SELECT COUNT(*) COUNT FROM FAMILY");
        try
        {
            if(rs.next())
            {
                nextFamilyId = Long.valueOf(rs.getLong("COUNT") + 1L);
            }
        }
        catch(Exception e)
        {
            System.out.print(e.getStackTrace());
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }

        return nextFamilyId;
    }

    public static Map<String, Integer> getAllFamilies()
    {
        Map<String, Integer> families;
        String sql;
        families = new HashMap<String, Integer>();
        ResultSet rs = null;
        sql = "SELECT * FROM FAMILY";
        try
        {
            while(rs.next())
            {
            	rs = executeQuery(sql);
                Adult primaryAdult = AdultDAO.getAdult(Long.valueOf(rs.getLong("Primary_Adult_Id")));
                if(ActionsDelegate.adultIsUser(primaryAdult))
                {
                    families.put((new StringBuilder(String.valueOf(primaryAdult.getFirstName()))).append(" ").append(primaryAdult.getLastName()).toString(), Integer.valueOf(rs.getInt("Family_Id")));
                }
            }
        }
        catch(Exception e)
        {
            System.out.print(e.getStackTrace());
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }

        return families;
    }
}
