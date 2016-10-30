package com.peanutBarrel.dao.object;

import java.awt.Image;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.ImageIcon;

import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.data.ChildInfo;
import com.peanutBarrel.data.ChildPicture;
import com.peanutBarrel.errorLogging.ErrorLogger;
import com.peanutBarrel.services.DatabaseServices;

public class ChildInfoDAO extends DAO
{

    public ChildInfoDAO()
    {
    }

    public static ChildInfo getChildInfo(Long childInfoId)
    {
    	ChildInfo childInfo = new ChildInfo();
        ResultSet rs;

        rs = executeQuery((new StringBuilder("SELECT * FROM CHILD_INFO WHERE Child_Info_ID = ")).append(childInfoId).toString());
        try
        {
            if(rs.next())
            {
            	childInfo.setChildInfoId(childInfoId);
            	childInfo.setBirthday(rs.getDate("Birthday"));
            	childInfo.setEnrollmentDate(rs.getDate("Enrollment_Date"));
            	
            	String medicalInfo = rs.getString("Medical_Info");
            	childInfo.setMedicalInfo((medicalInfo == null)? "" : medicalInfo);
            	
            	String notes = rs.getString("Notes");
            	childInfo.setNotes((notes == null)? "" : notes);
            	
            	childInfo.setPicture(getPicture(childInfoId));
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

        return childInfo;
    }

    public static ChildPicture getPicture(Long childInfoId)
    {
        ChildPicture picture;
        ResultSet rs;
        picture = null;
        rs = executeQuery((new StringBuilder("SELECT * FROM CHILD_INFO WHERE Child_Info_ID = ")).append(childInfoId).toString());
        try
        {
            if(rs.next())
            {
                Blob blob = rs.getBlob("Picture");
                
                if(blob != null)
                {
	                java.io.InputStream inputStream = blob.getBinaryStream();
	                ImageIcon icon = new ImageIcon(blob.getBytes(1L, (int)blob.length()));
	                Image img = icon.getImage();
	                Image newimg = img.getScaledInstance(100, 100, 4);
	                picture = new ChildPicture(newimg, inputStream);
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

        return picture;
    }

    public static long createNewChildInfo(ChildInfo childInfo)
    {
        Date birthday = childInfo.getBirthday();
        String medicalInfo = childInfo.getMedicalInfo();
        String notes = childInfo.getNotes();
        String sql = (new StringBuilder("insert into child_info (birthday, enrollment_date, medical_info, notes) values ("))
        		.append(birthday).append(", ")
        		.append("sysdate, ")
        		.append("'").append(medicalInfo).append("', ")
        		.append("'").append(notes).append("')").toString();
        
        long childInfoId = executeInsert(sql).longValue();
        executeInsert(childInfo.getPicture(), Long.valueOf(childInfoId));
        return childInfoId;
    }
}
