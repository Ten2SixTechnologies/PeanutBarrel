package com.peanutBarrel.dao.object;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.data.TimeLog;
import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.entities.Child;
import com.peanutBarrel.errorLogging.ErrorLogger;
import com.peanutBarrel.services.DatabaseServices;

public class TimeLogDAO extends DAO
{

    public TimeLogDAO()
    {
    }

    public static Long persistNewTimeEntry(TimeLog timeEntry)
    {
        Long timeEntryId = null;
        try
        {
            timeEntryId = executeInsert((new StringBuilder("INSERT INTO TIME_LOG (CHILD_ID,ADULT_IN_ID,TIME_IN) VALUES ("))
            		.append(timeEntry.getChild().getChildId()).append(",")
            		.append(timeEntry.getAdultIn().getAdultId()).append(",").append("'")
            		.append(new Timestamp(timeEntry.getTimeIn().getTime())).append("')").toString());
        }
        catch(Exception e)
        {
            ErrorLogger.LogError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        return timeEntryId;
    }

    public static Long updateTimeEntry(TimeLog timeEntry)
    {
        Long timeEntryId = null;
        try
        {
            executeUpdate((new StringBuilder("UPDATE TIME_LOG SET Time_Out = '"))
            		.append(new Timestamp(timeEntry.getTimeOut().getTime())).append("',")
            		.append("Adult_Out_ID = ").append(timeEntry.getAdultOut().getAdultId()).append(" ")
            		.append("WHERE Time_Log_ID = ").append(timeEntry.getTimeLogId()).toString());
        }
        catch(Exception e)
        {
            ErrorLogger.LogError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        return timeEntryId;
    }

    public static List<TimeLog> getTimeLog(Long childId)
    {
        ResultSet rs;
        List<TimeLog> timeLogs = new ArrayList<TimeLog>();
        
        rs = executeQuery((new StringBuilder("SELECT * FROM TIME_LOG WHERE Child_ID = ")).append(childId).toString());
        
        try
        {
            while(rs.next())
            {
                Long timeLogId = Long.valueOf(rs.getLong("Time_Log_ID"));
                Child child = ChildDAO.getChild(Long.valueOf(rs.getLong("Child_ID")));
                Adult adultIn = AdultDAO.getAdult(Long.valueOf(rs.getLong("Adult_In_ID")));
                Date timeIn = rs.getDate("Time_In");
                Long adultId = Long.valueOf(rs.getLong("Adult_Out_ID"));
                
                Adult adultOut = null;
                
                if(adultId.longValue() != 0L)
                {
                    adultOut = AdultDAO.getAdult(adultId);
                }
                
                Date timeOut = rs.getDate("Time_Out");
                
                TimeLog timeLog = new TimeLog();
                timeLog.setTimeLogId(timeLogId);
                timeLog.setChild(child);
                timeLog.setAdultIn(adultIn);
                timeLog.setTimeIn(timeIn);
                timeLog.setAdultOut(adultOut);
                timeLog.setTimeOut(timeOut);
                
                timeLogs.add(timeLog);
            }
        }
        catch(Exception e)
        {
            ErrorLogger.LogError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return timeLogs;
    }
}
