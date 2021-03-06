package com.peanutBarrel.entities;

import com.peanutBarrel.constants.UserType;
import com.peanutBarrel.data.ContactInfo;

public class Adult
{

    Long adultId;
    Long familyId;
    Long cridentialsId;
    UserType userType;
    ContactInfo contactInfo;
    String firstName;
    String middleName;
    String lastName;

    public Adult()
    {
        adultId = new Long(-1L);
        userType = UserType.INVALID;
    }

    public void setAdultId(Long adultId)
    {
        this.adultId = adultId;
    }

    public void setFamilyId(Long familyId)
    {
        this.familyId = familyId;
    }

    public void setCridentialsId(Long cridentialsId)
    {
        this.cridentialsId = cridentialsId;
    }

    public void setUserType(UserType userType)
    {
        this.userType = userType;
    }

    public void setContactInfo(ContactInfo contactInfo)
    {
        this.contactInfo = contactInfo;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Long getAdultId()
    {
        return adultId;
    }

    public Long getFamilyId()
    {
        return familyId;
    }

    public Long getCridentialsId()
    {
        return cridentialsId;
    }

    public UserType getUserType()
    {
        return userType;
    }

    public ContactInfo getContactInfo()
    {
        return contactInfo;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Adult(ContactInfo contactInfo)
    {
        adultId = new Long(-1L);
        userType = UserType.INVALID;
        this.contactInfo = contactInfo;
    }
}
