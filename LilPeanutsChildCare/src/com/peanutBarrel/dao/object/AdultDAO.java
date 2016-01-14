package com.peanutBarrel.dao.object;

import java.sql.ResultSet;

import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.errorLogging.ErrorLogger;
import com.peanutBarrel.services.DatabaseServices;

// Referenced classes of package com.peanutBarrel.dao.object:
//            FamilyDAO, ContactInfoDAO, CridentialsDAO, AddressDAO

public class AdultDAO extends DAO
{

    public AdultDAO()
    {
    }

    public static Adult getAdult(Long adultId)
    {
    	Adult adult = new Adult();
        ResultSet rs;

        rs = executeQuery((new StringBuilder("SELECT * FROM ADULT WHERE Adult_ID = ")).append(adultId).toString());
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
        }
        catch(Exception e)
        {
        	ErrorLogger.LogError(e);
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
        rs = executeQuery((new StringBuilder("SELECT Adult_Id FROM ADULT WHERE Cridentials_Id = ")).append(cridentialsId).toString());
        try
        {
            if(rs.next())
            {
                long adultId = rs.getLong("Adult_Id");
                adult = getAdult(Long.valueOf(adultId));
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
            ErrorLogger.LogError(e);
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
        }
        catch(Exception e)
        {
            ErrorLogger.LogError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return newAdultId;
    }
}
