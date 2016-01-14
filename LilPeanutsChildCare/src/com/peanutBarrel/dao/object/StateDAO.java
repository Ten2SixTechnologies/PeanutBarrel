package com.peanutBarrel.dao.object;

import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;

import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.services.DatabaseServices;

public class StateDAO extends DAO
{

    public StateDAO()
    {
    }

    public static String getStateAbrev(Long stateId)
    {
        String state;
        ResultSet rs;
        state = null;
        rs = executeQuery((new StringBuilder("SELECT * FROM STATE WHERE State_ID = ")).append(stateId).toString());
        try
        {
            if(rs.next())
            {
                state = rs.getString("State_Abrev");
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
        
        return state;
    }

    public static String getStateName(Long stateId)
    {
        String state;
        ResultSet rs;
        state = null;
        rs = executeQuery((new StringBuilder("SELECT * FROM STATE WHERE State_ID = ")).append(stateId).toString());
        try
        {
            if(rs.next())
            {
                state = rs.getString("State_Name");
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

        return state;
    }

    public static Map<String, String> getAllStates()
    {
        Map<String, String> states;
        String sql;
        states = new TreeMap<String, String>();
        sql = "SELECT * FROM STATE order by State_Name";
        ResultSet rs = null;
        try
        {
        	rs = executeQuery(sql);
        	
            while(rs.next())
            { 
            	states.put(rs.getString("State_Name"), rs.getString("State_Abrev"));
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

        return states;
    }
}
