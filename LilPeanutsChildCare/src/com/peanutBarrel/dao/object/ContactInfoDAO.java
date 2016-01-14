package com.peanutBarrel.dao.object;

import java.sql.ResultSet;

import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.data.ContactInfo;
import com.peanutBarrel.errorLogging.ErrorLogger;
import com.peanutBarrel.services.DatabaseServices;

// Referenced classes of package com.peanutBarrel.dao.object:
//            AddressDAO

public class ContactInfoDAO extends DAO
{

    public ContactInfoDAO()
    {
    }

    public static long persistContactInfo(ContactInfo contactInfo)
    {
        long newContactInfoId;
        
        String sql;
        Long primaryPhoneId = persistPhoneNumber(contactInfo.getPrimaryPhone());
        Long secondaryPhoneId = persistPhoneNumber(contactInfo.getSecondaryPhone());
        
        String email = contactInfo.getEMail().equals("") ? null : contactInfo.getEMail();
        newContactInfoId = 0L;
        
        sql = (new StringBuilder("INSERT INTO CONTACT_INFO (Primary_Phone_Id, Secondary_Phone_Id, Address_Id, Email)VALUES ("))
        		.append(primaryPhoneId).append(", ")
        		.append(secondaryPhoneId).append(", ")
        		.append(contactInfo.getAddress().getAddressId()).append(", ")
        		.append(email).append(")").toString();
        try
        {
            newContactInfoId = executeInsert(sql).longValue();
        }
        catch(Exception e)
        {
            ErrorLogger.LogError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return newContactInfoId;
    }

    public static Long persistPhoneNumber(String phoneNumber)
    {
        Long newPhoneId;
        String sql;
        newPhoneId = null;
        
        String areaCode = phoneNumber.substring(0, 3);
        String prefix = phoneNumber.substring(3, 6);
        String postfix = phoneNumber.substring(6, 10);
        sql = (new StringBuilder("INSERT INTO PHONE (Area_Code, Prefix, Postfix) VALUES ("))
        		.append(areaCode).append(", ")
        		.append(prefix).append(", ")
        		.append(postfix).append(")").toString();
        try
        {
            newPhoneId = executeInsert(sql);
        }
        catch(Exception e)
        {
            ErrorLogger.LogError(e);
        }
        finally
        {
        	DatabaseServices.closeCurrentConnection();
        }
        
        return newPhoneId;
    }

    public static ContactInfo getContactInfo(Long contactInfoId)
    {
    	ContactInfo contactInfo = new ContactInfo();
        ResultSet rs;

        rs = executeQuery((new StringBuilder("SELECT * FROM CONTACT_INFO WHERE Contact_Info_ID = ")).append(contactInfoId).toString());
        try
        {
            if(rs.next())
            {
            	contactInfo.setContactInfoId(contactInfoId);
            	contactInfo.setPrimaryPhone(getPhone(rs.getLong("Primary_Phone_ID")));
            	contactInfo.setSecondaryPhone(getPhone(rs.getLong("Secondary_Phone_ID")));
            	contactInfo.setAddress(AddressDAO.getAddress(rs.getLong("Address_ID")));

            	String eMail = rs.getString("EMail");
                contactInfo.setEMail((eMail == null) ? "" : eMail);
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
        
        return contactInfo;
    }

    private static String getPhone(Long phoneId)
    {
        String phone = null;
        ResultSet rs;

        rs = executeQuery((new StringBuilder("SELECT * FROM PHONE WHERE Phone_ID = ")).append(phoneId).toString());
        try
        {
            if(rs.next())
            {
                Integer areaCode = Integer.valueOf(rs.getInt("Area_Code"));
                Integer prefix = Integer.valueOf(rs.getInt("Prefix"));
                Integer postfix = Integer.valueOf(rs.getInt("Postfix"));
                
                phone = (new StringBuilder("(")).append(areaCode).append(") ").append(prefix).append("-").append(postfix).toString();
            } 
            else
            {
                phone = "";
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

        return phone;
    }
}
