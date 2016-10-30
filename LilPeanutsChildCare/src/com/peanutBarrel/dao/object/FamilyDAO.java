package com.peanutBarrel.dao.object;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.peanutBarrel.actions.ActionsDelegate;
import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.errorLogging.ErrorLogger;
import com.peanutBarrel.services.DatabaseServices;

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
            ErrorLogger.logError(e);
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
                    families.put((new StringBuilder(String.valueOf(primaryAdult.getFirstName()))).append(" ")
                    		.append(primaryAdult.getLastName()).toString(), Integer.valueOf(rs.getInt("Family_Id")));
                }
            }
        }
        catch(Exception e)
        {
            ErrorLogger.logError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }

        return families;
    }
    
    public static Long createNewFamily()
    {
        long newFamilyId;
        String sql;
        newFamilyId = 0L;
        sql = (new StringBuilder("INSERT INTO FAMILY (Primary_Adult_Id) VALUES (")).append(AdultDAO.getNextAdultId()).append(")").toString();
        try
        {
            newFamilyId = executeInsert(sql).longValue();
        }
        catch(Exception e)
        {
            ErrorLogger.logError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return Long.valueOf(newFamilyId);
    }
}
