package com.peanutBarrel.dao.object;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.peanutBarrel.constants.UserType;
import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.errorLogging.ErrorLogger;
import com.peanutBarrel.runtimeExceptions.DataNotFoundException;
import com.peanutBarrel.services.DatabaseServices;

public class AdultDAO extends DAO
{

    public AdultDAO()
    {
    }

    public static Adult getAdult(Long adultId)
    {
    	Adult adult = new Adult();
        ResultSet rs;

        String sql = (new StringBuilder("SELECT * FROM ADULT WHERE Adult_ID = ")).append(adultId).toString();
        rs = executeQuery(sql);
        try
        {
            if(rs.next())
            {
            	adult.setAdultId(adultId);
            	adult.setContactInfo(ContactInfoDAO.getContactInfo(Long.valueOf(rs.getLong("Contact_Info_Id"))));
            	adult.setCridentialsId(Long.valueOf(rs.getLong("Cridentials_Id")));
            	adult.setUserType(CridentialsDAO.getUserTypeFromCridentialsId(adult.getCridentialsId()));
            	adult.setFamilyId(FamilyDAO.getNextFamilyId());
            	adult.setFirstName(rs.getString("First_Name"));
            	adult.setLastName(rs.getString("Last_Name"));
            	
            	String middleName = rs.getString("Middle_Name");
            	adult.setMiddleName((middleName == null) ? "" : middleName);
            }
            else {
            	throw new DataNotFoundException(sql);
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
        
        return adult;
    }

    public static Adult getAdultWithCridentialsId(Long cridentialsId)
    {
        Adult adult;
        ResultSet rs;
        adult = new Adult();
        String sql = (new StringBuilder("SELECT Adult_Id FROM ADULT WHERE Cridentials_Id = ")).append(cridentialsId).toString();
        rs = executeQuery(sql);
        try
        {
            if(rs.next())
            {
                long adultId = rs.getLong("Adult_Id");
                adult = getAdult(Long.valueOf(adultId));
            }
            else {
            	throw new DataNotFoundException(sql);
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
        
        return adult;
    }

    public static Adult addAdult(Adult adult)
    {
        String sql;
        if(adult.getFamilyId().longValue() == 0L)
        {
            adult.setFamilyId(FamilyDAO.createNewFamily());
        }
        
        adult.setCridentialsId(Long.valueOf(CridentialsDAO.getLatestCridentialsId()));
        adult.getContactInfo().getAddress().setAddressId(AddressDAO.persistAddress(adult.getContactInfo().getAddress()));
        adult.getContactInfo().setContactInfoId(Long.valueOf(ContactInfoDAO.persistContactInfo(adult.getContactInfo())));
        
        sql = (new StringBuilder("INSERT INTO ADULT (First_Name, Middle_Name, Last_Name, Family_Id, Contact_Info_Id, Cridentials_Id)VALUES ('"))
        		.append(adult.getFirstName()).append("', ").append("'")
        		.append(adult.getMiddleName()).append("', ").append("'")
        		.append(adult.getLastName()).append("', ")
        		.append(adult.getFamilyId()).append(", ")
        		.append(adult.getContactInfo().getContactInfoId()).append(", ")
        		.append(adult.getCridentialsId()).append(")").toString();
        try
        {
            adult.setAdultId(executeInsert(sql));
        }
        catch(Exception e)
        {
            ErrorLogger.logError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return adult;
    }

    public static int getNextAdultId()
    {
        int newAdultId;
        String sql;
        newAdultId = 0;
        ResultSet rs = null;
        sql = "SELECT MAX(Adult_Id) AS Adult_Id FROM ADULT";
        try
        {
            rs = executeQuery(sql);
            if(rs.next())
            {
                int maxAdultId = rs.getInt("Adult_Id");
                newAdultId = ++maxAdultId;
            }
            else {
            	throw new DataNotFoundException(sql);
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
        
        return newAdultId;
    }
    
    public static List<Adult> getAllAdults()
    {
    	List<Adult> adults = new ArrayList<Adult>();
    	adults.addAll(getAllUsers());
    	
    	return adults;
    }
    
    private static List<Adult> getAllUsers()
    {
    	List<Adult> adults = new ArrayList<Adult>();
    	String sql = "SELECT ADULT_ID FROM ADULT "
    			+ "JOIN CRIDENTIALS ON ADULT.CRIDENTIALS_ID = CRIDENTIALS.CRIDENTIALS_ID "
    			+ "WHERE USER_TYPE_ID = " + UserType.USER.getKey();
    	ResultSet rs = null;
    	
    	try
    	{
    		rs = executeQuery(sql);

    		List<Long> adultIds = new ArrayList<Long>();
    		while(rs.next())
    		{
    			adultIds.add(rs.getLong("ADULT_ID"));
    		}
    		
    		if(adultIds.isEmpty())
    		{
    			throw new DataNotFoundException(sql);
    		}
    		
    		for(Long adultId : adultIds)
    		{
    			adults.add(getAdult(adultId));
    		}
    	}
    	catch (Exception e) {
    		ErrorLogger.logError(e);
    	}
    	
    	return adults;
    }
    
    public static Adult getAdultWithName(String adultName)
    {
    	Adult adult = new Adult();
    	
    	if(adultName != null && adultName.equals("") == false)
    	{
	    	String[] parsedAdultName = adultName.split(" ");
	    	String firstName = parsedAdultName[0];
	    	String lastName = parsedAdultName[1];
	    	
	    	String sql = "SELECT ADULT_ID FROM ADULT WHERE FIRST_NAME = '" + firstName + "' AND LAST_NAME = '" + lastName + "'";
	    	ResultSet rs = null;
	    	
	    	try
	    	{
	    		rs = executeQuery(sql);
	
	    		Long adultId = 0L;
	    		if(rs.next())
	    		{
	    			adultId = rs.getLong("ADULT_ID");
	    		}
	    		else {
	    			throw new DataNotFoundException(sql);
	    		}
	    		
	    		adult = getAdult(adultId);
	    	}
	    	catch (Exception e) {
	    		ErrorLogger.logError(e);
	    	}
    	}
    	
    	return adult;
    }
}
