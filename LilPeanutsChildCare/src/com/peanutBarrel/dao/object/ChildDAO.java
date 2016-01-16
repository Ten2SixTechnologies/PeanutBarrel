package com.peanutBarrel.dao.object;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.peanutBarrel.dao.DAO;
import com.peanutBarrel.data.ChildInfo;
import com.peanutBarrel.data.ChildPicture;
import com.peanutBarrel.entities.Child;
import com.peanutBarrel.errorLogging.ErrorLogger;
import com.peanutBarrel.services.DatabaseServices;

public class ChildDAO extends DAO
{

    public ChildDAO()
    {
    }

    public static List<Child> getChildren(Long adultId)
    {
        List<Child> children;
        ResultSet rs;
        children = new ArrayList<Child>();
        rs = executeQuery((new StringBuilder("select Child_Id from CHILD inner join ADULT on CHILD.Family_id = ADULT.Family_Id where ADULT.adult_id = ")).append(adultId).toString());
        try
        {
            while(rs.next()) 
            {
                children.add(getChild(Long.valueOf(rs.getLong("Child_Id"))));
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

        return children;
    }

    public static Child getChild(Long childId)
    {
    	Child child = null;
        ResultSet rs;
        
        rs = executeQuery((new StringBuilder("SELECT * FROM CHILD WHERE Child_ID = ")).append(childId).toString());
        try
        {
            if(rs.next())
            {
            	child = new Child(Long.valueOf(rs.getLong("Family_ID")));
            	child.setChildId(childId);
            	child.setChildInfo(ChildInfoDAO.getChildInfo(Long.valueOf(rs.getLong("Child_Info_ID"))));
            	child.setFirstName(rs.getString("First_Name"));
            	child.setLastName(rs.getString("Last_Name"));
            	
            	String middleName = rs.getString("Middle_Name");
            	child.setMiddleName((middleName == null) ? "" : middleName);
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

        return child;
    }

    public static void createNewChild(String firstName, String middleName, String lastName, Date birthDate, String medicalInfo, String notes, ChildPicture picture, String family)
    {
        ChildInfo childInfo = new ChildInfo();
        childInfo.setBirthday(birthDate);
        childInfo.setMedicalInfo(medicalInfo);
        childInfo.setNotes(notes);
        childInfo.setPicture(picture);
        childInfo.setChildInfoId(Long.valueOf(ChildInfoDAO.createNewChildInfo(childInfo)));
        
        Child child = new Child(Long.valueOf(Long.parseLong(family)));
        child.setFirstName(firstName);
        child.setMiddleName(middleName);
        child.setLastName(lastName);
        child.setChildInfo(childInfo);
        
        String sql = (new StringBuilder("insert into child (first_name, middle_name, last_name, child_info_id, family_id) values ('"))
        		.append(child.getFirstName()).append("', ").append("'")
        		.append(child.getMiddleName()).append("', ").append("'")
        		.append(child.getLastName()).append("', ")
        		.append(child.getChildInfo().getChilInfoId()).append(", ")
        		.append(child.getFamilyId()).append(")").toString();
        
        executeInsert(sql);
    }
}
