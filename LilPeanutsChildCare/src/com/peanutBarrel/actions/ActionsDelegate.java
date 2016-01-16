package com.peanutBarrel.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.peanutBarrel.constants.UserType;
import com.peanutBarrel.dao.object.AdultDAO;
import com.peanutBarrel.dao.object.ChildDAO;
import com.peanutBarrel.dao.object.CridentialsDAO;
import com.peanutBarrel.dao.object.FamilyDAO;
import com.peanutBarrel.dao.object.TimeLogDAO;
import com.peanutBarrel.data.Address;
import com.peanutBarrel.data.ChildPicture;
import com.peanutBarrel.data.ContactInfo;
import com.peanutBarrel.data.TimeLog;
import com.peanutBarrel.entities.Adult;
import com.peanutBarrel.entities.Child;
import com.peanutBarrel.runtimeExceptions.AlreadyCheckedInException;
import com.peanutBarrel.runtimeExceptions.AlreadyCheckedOutException;
import com.peanutBarrel.runtimeExceptions.NotACurrentClientException;

// Referenced classes of package com.peanutBarrel.actions:
//            Sorter

public class ActionsDelegate
{

    public ActionsDelegate()
    {
    }

    public static void signChildIn(Child child, Adult adult)
        throws AlreadyCheckedInException
    {
        List<TimeLog> timeLogs = getTimeLog(child);
        TimeLog mostRecentTimeLog = null;
        if(timeLogs.size() > 0)
        {
            mostRecentTimeLog = (TimeLog)timeLogs.get(timeLogs.size() - 1);
        } else
        {
            mostRecentTimeLog = new TimeLog();
        }
        if(mostRecentTimeLog.getTimeIn() != null && mostRecentTimeLog.getTimeOut() == null)
        {
            throw new AlreadyCheckedInException((new StringBuilder(String.valueOf(child.getFirstName()))).append(" is already checked in").toString());
        } else
        {
            TimeLog newLog = new TimeLog();
            newLog.setChild(child);
            newLog.setAdultIn(adult);
            newLog.setTimeIn(new Date());
            newLog.setTimeLogId(addTimeLog(newLog));
            return;
        }
    }

    public static void signChildOut(Child child, Adult adult)
        throws AlreadyCheckedOutException, NotACurrentClientException
    {
        List<TimeLog> timeLogs = getTimeLog(child);
        TimeLog mostRecentTimeLog = null;
        if(timeLogs.size() > 0)
        {
            mostRecentTimeLog = (TimeLog)timeLogs.get(timeLogs.size() - 1);
        } else
        {
            throw new NotACurrentClientException((new StringBuilder(String.valueOf(child.getFirstName()))).append("is not a current client").toString());
        }
        if(mostRecentTimeLog.getTimeOut() != null)
        {
            throw new AlreadyCheckedOutException((new StringBuilder(String.valueOf(child.getFirstName()))).append(" is already checked out").toString());
        } else
        {
            mostRecentTimeLog.setAdultOut(adult);
            mostRecentTimeLog.setTimeOut(new Date());
            updateTimeLog(mostRecentTimeLog);
            return;
        }
    }

    public static String getChildStatus(Child child)
    {
        String childStatus = "out";
        List<TimeLog> timeLogs = getTimeLog(child);
        TimeLog mostRecentTimeLog = null;
        if(timeLogs.size() > 0)
        {
            mostRecentTimeLog = (TimeLog)timeLogs.get(timeLogs.size() - 1);
            if(mostRecentTimeLog.getTimeIn() != null && mostRecentTimeLog.getTimeOut() == null)
            {
                childStatus = "in";
            }
        }
        return childStatus;
    }

    public static Adult signIn(String userName, String password, Adult adult)
    {
        Long cridentialsId = CridentialsDAO.loginValidation(userName, password);
        if(cridentialsId.longValue() != -1L)
        {
            adult = AdultDAO.getAdultWithCridentialsId(cridentialsId);
            if(adult.getAdultId().longValue() == -1L)
            {
                adult = new Adult();
                adult.setAdultId(null);
                adult.setUserType(CridentialsDAO.getUserTypeFromCridentialsId(cridentialsId));
            }
        }
        return adult;
    }

    public static Long createNewCridentials(String userName, String password, int userType)
    {
        return CridentialsDAO.persistCridentials(userName, password, userType);
    }

    public static List<Child> getChildren(Adult adult)
    {
        return Sorter.sortChildren(ChildDAO.getChildren(adult.getAdultId()));
    }
    
    public static List<Adult> getAllAdults()
    {
    	List<Adult> allAdults = AdultDAO.getAllAdults();
    	
    	return (allAdults.isEmpty() ? null : allAdults);
    }

    public static List<TimeLog> getTimeLog(Child child)
    {
        return TimeLogDAO.getTimeLog(child.getChildId());
    }

    public static Long addTimeLog(TimeLog newLog)
    {
        return TimeLogDAO.persistNewTimeEntry(newLog);
    }

    public static Long updateTimeLog(TimeLog newLog)
    {
        return TimeLogDAO.updateTimeEntry(newLog);
    }

    public static void createNewUser(String args[])
    {
        Address address = new Address();
        address.setHouseNumber(args[6]);
        address.setStreet(args[7]);
        address.setApt(args[8]);
        address.setCity(args[9]);
        address.setState(args[11]);
        address.setZipCode(Integer.parseInt(args[10]));
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setPrimaryPhone(args[3]);
        contactInfo.setSecondaryPhone(args[4]);
        contactInfo.setEMail(args[5]);
        contactInfo.setAddress(address);
        Adult adult = new Adult();
        adult.setFirstName(args[0]);
        adult.setMiddleName(args[1]);
        adult.setLastName(args[2]);
        adult.setFamilyId(Long.valueOf(Long.parseLong(args[12])));
        adult.setContactInfo(contactInfo);
        AdultDAO.addAdult(adult);
    }

    public static boolean adultIsUser(Adult primaryAdult)
    {
        boolean result = false;
        if(UserType.USER == CridentialsDAO.getUserTypeFromCridentialsId(primaryAdult.getCridentialsId()))
        {
            result = true;
        }
        return result;
    }

    public static Map<String, Integer> getFamilies()
    {
        Map<String, Integer> families = FamilyDAO.getAllFamilies();
        return families;
    }

    public static void changeCridentials(long adultId, int userType)
    {
        CridentialsDAO.changeCridentials(adultId, userType);
    }

    public static void createNewChild(String firstName, String middleName, String lastName, Date birthDate, String medicalInfo, String notes, ChildPicture picture, String family)
    {
        ChildDAO.createNewChild(firstName, middleName, lastName, birthDate, medicalInfo, notes, picture, family);
    }
}
