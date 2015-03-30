package com.peanutBarrel.dao.object;

import java.sql.ResultSet;

import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.entities.Adult;
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
        Long familyId;
        Long cridentialsId;
        Long contactInfoId;
        String firstName;
        String lastName;
        String middleName;
        ResultSet rs;
        familyId = null;
        cridentialsId = null;
        contactInfoId = null;
        firstName = null;
        lastName = null;
        middleName = null;
        rs = executeQuery((new StringBuilder("SELECT * FROM ADULT WHERE Adult_ID = ")).append(adultId).toString());
        try
        {
            if(rs.next())
            {
                familyId = FamilyDAO.getNextFamilyId();
                cridentialsId = Long.valueOf(rs.getLong("Cridentials_Id"));
                contactInfoId = Long.valueOf(rs.getLong("Contact_Info_Id"));
                firstName = rs.getString("First_Name");
                lastName = rs.getString("Last_Name");
                middleName = rs.getString("Middle_Name");
                if(middleName == null)
                {
                    middleName = "";
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
        
        Adult adult = new Adult();
        adult.setAdultId(adultId);
        adult.setContactInfo(ContactInfoDAO.getContactInfo(contactInfoId));
        adult.setCridentialsId(cridentialsId);
        adult.setUserType(getUserType(cridentialsId));
        adult.setFamilyId(familyId);
        adult.setFirstName(firstName);
        adult.setLastName(lastName);
        adult.setMiddleName(middleName);
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
            System.out.print(e.getStackTrace());
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return adult;
    }

    private static int getUserType(Long cridenialsId)
    {
        int userType;
        ResultSet rs;
        userType = -1;
        rs = executeQuery((new StringBuilder("SELECT User_Type_Id FROM CRIDENTIALS WHERE Cridentials_ID = ")).append(cridenialsId).toString());
        try
        {
            if(rs.next())
            {
                userType = rs.getInt("User_Type_Id");
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
        
        return userType;
    }

    public static Adult addAdult(Adult adult)
    {
        String sql;
        if(adult.getFamilyId().longValue() == 0L)
        {
            adult.setFamilyId(createNewFamily());
        }
        adult.setCridentialsId(Long.valueOf(CridentialsDAO.getLatestCridentialsId()));
        adult.getContactInfo().getAddress().setAddressId(AddressDAO.persistAddress(adult.getContactInfo().getAddress()));
        adult.getContactInfo().setContactInfoId(Long.valueOf(ContactInfoDAO.persistContactInfo(adult.getContactInfo())));
        sql = (new StringBuilder("INSERT INTO ADULT (First_Name, Middle_Name, Last_Name, Family_Id, Contact_Info_I" +
"d, Cridentials_Id)VALUES ('"
)).append(adult.getFirstName()).append("', ").append("'").append(adult.getMiddleName()).append("', ").append("'").append(adult.getLastName()).append("', ").append(adult.getFamilyId()).append(", ").append(adult.getContactInfo().getContactInfoId()).append(", ").append(adult.getCridentialsId()).append(")").toString();
        try
        {
            adult.setAdultId(executeInsert(sql));
        }
        catch(Exception e)
        {
            System.out.print(e.getStackTrace());
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return adult;
    }

    private static Long createNewFamily()
    {
        long newFamilyId;
        String sql;
        newFamilyId = 0L;
        sql = (new StringBuilder("INSERT INTO FAMILY (Primary_Adult_Id) VALUES (")).append(getNextAdultId()).append(")").toString();
        try
        {
            newFamilyId = executeInsert(sql).longValue();
        }
        catch(Exception e)
        {
            System.out.print(e.getStackTrace());
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return Long.valueOf(newFamilyId);
    }

    private static int getNextAdultId()
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
            System.out.print(e.getStackTrace());
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return newAdultId;
    }
}
