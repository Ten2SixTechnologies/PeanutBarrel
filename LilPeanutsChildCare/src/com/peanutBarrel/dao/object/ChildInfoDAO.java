package com.peanutBarrel.dao.object;

import java.awt.Image;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.ImageIcon;

import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.data.ChildInfo;
import com.peanutBarrel.data.ChildPicture;
import com.peanutBarrel.services.DatabaseServices;

public class ChildInfoDAO extends DAO
{

    public ChildInfoDAO()
    {
    }

    public static ChildInfo getChildInfo(Long childInfoId)
    {
        Date birthday;
        Date enrollmentDate;
        String medicalInfo;
        String notes;
        ResultSet rs;
        birthday = null;
        enrollmentDate = null;
        medicalInfo = null;
        notes = null;
        rs = executeQuery((new StringBuilder("SELECT * FROM CHILD_INFO WHERE Child_Info_ID = ")).append(childInfoId).toString());
        try
        {
            if(rs.next())
            {
                birthday = rs.getDate("Birthday");
                enrollmentDate = rs.getDate("Enrollment_Date");
                medicalInfo = rs.getString("Medical_Info");
                notes = rs.getString("Notes");
                if(medicalInfo == null)
                {
                    medicalInfo = "";
                }
                if(notes == null)
                {
                    notes = "";
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

        ChildInfo childInfo = new ChildInfo();
        childInfo.setChildInfoId(childInfoId);
        childInfo.setBirthday(birthday);
        childInfo.setEnrollmentDate(enrollmentDate);
        childInfo.setMedicalInfo(medicalInfo);
        childInfo.setNotes(notes);
        childInfo.setPicture(getPicture(childInfoId));
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
                java.io.InputStream inputStream = blob.getBinaryStream();
                ImageIcon icon = new ImageIcon(blob.getBytes(1L, (int)blob.length()));
                Image img = icon.getImage();
                Image newimg = img.getScaledInstance(100, 100, 4);
                picture = new ChildPicture(newimg, inputStream);
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

        return picture;
    }

    public static long createNewChildInfo(ChildInfo childInfo)
    {
        Date birthday = childInfo.getBirthday();
        String medicalInfo = childInfo.getMedicalInfo();
        String notes = childInfo.getNotes();
        String sql = (new StringBuilder("insert into child_info (birthday, enrollment_date, medical_info, notes) values (")).append(birthday).append(", ").append("sysdate, ").append("'").append(medicalInfo).append("', ").append("'").append(notes).append("')").toString();
        long childInfoId = executeInsert(sql).longValue();
        executeInsert(childInfo.getPicture(), Long.valueOf(childInfoId));
        return childInfoId;
    }
}
