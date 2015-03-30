package com.peanutBarrel.entities;

import com.peanutBarrel.data.ChildInfo;

public class Child
{

    Long childId;
    ChildInfo childInfo;
    Long familyId;
    String firstName;
    String lastName;
    String middleName;

    public Child(Long familyId)
    {
        childId = new Long(-1L);
        childInfo = null;
        this.familyId = null;
        firstName = null;
        lastName = null;
        middleName = null;
        this.familyId = familyId;
    }

    public Long getChildId()
    {
        return childId;
    }

    public ChildInfo getChildInfo()
    {
        return childInfo;
    }

    public Long getFamilyId()
    {
        return familyId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setChildId(Long childId)
    {
        this.childId = childId;
    }

    public void setChildInfo(ChildInfo childInfo)
    {
        this.childInfo = childInfo;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }
}
